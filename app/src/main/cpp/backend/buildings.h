class Player;
class Microdistrict;

class Building {
public:
	Building(long double cost, int period) :
		_start_build(-1),
		_construction_cost(cost),
		_construction_period(period),
		_owner(nullptr),
		is_builded(false)
	{}
	friend bool buy_building(Player*, Building*, int);
private:
	long double _construction_cost; // »змер€етс€ в тыс. у.е
	int _construction_period; //»змер€етс€ в мес€цах
	Player* _owner;
	int _start_build; // ћес€ц начала стройки
	bool is_builded;
	Microdistrict* _district;
};

class House : public Building {
public:
	//...
private:
	std::string type;
	int _demand; // спрос на жильЄ
};

class Shop : public Building {
public:
	//...
private:
	std::string type;
	int _average_sales_level; // средний уровень продаж
};