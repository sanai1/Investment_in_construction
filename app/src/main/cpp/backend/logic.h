#include "player.h"
#include "buildings.h"
#include "microdistrict.h"

bool buy_building(Player* player, Building* building, int month) {
	if (player->_cash >= building->_construction_cost) {
		player->_cash -= building->_construction_cost;
		player->_my_buildings.push_back(building);
		building->_owner = player;
		building->_start_build = month;
		building->_district = player->_my_district;
		return true; // Операция прошла успешно
	}
	return false; // Игроку не хватает денег на покупку
}

void Player::info() {
	std::cout << "name: " << _name << "\n";
	std::cout << "cash: " << _cash << "\n";
	std::cout << "My microdistrict: " << _my_district->get_name() << "\n";
}