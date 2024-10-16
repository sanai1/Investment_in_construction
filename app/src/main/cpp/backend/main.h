#include "parcer.h"

#pragma once

std::string make_run(std::string s) {
	Game game(s);
	game.bots_make_step();
	game.players_pay_month_construction();
	game.players_get_cash_from_houses();
	game.players_get_cash_from_shops();
	if (game.is_end()) {
		game.set_capital();
	}
	return game.convert_to_json();
}