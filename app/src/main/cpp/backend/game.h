#include "logic.h"

class Game {
public:
	//Game(std::string); // json?
	Game(int num_pl, int cnt_month) :
		_num_players(num_pl),
		_current_month(1),
		_last_month(cnt_month) {
		//...
	}
private:
	int _current_month;
	int _last_month;
	int _num_players;
	std::vector<Player*> _players;
	std::vector<Building*> _all_buildings;
	std::vector<Microdistrict*> _all_microdistricts;
};