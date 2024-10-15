#include <vector>
#include <string>
class Building;
class Player;
// Спрос на жилье и средний уровень продаж по месяцам:
const std::vector<long long> percent_of_demands = { 5, 5 ,10, 15, 10, 15, 20, 25, 30, 30, 25, 20 };
const std::vector<long long> percent_of_sales_levels = { 25, 25, 20, 20, 15, 15, 10, 20, 10, 15, 20, 25 };
const long long start_demand = 10;

class Microdistrict {
public:
	Microdistrict(std::string name, int month) :
		_name(name),
		_demand(0),
		_sales_level(0),
		_inc_demand(0),
		_month(month),
		_cnt_apart_sold_now(0),
		_have_supermarket(0)
	{}
	void add_building(Building*);
	std::string get_name() {
		return _name;
	}
	void add_percent_demand(long long x) {
		_inc_demand += x;
	}
	long long get_demand() {
		return _demand + _demand * (_inc_demand + percent_of_demands[_month % 12]) / 100 + start_demand;
	}
	long long get_sales_level() {
		return _sales_level + _sales_level * percent_of_sales_levels[_month % 12] / 100;
	}
	bool is_have_supermarket() {
		return _have_supermarket;
	}
	void add_apart(int x) {
		_cnt_apart_sold_now += x;
	}
	long long get_sold_apart() {
		return _cnt_apart_sold_now;
	}
	void sell_some_apartments();
private:
	long long _cnt_apart_sold_now;
	bool _have_supermarket;
	int _month;
	std::string _name;
	std::vector<Building*> _houses_here;
	std::vector<Building*> _shops_here;
	long long _demand;
	long long _sales_level;
	long long _inc_demand; // - Повышение спроса от рекламы, измерятеся в %
};

void Microdistrict::add_building(Building* building) {
	if (building->is_shop()) {
		if (building->get_type() == "Supermarket") {
			_have_supermarket = 1;
			_demand += 10;
		}
		else {
			_demand += 5;
		}
		_shops_here.push_back(building);
	}
	else {
		_houses_here.push_back(building);
		_sales_level += building->get_info() * 500;
	}
}