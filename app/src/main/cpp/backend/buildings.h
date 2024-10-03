class Player;
class Microdistrict;
const int INC_PERCENT = 10;

class Building {
public:
	Building(long double cost, int period, std::string type) :
		_construction_cost(cost),
		_construction_period(period),
		_owner(nullptr),
		_is_builded(false),
		_district(nullptr),
		_type(type)
	{}
	bool is_builded() {
		return _is_builded;
	}
	void update() {
		if (_is_builded) {
			return;
		}
		_construction_period--;
	}
	virtual bool is_shop() const = 0;
	friend bool buy_building(Player*, Building*);
	std::string get_type() {
		return _type;
	}
	double get_cost() {
		return _construction_cost;
	}
private:
	std::string _type;
	bool _is_builded;
	double _construction_cost; // Измеряется в тыс. у.е в месяц.
	int _construction_period; //Сколько месяцев до конца стройки
	Player* _owner;
	Microdistrict* _district;
};

class House : public Building {
public:
	House(long double cost, int period, std::string type, int cnt_apart) :
		Building(cost, period, type),
		_cnt_apartments(cnt_apart),
		_sold_profit(0),
		_sold_apartments(0),
		_sale_apartments(0)
	{}
	bool is_shop() const final {
		return 0;
	}
	
private:
	int _cnt_apartments;// количество квартир
	double _apartment_price; // цена одной квартиры
	int _sold_apartments; // количество проданных квартир
	int _sale_apartments; // количество квартир на продаже
	double _sold_profit; // общая прибыль с дома
};

class Shop : public Building {
public:
	Shop(double cost, int period, std::string type) :
		Building(cost, period, type), _sold_profit(0) {
	}
	bool is_shop() const final {
		return 1;
	}
private:
	double _sold_profit;
};