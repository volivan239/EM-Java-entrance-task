# Программа

Это учебная программа, являющаяся реализацией вступительного задания для проекта по External Memory.

Для измерения скорости работы с диском программа `nTimes` раз повторяет следующую операцию: генерирует достаточно большой случайный набор байт, после чего записывает их в некоторый файл на диске, а затем считывает этот же набор из файла, замеряя время, потраченное на запись/чтение.
Дальше вычисляется скорость на запись/чтение этого набора байт, а затем по всем `nTimes` итерациям берется среднее значение.

В случае с записью используется `.channel.force()`, который позволяет убедиться что данные были действительно записаны на диск, а не "осели" где-нибудь по дороге.
Для чтения, я так понимаю, есть вероятность что ОС читает данные не с диска, а из каких-то своих кэшей, и я не нашел метода который бы гарантировал обратное. 
Однако, вероятно, если записывать и читать большие объемы, то это не должно заметно повлиять; в целом результаты выглядят правдоподобно.

Для запуска используется `gradle`. Параметр `nTimes` передается к аргумент командной строки.

### Пример запуска

```
gradle run --args='10'
```

# Результаты и интерпретация

На моем ноутбуке программа выдала скорость `290 MB/s` на запись и `418 MB/s` на чтение (средние значения). 
Полный вывод программы находится в файле [results.txt](results.txt).

Результаты работы программы показывают, что:

* Скорость работы с диском достаточно нестабильная: значения скорости от теста к тесту могут отличаться в 2 и более раз
* Чтение происходит ощутимо быстрее чем запись
* Чтение/запись на диск — достаточно дорогие операции: за ту секунду, за которую на диск запишется `300 MB`, можно было бы сложить порядка `10^9` интов, то есть обработать около `4 GB` в оперативной памяти.
