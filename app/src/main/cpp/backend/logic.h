#include "player.h"
#include "buildings.h"
#include "microdistrict.h"
#include <algorithm>

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

Player::Player(long long cash, long long ad_shops, long long ad_houses, Microdistrict* district, std::string uid, int numberstep) :
	_cash(cash),
	_number_step(numberstep),
	_my_district(district),
	_uid(uid),
	_house_advertisment(ad_houses),
	_shops_advertisment(ad_shops) {
	_my_district->add_percent_demand(ad_houses / 2000);
}

void Player::set_buildings(std::vector<Building*>& houses, std::vector<Building*>& shops) {
	_houses = houses;
	_shops = shops;
	for (Building* house : _houses) {
		_my_district->add_building(house);
	}
	for (Building* shop : _shops) {
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
				}
				else {
					_houses[i]->delay();
				}
			}
			i++;
		}
		else {
			if (!_shops[j]->is_builded(month)) {
				if (_cash >= _shops[j]->get_cost()) {
					_cash -= _shops[j]->get_cost();
				}
				else {
					_shops[j]->delay();
				}
			}
			j++;
		}
	}
	while (i < _houses.size()) {
		if (!_houses[i]->is_builded(month)) {
			if (_cash >= _houses[i]->get_cost()) {
				_cash -= _houses[i]->get_cost();
			}
			else {
				_houses[i]->delay();
			}
		}
		i++;
	}
	while (j < _shops.size()) {
		if (!_shops[j]->is_builded(month)) {
			if (_cash >= _shops[j]->get_cost()) {
				_cash -= _shops[j]->get_cost();
			}
			else {
				_shops[j]->delay();
			}
		}
		j++;
	}
}

void Player::get_cahs_shops(int month) {
	long long cur_sales_level = _my_district->get_sales_level();
	for (Building* shop : _shops) {
		if (shop->is_builded(month)) {
			long long profit = cur_sales_level + cur_sales_level * _shops_advertisment / 100;
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
			break;
		}
		else {
			int sold_apart = cur_house->get_sale_apartments();
			offer += sold_apart;
			cur_house->add_profit(sold_apart * cur_house->get_apartment_price());
			cur_house->get_owner()->add_profit(sold_apart * cur_house->get_apartment_price());
			cur_house->add_sold_apartments(sold_apart);
			cur_house->minus_sale_apartments(sold_apart);
		}
	}
}
