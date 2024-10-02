#include <iostream>
#include <string>
#include <vector>

class Microdistrict;
class Building;

class Player {
public:
	Player(long double cash, std::string name, Microdistrict* district) :
		_cash(cash),
		_name(name),
		_my_district(district)
	{}
	void info();
	friend bool buy_building(Player*, Building*, int);
private:
	long double _cash;// измеряется в тыс. у.е
	std::string _name;
	std::vector<Building*> _my_buildings;
	Microdistrict* _my_district;
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