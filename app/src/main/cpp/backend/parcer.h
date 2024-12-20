#include <fstream>
#include <iostream>
#include "game.h"
#include "../json-develop/single_include/nlohmann/json.hpp"
#include <map>

#pragma once

struct Info {
	int nowPeople;
	int cntPeople;
	int numberStep;
	int roomCode;
	int currentPeriod;
	int startPeriod;
	Info() {}
};

Info info_for_json;

Game::Game(std::string s) {
	nlohmann::json jsonData = nlohmann::json::parse(s);
	_current_month = jsonData["numberStep"];
	info_for_json.currentPeriod = jsonData["currentPeriod"];
	info_for_json.cntPeople = jsonData["cntPeople"];
	info_for_json.nowPeople = jsonData["nowPeople"];
	info_for_json.numberStep = jsonData["numberStep"];
    info_for_json.numberStep++;
	_is_end = (_current_month == info_for_json.currentPeriod);
	info_for_json.roomCode = jsonData["roomCode"];
	info_for_json.startPeriod = jsonData["startPeriod"];
	_start_period = info_for_json.startPeriod;
	std::map<std::string, Microdistrict*> mp_districts;
	for (auto el : jsonData["userMap"]) {
		std::string distr = el["district"];
		if (mp_districts.find(distr) == mp_districts.end()) {
			mp_districts[distr] = new Microdistrict(distr, _current_month);
		}
		std::string uid = el["uid"];
		int numberstep = el["numberStep"];
		long long cash = el["profitFull"];
		long long ad_shop = el["advertisement"]["shop"];
		long long ad_house = el["advertisement"]["house"];
		long long prev_ad = el["advertisement"]["prevHouse"];
		std::string name = el["name"];
		int age = el["age"];
		int gen = el["gender"];
		Player* cur_player = nullptr;
		if (uid.size() >= 3 && uid.substr(0, 3) == "BOT") {
			cur_player = new Bot(cash, ad_shop, ad_house, mp_districts[distr], uid, numberstep, prev_ad, name, age, gen);
		}
		else {
			cur_player = new Player(cash, ad_shop, ad_house, mp_districts[distr], uid, numberstep, prev_ad, name, age, gen);
		}
		std::vector<Building*> houses;
		std::vector<Building*> shops;

		for (auto shop : el["shopMap"]) {
			int duration = shop["duration"];
			int startPeriod = shop["startPeriod"];
			long long priceMonth = shop["priceMonth"];	
			long long soldProfit = shop["soldProfit"];
			int percent = shop["percent"];
			std::string sid = shop["sid"];
			std::string type = shop["typeShop"];
			Microdistrict* sh_district = mp_districts[distr];
			int number = shop["number"];
			Shop* cur_shop = new Shop(duration, startPeriod, priceMonth, cur_player, sh_district, soldProfit, sid, type, percent, number);
			shops.push_back(cur_shop);
			_all_buildings.push_back(cur_shop);
		}

		for (auto house : el["houseMap"]) {
			int percent = house["percent"];
			int duration = house["duration"];
			int startPeriod = house["startPeriod"];
			std::string hid = house["hid"];
			int salePrice = house["salePrice"];
			std::string type = house["typeHouse"];
			long long saleApartments = house["saleApartments"];
			int countApartments = house["countApartments"];
			int soldApartments = house["soldApartments"];
			long long priceMonth = house["priceMonth"];
			long long soldProfit = house["soldProfit"];
			int number = house["number"];
			House* cur_house = new House(duration, startPeriod, priceMonth, soldProfit, cur_player, mp_districts[distr], countApartments, soldApartments, saleApartments,salePrice, hid, type, percent, number);
			houses.push_back(cur_house);
			_all_buildings.push_back(cur_house);
		}
		cur_player->set_buildings(houses, shops);
		_players.push_back(cur_player);
		if (uid.size() >= 0 && uid.substr(0, 3) == "BOT") {
			_bots.push_back(cur_player);
		}

	}

	for (auto el : mp_districts) {
		_all_microdistricts.push_back(el.second);
	}
}

Game::~Game() {
	for (Building* building : _all_buildings) {
		delete building;
	}
	for (Microdistrict* distr : _all_microdistricts) {
		delete distr;
	}
	for (Player* player : _players) {
		delete player;
	}
}

std::string Building::convert() {
	std::string ans = R"(          "duration" : )" + std::to_string(_duration) + ",\n";
	ans += R"(          "startPeriod" : )" + std::to_string(_start_period) + ",\n";
	ans += R"(          "priceMonth" : )" + std::to_string(_construction_cost) + ",\n";
	ans += R"(          "number" : )" + std::to_string(_number) + ",\n";
	ans += R"(          "percent" : )" + std::to_string(int(_percent_of_construction)) + ",\n";
	return ans;
}

std::string Shop::convert_to_json() {
	std::string ans = "        ";
	ans += R"(")" + _sid + R"(" : {)";
	ans += "\n";
	ans += Building::convert();
	ans += R"(          "soldProfit" : )" + std::to_string(_shop_profit) + ",\n";
	ans += R"(          "sid" : ")" + _sid + R"(",)" + "\n";
	ans += R"(          "typeShop" : ")" + _shop_type + R"("})";
	return ans;
}

std::string House::convert_to_json() {
	std::string ans = "        ";
	ans += R"(")" + _hid + R"(" : {)";
	ans += "\n";
	ans += Building::convert();
	ans += R"(          "hid": ")" + _hid + R"(",)" + "\n";
	ans += R"(          "salePrice": )" + std::to_string(_apartment_price) + ",\n";
	ans += R"(          "typeHouse": ")" + _house_type + R"(",)" + "\n";
	ans += R"(          "saleApartments": )" + std::to_string(_sale_apartments) + ",\n";
	ans += R"(          "countApartments": )" + std::to_string(_cnt_apartments) + ",\n";
	ans += R"(          "soldApartments": )" + std::to_string(_sold_apartments) + ",\n";
	ans += R"(          "soldProfit": )" + std::to_string(_house_profit) + "}";
	return ans;
}

std::string Player::convert_to_json() {
	std::string ans = R"(    ")" + _uid + R"(" : {)" + "\n";
	ans += R"(      "uid" : ")" + _uid + R"(",)" + "\n";
	ans += R"(      "gender" : )" + std::to_string(_gender) + R"(,)" + "\n";
	ans += R"(      "age" : )" + std::to_string(_age) + R"(,)" + "\n";
	ans += R"(      "name" : ")" + _name + R"(",)" + "\n";
	ans += R"(      "shopMap" : {)";
	ans += "\n";
	for (int i = 0; i < _shops.size(); ++i) {
		ans += _shops[i]->convert_to_json();
		if (i != _shops.size() - 1) {
			ans += ",\n";
		}
		else {
			ans += "\n";
		}
	}
	ans += R"(      },)";
	ans += "\n";
	ans += R"(      "houseMap" : {)";
	ans += "\n";
	for (int i = 0; i < _houses.size(); ++i) {
		ans += _houses[i]->convert_to_json();
		if (i != _houses.size() - 1) {
			ans += ",\n";
		}
		else {
			ans += "\n";
		}
	}
	ans += R"(      },)";
	ans += "\n";
	ans += R"(      "district": ")" + _my_district->get_name() + R"(",)";
	ans += "\n";
	ans += R"(      "advertisement": {)";
	ans += "\n";
	ans += R"(        "shop": )" + std::to_string(0) + ",\n";
	ans += R"(        "house": )" + std::to_string(0) + ",\n";
	ans += R"(        "prevHouse":)" + std::to_string(_house_advertisment) + "},\n";
	ans += R"(     "profitFull": )" + std::to_string(_cash) + ",\n";
	ans += R"(      "numberStep": )" + std::to_string(_number_step) + "}";
	return ans;
}


std::string Game::convert_to_json() {
	std::string ans = R"({ "nowPeople" : )" + std::to_string(info_for_json.nowPeople) + ",\n";
	ans += R"(  "startPeriod": )" + std::to_string(_start_period) + ",\n";
	ans += R"(  "cntPeople" : )" + std::to_string(info_for_json.cntPeople) + ",\n";
	ans += R"(  "numberStep" : )" + std::to_string(info_for_json.numberStep) + ",\n";
	ans += R"(  "roomCode" : )" + std::to_string(info_for_json.roomCode) + ",\n";
	ans += R"(  "userMap" : {)";
	ans += "\n";
	for (int i = 0; i < _players.size(); ++i) {
		ans += _players[i]->convert_to_json();
		if (i != _players.size() - 1) {
			ans += ",\n";
		}
	}
	ans += "},\n";
	ans += R"(  "currentPeriod": )" + std::to_string(info_for_json.currentPeriod) + "}";
	return ans;
}
