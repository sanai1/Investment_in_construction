#include "parcer.h"

void make_run() {
	Game game;
	game.players_pay_month_construction();
	game.players_get_cash_from_shops();
	game.players_get_cash_from_houses();
	std::ofstream out(PATH);
	out << game.convert_to_json();
}