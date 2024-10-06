#include <string>
class Player;
class Microdistrict;

class Building {
public:
	Building(int duration, int start, long long cost, Player* owner, Microdistrict* district) :
		_duration(duration),
		_start_period(start),
		_construction_cost(cost),
		_owner(owner),
		_district(district)
	{}
	bool is_builded(int cur_month) {
		return cur_month - _duration >= _start_period;
	}
	virtual bool is_shop() const = 0;
	std::string get_type() {
		return _type;
	}
	virtual int get_info() = 0;
	long long get_cost() {
		return _construction_cost;
	}
	virtual void add_profit(long long x) = 0;
	Player* get_owner() {
		return _owner;
	}
	std::string convert();
	virtual std::string convert_to_json() = 0;
private:
	std::string _type;
	long long _construction_cost; // стоимость постройки измер€етс€ в у.е в мес€ц.
	int _duration; // —рок стройки в мес€цах
	int _start_period; // ћес€ц начала стройки
	Player* _owner;
	Microdistrict* _district;
};

class House : public Building {
public:
	House(int duration, int start, long long cost, long long profit, Player* owner, Microdistrict* district, int cnt_aparts, int sold_aparts, int sale_aparts, long long price_aparts, std::string hid, std::string type):
		_house_type(type),
		Building(duration, start, cost, owner, district),
		_cnt_apartments(cnt_aparts),
		_apartment_price(price_aparts),
		_sold_apartments(sold_aparts),
		_sale_apartments(sale_aparts),
		_house_profit(profit),
		_hid(hid)
	{}
	int get_info() {
		return _sold_apartments;
	}
	bool is_shop() const final {
		return 0;
	}
	void add_profit(long long x) {
		_house_profit += x;
	}
	long long get_sale_apartments() {
		return _sale_apartments;
	}
	long long get_apartment_price() {
		return _apartment_price;
	}
	void add_sold_apartments(int x) {
		_sold_apartments += x;
	}
	void minus_sale_apartments(int x) {
		_sale_apartments -= x;
	}
	std::string convert_to_json();
private:
	std::string _house_type;
	long long _house_profit;
	int _cnt_apartments;// количество квартир
	long long _apartment_price; // цена одной квартиры
	int _sold_apartments; // количество проданных квартир
	int _sale_apartments; // количество квартир на продаже
	std::string _hid;
};

class Shop : public Building {
public:
	Shop(int duration, int start, long long cost, Player* owner, Microdistrict* district, long long profit, std::string sid, std::string type) :
		Building(duration, start, cost, owner, district),
		_shop_type(type),
		_shop_profit(profit), _sid(sid)
	{}
	bool is_shop() const final {
		return 1;
	}
	void add_profit(long long x) {
		_shop_profit += x;
	}
	int get_info() {
		return 0;
	}
	std::string convert_to_json();
private:
	std::string _shop_type;
	long long _shop_profit;
	std::string _sid;
};