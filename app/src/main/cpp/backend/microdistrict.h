#include <vector>
#include <string>
class Building;
class Player;
// Спрос на жилье и средний уровень продаж по месяцам:
const std::vector<int> percent_of_demands = { 10, 5 ,10, 15, 20, 25, 30, 35, 40, 40, 35, 25 };
const std::vector<int> percent_of_sales_levels = { 40, 40, 30, 25, 20, 20, 15, 20, 10, 20, 25, 30 };

class Microdistrict {
public:
	Microdistrict(std::string name) :
		_name(name),
		_demand(50),
		_sales_level(0) // Изначально нет магазинов, следовательно нет продаж
	{}
	std::string get_name() {
		return _name;
	}
	void new_building(Building*);
	int get_cur_demand(int month) {
		return _demand + _demand * 100 / percent_of_demands[month % 12];
	}
	int get_cur_sales_level(int month) {
		return _sales_level + _sales_level * 100 / percent_of_sales_levels[month % 12];
	}
private:
	std::string _name;
	std::vector<Building*> _buildings_here;
	int _demand; // спрос на жилье в данном микрорайоне
	double _sales_level; // средний уровень продаж в магазинах данного микрорайона
};