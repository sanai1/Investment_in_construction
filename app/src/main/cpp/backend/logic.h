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

Player::Player(std::vector<Building*>& houses, std::vector<Building*>& shops, long long cash, long long ad_shops, long long ad_houses, Microdistrict* district) :
	_cash(cash),
	_houses(houses),
	_my_district(district),
	_shops(shops),
	_shops_advertisment(ad_shops) {
	for (Building* house : _houses) {
		_my_district->add_building(house);
	}
	for (Building* shop : _shops) {
		_my_district->add_building(shop);
	}
	_my_district->add_percent_demand(ad_houses / 2000);
}

void Player::update_construction(int month) {
	for (Building* build : _houses) {
		if (!build->is_builded(month)) {
			_cash -= build->get_cost();
		}
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
		House* next_house = dynamic_cast<House*>(_houses_here[i + 1]);
		if (offer + cur_house->get_sale_apartments() >= cur_demand) {
			int sold_apart = cur_demand - offer;
			cur_house->add_profit(sold_apart * cur_house->get_sale_apartments());
			cur_house->get_owner()->add_profit(sold_apart * cur_house->get_sale_apartments());
			cur_house->add_sold_apartments(sold_apart);
			cur_house->minus_sale_apartments(sold_apart);
			break;
		}
		else {
			int sold_apart = cur_house->get_sale_apartments();
			offer += sold_apart;
			cur_house->add_profit(sold_apart * cur_house->get_apartment_price());
			cur_house->get_owner()->add_profit(sold_apart * cur_house->get_sale_apartments());
			cur_house->add_sold_apartments(sold_apart);
			cur_house->minus_sale_apartments(sold_apart);
		}
	}
}