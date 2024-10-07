#include "parcer.h"

std::string make_run(std::string s) {
	Game game(s);
	game.players_pay_month_construction();
	game.players_get_cash_from_shops();
	game.players_get_cash_from_houses();
	return game.convert_to_json();
}