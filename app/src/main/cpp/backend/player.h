#include <iostream>
#include <string>
#include <ctime>
#include <vector>

class Microdistrict;
class Building;

class Player {
public:
	Player(long long cash, long long ad_shops, long long ad_houses, Microdistrict* district, std::string uid, int numberstep, long long prev, std::string name, int age, int gen);
	void get_cahs_shops(int );
	void update_construction(int month);
	Microdistrict* get_district() {
		return _my_district;
	}
	void add_profit(long long x) {
		_cash += x;
	}
	virtual void set_data(int current_month) {
		return;
	}
	std::string convert_to_json();
	void set_buildings(std::vector<Building*>& houses, std::vector<Building*>& shops);
protected:
	int _number_step;
	std::string _name;
	int _age, _gender;
	std::string _uid;
	long long _cash; // измеряется в у.е
	std::vector<Building*> _houses, _shops;
	Microdistrict* _my_district;
	long long _shops_advertisment;
	long long _house_advertisment;
	long long _prev_advertisment;
};

class Bot :  public Player{
public:
	Bot(long long cash, long long ad_shops, long long ad_houses, Microdistrict* district, std::string uid, int numberstep, long long prev, std::string name, int age, int gen) :
		Player(cash, ad_shops, ad_houses, district, uid, numberstep, prev, name, age, gen) {
		if (name == "GalinaBot") {
			_strategy = 1; // "Balanced investing"
		}
		else if (name == "IvanBot") {
			_strategy = 2; // "Aggressive construction"
		}
		else if (name == "EdwardBot") {
			_strategy = 3; // "Focus on supermarkets"
		}
		srand(time(0));
	}
	void set_data(int current_month);
	int last_num() {
		return _houses.size() + _shops.size() + 1;
	}
private:
	int _strategy;
};