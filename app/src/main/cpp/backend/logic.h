#include "player.h"
#include "buildings.h"
#include "microdistrict.h"
#include <algorithm>
#include <set>

std::set<int> used_id;

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
        _shops_advertisment = _shops_advertisment * 3 / 500; // перевод в проценты
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

int generate_id() {
    int id = 10000 + rand() % 90000;
    while (used_id.find(id) != used_id.end()) {
        id = 10000 + rand() % 90000;
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
            int sold_apart = cur_demand - offer;
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

void Bot::set_data(int current_month) {
    long long spend = _cash - (_houses.size() + _shops.size()) * 500000;
    if (_strategy == 1) { 
        long long on_houses = spend * 60 / 100;
        long long on_shops = spend * 30 / 100;
        long long advertisment = spend - on_houses - on_shops;
        while (true) {
            int type_house = rand() % 3 + 1;
            if (type_house == 1) {
                if (on_houses >= 600000) {
                    on_houses -= 600000;
                    Building* house = new House(6, current_month, 600000, 0, this, _my_district, 30, 0, 30, 60000, std::to_string(generate_id()), "Panel", 0);
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 2) {
                if (on_houses >= 700000) {
                    on_houses -= 700000;
                    Building* house = new House(7, current_month, 700000, 0, this, _my_district, 40, 0, 40, 70000, std::to_string(generate_id()), "Brick", 0);
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 3) {
                if (on_houses >= 800000) {
                    on_houses -= 800000;
                    Building* house = new House(8, current_month, 800000, 0, this, _my_district, 50, 0, 50, 80000, std::to_string(generate_id()), "Monolithic", 0);
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
                if (on_shops >= 300000) {
                    on_shops -= 300000;
                    Building* shop = new Shop(3, current_month, 300000, this, _my_district, 0, std::to_string(generate_id()), "Bakery", 0);
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 2) {
                if (on_shops >= 500000) {
                    on_shops -= 500000;
                    Building* shop = new Shop(5, current_month, 500000, this, _my_district, 0, std::to_string(generate_id()), "Supermarket", 0);
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 3) {
                if (on_shops >= 500000) {
                    on_shops -= 500000;
                    Building* shop = new Shop(5, current_month, 300000, this, _my_district, 0, std::to_string(generate_id()), "HardwareStore", 0);
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
        long long advertisment = spend - on_shops - on_shops;
        while (true) {
            int type_house = rand() % 3 + 1;
            if (type_house == 1) {
                if (on_houses >= 600000) {
                    on_houses -= 600000;
                    Building* house = new House(6, current_month, 600000, 0, this, _my_district, 30, 0, 30, 45000, std::to_string(generate_id()), "Panel", 0);
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 2) {
                if (on_houses >= 700000) {
                    on_houses -= 700000;
                    Building* house = new House(7, current_month, 700000, 0, this, _my_district, 40, 0, 40, 55000, std::to_string(generate_id()), "Brick", 0);
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 3) {
                if (on_houses >= 800000) {
                    on_houses -= 800000;
                    Building* house = new House(8, current_month, 800000, 0, this, _my_district, 50, 0, 50, 70000, std::to_string(generate_id()), "Monolithic", 0);
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
                if (on_shops >= 500000) {
                    on_shops -= 500000;
                    Building* shop = new Shop(5, current_month, 500000, this, _my_district, 0, std::to_string(generate_id()), "Supermarket", 0);
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 3) {
                if (on_shops >= 500000) {
                    on_shops -= 500000;
                    Building* shop = new Shop(5, current_month, 300000, this, _my_district, 0, std::to_string(generate_id()), "HardwareStore", 0);
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
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
                if (on_houses >= 600000) {
                    on_houses -= 600000;
                    Building* house = new House(6, current_month, 600000, 0, this, _my_district, 30, 0, 30, 55000, std::to_string(generate_id()), "Panel", 0);
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 2) {
                if (on_houses >= 700000) {
                    on_houses -= 700000;
                    Building* house = new House(7, current_month, 700000, 0, this, _my_district, 40, 0, 40, 65000, std::to_string(generate_id()), "Brick", 0);
                    _houses.push_back(house);
                    _my_district->add_building(house);
                }
                else {
                    break;
                }
            }
            else if (type_house == 3) {
                if (on_houses >= 800000) {
                    on_houses -= 800000;
                    Building* house = new House(8, current_month, 800000, 0, this, _my_district, 50, 0, 50, 81000, std::to_string(generate_id()), "Monolithic", 0);
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
                if (on_shops >= 500000) {
                    on_shops -= 500000;
                    Building* shop = new Shop(5, current_month, 500000, this, _my_district, 0, std::to_string(generate_id()), "Supermarket", 0);
                    _shops.push_back(shop);
                    _my_district->add_building(shop);
                }
                else {
                    break;
                }
            }
            else if (type_shop == 3) {
                if (on_shops >= 500000) {
                    on_shops -= 500000;
                    Building* shop = new Shop(5, current_month, 300000, this, _my_district, 0, std::to_string(generate_id()), "HardwareStore", 0);
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
