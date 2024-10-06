#include "logic.h"

class Game {
public:
	Game();
	void players_pay_month_construction(); // ����� ������ �� ��������� 1 ������ ������.
	void players_get_cash_from_shops(); // ������ �������� ������� �� ��������
	void players_get_cash_from_houses();//������� �� ������� �������
	std::string convert_to_json();

	~Game();
private:
	int _current_month;
	std::vector<Player*> _players;
	std::vector<Building*> _all_buildings;
	std::vector<Microdistrict*> _all_microdistricts;
};

void Game::players_pay_month_construction() {
	for (Player* player : _players) {
		player->update_construction(_current_month);
	}
}

void Game::players_get_cash_from_shops() {
	for (Player* player : _players) {
		player->get_cahs_shops(_current_month);
	}
}

void Game::players_get_cash_from_houses() {
	for (Microdistrict* district : _all_microdistricts) {
		district->sell_some_apartments();
	}
}