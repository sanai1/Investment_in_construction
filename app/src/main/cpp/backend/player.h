#include <iostream>
#include <string>
#include <vector>

class Microdistrict;
class Building;

class Player {
public:
	Player(long long cash, long long ad_shops, long long ad_houses, Microdistrict* district, std::string uid, int numberstep, long long prev);
	void get_cahs_shops(int );
	void update_construction(int month);
	Microdistrict* get_district() {
		return _my_district;
	}
	void add_profit(long long x) {
		_cash += x;
	}
	std::string convert_to_json();
	void set_buildings(std::vector<Building*>& houses, std::vector<Building*>& shops);
private:
	int _number_step;
	std::string _uid;
	long long _cash; // измеряется в у.е
	std::vector<Building*> _houses, _shops;
	Microdistrict* _my_district;
	long long _shops_advertisment;
	long long _house_advertisment;
	long long _prev_advertisment;
};