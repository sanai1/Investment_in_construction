# Практикум "Моделированние"

## Постановка задачи

Разработать программную систему, осуществляющую имитационное моделирование процесса или явления (определяемого вариантом задания) и визуализирующую этот процесс или явление. Использовать для создания системы один из объектно-ориентированных языков программирования: С#, Object Pascal, C++, Java, Python, PHP, Ruby, Visual Basic и др., а также поддерживающие его инструментальные средства. Провести с помощью разработанной системы исследование поведения моделируемого процесса, задавая для этого различные значения параметров, от которых зависит этот процесс.

## Основные требования

- Система должна быть спроектирована на основе методологии объектно-ориентированного программирования, т.е. должна быть представлена в виде совокупности взаимодействующих друг с другом объектов, причем каждый объект является экземпляром определенного класса, а классы образуют иерархию. В ходе объектно-ориентированного проектирования необходимо определить и зафиксировать логическую структуру (классы и объекты) и файловую (модульную) структуру системы.

- Система должна предоставлять удобный и понятный пользовательский интерфейс, предусматривающий проведение экспериментов по моделированию и выдачу в ходе экспериментов необходимой информации (определяемой вариантом задания). В интерфейсе обязательно должны быть движущиеся объекты.

- Для проведения экспериментов по моделированию перед началом каждого эксперимента пользователь должен иметь возможность устанавливать нужные значения параметров, от которых зависит этот процесс или явление. Такие параметры называются параметрами моделирования, обычно в их числе – шаг моделирования, т.е. отрезок времени, измеряемый в тех или иных единицах времени (секундах, минутах, часах, днях, неделях и пр.) и/или число шагов моделирования.

- Поскольку в большинстве вариантов задания моделируемый процесс или явление зависит от нескольких неопределенных факторов, следует моделировать такие факторы статистически – на основе одного из законов вероятностного распределения (равномерного, нормального и др.).

## Содержание работы

1. Выбор и изучение инструментальных средств: языка программирования, соответствующей интегрированной среды разработки приложений (Visual Studio, Eclips, Delphi, С++ Builder и т.п.), графических библиотек.

2. Общее проектирование системы: уточнение постановки задачи выбранного варианта задания, определение изменяемых параметров моделируемого процесса/явления, метода моделирования, средств и объектов визуализации; составление эскиза пользовательского интерфейса.

3. Объектно-ориентированное проектирование: объектный анализ решаемой задачи и разработка диаграмм, характеризующих соответственно классы и объекты системы, выделенные в ходе анализа; составление текстовых спецификаций интерфейса классов.

4. Программирование системы на основе всех проектных решений, определение файловой (модульной) структуры программы.

5. Проведение исследования (экспериментов) по моделированию на базе реализованной программной системы.

6. Составление отчета, в который включаются:
   - 1). Уточненная постановка задачи для назначенного варианта задания.
   - 2). Диаграмма классов программной системы.
   - 3). Текстовые спецификации основных классов системы.
   - 4). Указание использованных при выполнении задания инструментальных средств (языка программирования, интегрированной среды, библиотек).
   - 5). Описание файловой структуры программной системы.
   - 6). Краткая характеристика пользовательского интерфейса.
   - 7). Краткое описание проведенных экспериментов.
   - 8). Подготовка полного GIT - репозитория проекта на github.