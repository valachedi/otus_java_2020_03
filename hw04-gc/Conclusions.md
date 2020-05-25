# Результаты сравнения сборщиков мусора

## Параметры приложения

Замеры проводились для сборщиков мусора `Parallel` и `G1` с параметрами `-Xms1g -Xmx1g`.

### Полные команды запуска:
```
java -Xms1g -Xmx1g -Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m -XX:+UseParallelGC -jar hw04-gc/build/libs/homework-4-0.4-all.jar
```
```
java -Xms1g -Xmx1g -Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m -XX:+UseG1GC -jar hw04-gc/build/libs/homework-4-0.4-all.jar
```

## Метрики

Под "полезным временем" понимается время, затраченное на выполнение основной функции программы без учёта времени на сборку мусора: `(общее время выполнения) - (затраченное на gc).`

### -XX:+UseParallelGC

| Минута | Тип действия | Кол-во вызовов | Суммарное время (секунд) |
| :---: | --- | ---: | ---: |
| 1   | minor | 205 | 26 |
| 1   | major | 116 | 14 |
| 2   | minor |  0  |  0 |
| 2   | major | 134 | 21 |
| 3   | minor |  0  |  0 |
| 3   | major | 151 | 36 |
| Итого |     | 606 | 97 |

Полное время выполнения: 283 секунд.

**Полезное время: 186 секунды.**


### -XX:+UseG1GC

| Минута | Тип действия | Кол-во вызовов | Суммарное время (секунд) |
| :---: | --- | ---: | ---: |
| 1   | minor | 198 |  3 |
| 1   | major |  40 |  2 |
| 2   | minor | 190 |  4 |
| 2   | major |  91 |  7 |
| 3   | minor | 236 |  5 |
| 3   | major | 118 | 10 |
| Итого |     | 873 | 31 |

Полное время выполнения: 286 секунд.

**Полезное время: 255 секунд.**

## Вывод
Выводы на основе данных таблиц метрики:
- В следствие большой длительности выполнения сборки мусора на больших объёмах heap у Parallel число вызовов выглядит меньше.
- Рост занимаемой памяти не оказывает существенного влияния на время работы G1, увеличиваясь примерно на 4 секунды каждую минуту. У Parallel в виду больших значений времени увеличение выполнения более заметно.
- Parallel перестал производить minor-сборки, попытки изменения значений размера Survivor параметрами `-XX:NewRatio` и `-XX:SurvivorRatio` не дали существенных изменений в лучшую сторону.
- Полезное время выполнения программы с G1 на 37% больше, чем с Parallel.

Итого, G1 по сравнению с Parallel для конкретно рассматриваемого алгоритма более эффективен.

## Примечания
### Время выполнения программ / OutOfMemoryError
На 4й минуте работы обоих сборщиков, согласно заданию, выбрасывалось исключение:
```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.base/java.util.Arrays.copyOf(Arrays.java:3721)
        at java.base/java.util.Arrays.copyOf(Arrays.java:3690)
        at java.base/java.util.ArrayList.grow(ArrayList.java:235)
        at java.base/java.util.ArrayList.grow(ArrayList.java:242)
        at java.base/java.util.ArrayList.add(ArrayList.java:452)
        at java.base/java.util.ArrayList.add(ArrayList.java:465)
        at hw04.components.BenchmarkOverfillMemory.run(BenchmarkOverfillMemory.java:32)
        at hw04.Main.main(Main.java:35)
```