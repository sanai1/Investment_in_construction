#include "player.h"
#include "buildings.h"
#include "microdistrict.h"
#include <algorithm>
#include <set>

#pragma once

std::set<std::string> used_id;

bool compare_with_costs(Building* b1, Building* b2) {
    House* hptr1 = dynamic_cast<House*> (b1);
    House* hptr2 = dynamic_cast<House*> (b2);
    if (hptr1->get_apartment_price() == hptr2->get_apartment_price()) {
        return hptr1->get_sale_apartments() < hptr2->get_sale_apartments();
    }
    return hptr1->get_apartment_price() < hptr2->get_apartment_price();
}

bool compare_with_duration(Building* b1, Building* b2) {
    if (b1->end_period() == b2->end_period()) {
        return b1->get_cost() < b2->get_cost();
    }
    return b1->end_period() < b2->end_period();
}

Player::Player(long long cash, long long ad_shops, long long ad_houses, Microdistrict* district, std::string uid, int numberstep, long long prev, std::string name, int age, int gen) :
    _prev_advertisment(prev),
    _name(name),
    _age(age),
    _gender(gen),
    _cash(cash),
    _number_step(numberstep),
    _my_district(district),
    _uid(uid),
    _house_advertisment(ad_houses),
    _shops_advertisment(ad_shops) {
    if (_cash >= _house_advertisment) {
        _cash -= _house_advertisment;
    }
    else {
        _house_advertisment = 0;
    }
    if (_cash >= _shops_advertisment) {
        _cash -= _shops_advertisment;
        _shops_advertisment = _shops_advertisment * 3 / 500; // ������� � ��������
    }
    else {
        _shops_advertisment = 0;
    }
    _my_district->add_percent_demand((_house_advertisment + _prev_advertisment) / 2000);
}

void Player::set_buildings(std::vector<Building*>& houses, std::vector<Building*>& shops) {
    _houses = houses;
    _shops = shops;
    for (Building* house : _houses) {
        used_id.insert(house->get_id());
        _my_district->add_building(house);
    }
    for (Building* shop : _shops) {
        used_id.insert(shop->get_id());
        _my_district->add_building(shop);
    }
}

void Player::update_construction(int month) {
    std::sort(_houses.begin(), _houses.end(), compare_with_duration);
    std::sort(_shops.begin(), _shops.end(), compare_with_duration);
    int i = 0;
    int j = 0;
    for (i, j; i < _houses.size() && j < _shops.size();) {
        if (compare_with_duration(_houses[i], _shops[j])) {
            if (!_houses[i]->is_builded(month)) {
                if (_cash >= _houses[i]->get_cost()) {
                    _cash -= _houses[i]->get_cost();
                    _houses[i]->add_percent();
                }
            }
            i++;
        }
        else {
            if (!_shops[j]->is_builded(month)) {
                if (_cash >= _shops[j]->get_cost()) {
                    _cash -= _shops[j]->get_cost();
                    _shops[j]->add_percent();
                }
            }
            j++;
        }
    }
    while (i < _houses.size()) {
        if (!_houses[i]->is_builded(month)) {
            if (_cash >= _houses[i]->get_cost()) {
                _cash -= _houses[i]->get_cost();
                _houses[i]->add_percent();
            }
        }
        i++;
    }
    while (j < _shops.size()) {
        if (!_shops[j]->is_builded(month)) {
            if (_cash >= _shops[j]->get_cost()) {
                _cash -= _shops[j]->get_cost();
                _shops[j]->add_percent();
            }
        }
        j++;
    }
}

std::string generate_id() {
    std::string id = std::to_string(10000 + rand() % 90000);
    while (used_id.find(id) != used_id.end()) {
        id = std::to_string(10000 + rand() % 90000);
    }
    used_id.insert(id);
    return id;
}

void Player::get_cahs_shops(int month) {
    long long cur_sales_level = _my_district->get_sales_level();
    for (Building* shop : _shops) {
        if (shop->is_builded(month)) {
            long long profit = 0;
            if (shop->get_type() == "Supermarket") {
                profit = cur_sales_level + cur_sales_level * _shops_advertisment / 100;
            }
            else if (shop->get_type() == "Bakery") {
                if (_my_district->is_have_supermarket()) {
                    profit = cur_sales_level / 2 + cur_sales_level * _shops_advertisment / 200;
                }
            }
            else {
                profit = (cur_sales_level / 5 + cur_sales_level * _shops_advertisment / 500) * shop->get_district()->get_sold_apart() / 20;
            }
            shop->add_profit(profit);
            _cash += profit;
        }
    }
}

void Microdistrict::sell_some_apartments() {
    sort(_houses_here.begin(), _houses_here.end(), compare_with_costs);
    long long cur_demand = get_demand();
    long long offer = 0;
    for (int i = 0; i < _houses_here.size(); ++i) {
        House* cur_house = dynamic_cast<House*>(_houses_here[i]);
        if (offer + cur_house->get_sale_apartments() >= cur_demand) {
            long long sold_apart = cur_demand - offer;
            cur_house->add_profit(sold_apart * cur_house->get_apartment_price());
            cur_house->get_owner()->add_profit(sold_apart * cur_house->get_apartment_price());
            cur_house->add_sold_apartments(sold_apart);
            cur_house->minus_sale_apartments(sold_apart);
            if (cur_house->is_builded(_month)) {
                cur_house->get_owner()->get_district()->add_apart(sold_apart);
            }
            break;
        }
        else {
            int sold_apart = cur_house->get_sale_apartments();
            offer += sold_apart;
            cur_house->add_profit(sold_apart * cur_house->get_apartment_price());
            cur_house->get_owner()->add_profit(sold_apart * cur_house->get_apartment_price());
            cur_house->add_sold_apartments(sold_apart);
            cur_house->minus_sale_apartments(sold_apart);
            if (cur_house->is_builded(_month)) {
                cur_house->get_owner()->get_district()->add_apart(sold_apart);
            }
        }
    }
}

long long generate_price() {
    return (rand() % 5000 + 5000);
}

void Bot::set_data(int current_month) {
    long long spend = std::min(_cash, (long long) 500000);

    if (_strategy == 1) { 
        long long on_houses = spend * 60 / 100;
        long long on_shops = spend * 30 / 100;
      
        long long advertisment = spend - on_houses - on_shops;
        while (true) {
            int type_house = rand() % 3 + 1;
            if (type_house == 1) {
                if (on_houses >= 60000) {
                    on_houses -= 60000;
                    Building* house = new House(6, current_month, 60000, 0, this, _my_district, 30, 0, 30, generate_price(), generate_id(), "Panel", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 2) {
                if (on_houses >= 70000) {
                    on_houses -= 70000;
                    Building* house = new House(7, current_month, 70000, 0, this, _my_district, 40, 0, 40, generate_price(), generate_id(), "Brick", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 3) {
                if (on_houses >= 80000) {
                    on_houses -= 80000;
                    Building* house = new House(8, current_month, 80000, 0, this, _my_district, 50, 0, 50, generate_price(), generate_id(), "Monolithic", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
        }
        while (true) {
            int type_shop = rand() % 3 + 1;
            if (type_shop == 1) {
                if (on_shops >= 30000) {
                    on_shops -= 30000;
                    Building* shop = new Shop(3, current_month, 30000, this, _my_district, 0, generate_id(), "Bakery", 0, last_num());
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 2) {
                if (on_shops >= 50000) {
                    on_shops -= 50000;
                    Building* shop = new Shop(5, current_month, 50000, this, _my_district, 0, generate_id(), "Supermarket", 0, last_num());
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 3) {
                if (on_shops >= 50000) {
                    on_shops -= 50000;
                    Building* shop = new Shop(5, current_month, 30000, this, _my_district, 0, generate_id(), "HardwareStore", 0, last_num());
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
        }
        _shops_advertisment = advertisment / 2;
        _house_advertisment += advertisment / 2;
    }
    else if (_strategy == 2) {
        long long on_houses = spend * 75 / 100;
        long long on_shops = spend * 15 / 100;
        if (on_shops < 50000) {
            on_houses -= 50000 - on_shops;
            on_shops = 50000;
        }
        long long advertisment = spend - on_shops - on_houses;
        while (true) {
            int type_shop = rand() % 2 + 2;
            if (type_shop == 2) {
                if (on_shops >= 50000) {
                    on_shops -= 50000;
                    Building* shop = new Shop(5, current_month, 50000, this, _my_district, 0, generate_id(), "Supermarket", 0, last_num());
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 3) {
                if (on_shops >= 50000) {
                    on_shops -= 50000;
                    Building* shop = new Shop(5, current_month, 30000, this, _my_district, 0, generate_id(), "HardwareStore", 0, last_num());
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
        }
        while (true) {
            int type_house = rand() % 3 + 1;
            if (type_house == 1) {
                if (on_houses >= 60000) {
                    on_houses -= 60000;
                    Building* house = new House(6, current_month, 60000, 0, this, _my_district, 30, 0, 30, generate_price(), generate_id(), "Panel", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 2) {
                if (on_houses >= 70000) {
                    on_houses -= 70000;
                    Building* house = new House(7, current_month, 70000, 0, this, _my_district, 40, 0, 40, generate_price(), generate_id(), "Brick", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 3) {
                if (on_houses >= 80000) {
                    on_houses -= 80000;
                    Building* house = new House(8, current_month, 80000, 0, this, _my_district, 50, 0, 50, generate_price(), generate_id(), "Monolithic", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
        }
        _house_advertisment += advertisment;
    }
    else if(_strategy == 3) {
        long long on_houses = spend * 40 / 100;
        long long on_shops = spend * 50 / 100;
        long long adverisment = spend - on_houses - on_shops;
        while (true) {
            int type_house = rand() % 3 + 1;
            if (type_house == 1) {
                if (on_houses >= 60000) {
                    on_houses -= 60000;
                    Building* house = new House(6, current_month, 60000, 0, this, _my_district, 30, 0, 30, generate_price(), generate_id(), "Panel", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 2) {
                if (on_houses >= 70000) {
                    on_houses -= 70000;
                    Building* house = new House(7, current_month, 70000, 0, this, _my_district, 40, 0, 40, generate_price(), generate_id(), "Brick", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 3) {
                if (on_houses >= 80000) {
                    on_houses -= 80000;
                    Building* house = new House(8, current_month, 80000, 0, this, _my_district, 50, 0, 50, generate_price(), generate_id(), "Monolithic", 0, last_num());
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
        }
        while (true) {
            int type_shop = rand() % 2 + 2;
            if (type_shop == 2) {
                if (on_shops >= 50000) {
                    on_shops -= 50000;
                    Building* shop = new Shop(5, current_month, 50000, this, _my_district, 0, generate_id(), "Supermarket", 0, last_num());
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 3) {
                if (on_shops >= 50000) {
                    on_shops -= 50000;
                    Building* shop = new Shop(5, current_month, 50000, this, _my_district, 0, generate_id(), "HardwareStore", 0, last_num());
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
        }
        _shops_advertisment = adverisment;
    }
}


void Player::get_capital() {
    long long capital = _cash;
    for (Building* house : _houses) {
        House* hptr = dynamic_cast<House*>(house);
        if (!house->is_builded(0)) {
            capital += house->get_percent() * house->get_duration() / 100 * house->get_price();
        }
        else {
            long long not_sold = hptr->get_cnt_apartments() - house->get_info();
            
            capital += (house->get_duration() * house->get_price() / hptr->get_cnt_apartments()) * not_sold;
        }
    }
    for (Building* shop : _shops) {
        if (!shop->is_builded(0)) {
            capital += capital += shop->get_percent() * shop->get_duration() / 100 * shop->get_price();
        }
        else {
            capital += shop->get_duration() * shop->get_price() / 100 * 160;
        }
    }
    _cash = capital;
    return;
}