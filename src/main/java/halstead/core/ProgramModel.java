package halstead.core;

public class ProgramModel {
    private static void checkParams(String paramName, double value) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                String.format("Параметр %s должен быть > 0, переданное значение: %.2f",  paramName, value)
            );
        }
    }

    /**
     * Метод для расчёта потенциального объёма программы (на потенциальном языке, т.е. функции заранее определены)
     * @param countInputParams количество входных параметров модели
     * @param countOutputParams количество выходных параметров моделип
     * @return ArgumentException или потенциальный объём программы, т.е. максимально компактный текст программы
     */
    public double getProgramVolume(int countInputParams, int countOutputParams) {
        checkParams("countInputParams", countInputParams);
        checkParams("countOutputParams", countOutputParams);

        int nStar = countInputParams + countOutputParams;
        // 2 добавляется, потому что в потенциальном языке нужно 2 оператора: функция и присваивание
        double x = nStar + 2.0;
        // log_2(x) = log(x)/log(2)
        return x * (Math.log(x) / Math.log(2));
    }

    /**
     * Метод для расчёта потенциального числа ошибок потенциальной программы
     * @param programVolume потенциальный объём программы на потенциальном языке
     * @param abstractionLevel уровень реализации языка (отношение числа функций/процедур к числу операторов)
     * @return ArgumentException или потенциальное число ошибок
     */
    public double getBugCount(double programVolume, double abstractionLevel) {
        checkParams("abstractionLevel", abstractionLevel);

        // 3000 здесь – эмпирический коэффициент Холстеда, описание программы на английском языке
        // или же время мысленных различий при работе над программой
        return (programVolume * programVolume) / (3000.0 * abstractionLevel);
    }
}
