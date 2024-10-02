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
	long double _construction_cost; // ���������� � ���. �.�
	int _construction_period; //���������� � �������
	Player* _owner;
	int _start_build; // ����� ������ �������
	bool is_builded;
	Microdistrict* _district;
};

class House : public Building {
public:
	//...
private:
	std::string type;
	int _demand; // ����� �� �����
};

class Shop : public Building {
public:
	//...
private:
	std::string type;
	int _average_sales_level; // ������� ������� ������
};