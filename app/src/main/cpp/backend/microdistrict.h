#include <vector>
#include <string>
class Building;
class Player;
// Спрос на жилье и средний уровень продаж по месяцам:
const std::vector<int> percent_of_demands = { 10, 5 ,10, 15, 20, 25, 30, 35, 40, 40, 35, 25 };
const std::vector<int> percent_of_sales_levels = { 40, 40, 30, 25, 20, 20, 15, 20, 10, 20, 25, 30 };

class Microdistrict {
public:
	Microdistrict(std::string name, int month) :
		_name(name),
		_demand(0),
		_sales_level(0),
		_inc_demand(0),
		_month(month)
	{}
	void add_building(Building*);
	std::string get_name() {
		return _name;
	}
	void add_percent_demand(int x) {
		_inc_demand += x;
	}
	long long get_demand() {
		return _demand + _demand * (_inc_demand + percent_of_demands[_month % 12]) / 100;
	}
	long long get_sales_level() {
		return _sales_level + _sales_level * percent_of_sales_levels[_month % 12] / 100;
	}
	void sell_some_apartments();
private:
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
		_shops_here.push_back(building);
		_demand += 100;
	}
	else {
		_houses_here.push_back(building);
		_sales_level += building->get_info();
	}
}