#include "player.h"
#include "buildings.h"
#include "microdistrict.h"


bool buy_building(Player* player, Building* building) {
	if (player->_cash >= building->_construction_cost) {
		player->_cash -= building->_construction_cost;
		player->_my_buildings.push_back(building);
		building->_owner = player;
		building->_district = player->_my_district;
		return true; // Операция прошла успешно - началась стройка
	}
	return false; // Игроку не хватает денег на покупку
}

void Microdistrict::new_building(Building* building) {
	std::string type = building->get_type();
	if (type == "supermarket") {
		_demand += 50;
	}
	else if (type == "bakery") {
		_demand += 25;
	}
	else if (type == "hardware store") {
		_demand += 100;
	}
	else if (type == "panel") {
		_sales_level += 50;
	}
	else if (type == "monolithic") {
		_sales_level += 250;
	}
	else if (type == "brick") {
		_sales_level += 125;
	}
}

void Player::update_construction() {
	for (Building* build : _my_buildings) {
		if (_cash >= build->get_cost() && !build->is_builded()) {
			build->update();
			_cash -= build->get_cost();
		}
	}
}