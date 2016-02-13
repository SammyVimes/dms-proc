#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

int main(int argc, char *argv[])
{
    std::string line;
    //читаем входные данные для модуля
    //они все будут в одной строке (пофигу на формат, хоть JSON, хоть XML, хоть plain-text)
    std::getline(std::cin, line);
    cout << line << " friend" << endl;
    return 0;
}