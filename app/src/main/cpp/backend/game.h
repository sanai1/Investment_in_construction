#include "logic.h"

class Game {
public:
	Game(int num_pl, int cnt_month) :
		_num_players(num_pl),
		_current_month(1),
		_last_month(cnt_month) {
	}
	// void player_makes_move(string params ...)
	void player_start_build(std::string type, Player* player);

private:
	int _current_month;
	int _last_month;
	int _num_players;
	std::vector<Player*> _players;
	std::vector<Building*> _all_buildings;
	std::vector<Microdistrict*> _all_microdistricts;
};

void Game::player_start_build(std::string type, Player* player) {
	Building* new_build = nullptr;
	if (type == "supermarket") {
		new_build = new Shop(35000, 5, type);
	}
	else if (type == "bakery") {
		new_build = new Shop(20000, 3, type);
	}
	else if (type == "hardware store") {
		new_build = new Shop(60000, 8, type);
	}
	else if (type == "panel") {
		new_build = new House(50000, 4, type, 80);
	}
	else if (type == "monolithic") {
		new_build = new House(10000, 8, type, 300);
	}
	else if (type == "brick") {
		new_build = new House(75000, 6, type, 150);
	}
	if (!buy_building(player, new_build)) {
		delete new_build;
	}
}