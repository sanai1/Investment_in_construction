#include <iostream>
#include <string>
#include <vector>

class Microdistrict;
class Building;

class Player {
public:
	Player(std::vector<Building*>& houses, std::vector<Building*>& shops, long long cash, long long ad_shops, long long ad_houses, Microdistrict* district);
	void get_cahs_shops(int );
	void update_construction(int month);
	Microdistrict* get_district() {
		return _my_district;
	}
	void add_profit(long long x) {
		_cash += x;
	}
private:
	long long _cash; // измеряется в у.е
	std::vector<Building*> _houses, _shops;
	Microdistrict* _my_district;
	long long _shops_advertisment;
};

class HumanPlayer : public Player {
public:
	//...

private:

};

class BotPlayer : public Player {
public:
	//...

private:
	//...
};

class BotStrategy1 :public BotPlayer {
public:
	//...
private:
	//...
};

class BotStrategy2 : public BotPlayer {
public:
	//...
private:
	//...
};

//class BotStrategy3 ...