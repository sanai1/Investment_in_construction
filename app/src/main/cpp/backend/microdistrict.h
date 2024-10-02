#include <vector>
#include <string>
class Building;
class Player;

class Microdistrict {
public:
	Microdistrict(std::string name) :
		_name(name)
	{}
	std::string get_name() {
		return _name;
	}
private:
	std::string _name;
	std::vector<Building*> _buildings_here;
};