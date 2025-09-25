package halstead.core;

import halstead.dto.EstimationProgramModel;

public class ProgramModel {
    public EstimationProgramModel estimate(int countInputParams, int countOutputParams, double abstractionLevel) {
        double programVolume = getProgramVolume(countInputParams, countOutputParams);
        double bugCount = getBugCount(programVolume, abstractionLevel);

        return new  EstimationProgramModel(programVolume, bugCount);
    }

    /**
     * Метод для расчёта потенциального объёма программы (на потенциальном языке, т.е. функции заранее определены)
     * @param countInputParams количество входных параметров модели
     * @param countOutputParams количество выходных параметров модели
     * @return ArgumentException или потенциальный объём программы, т.е. максимально компактный текст программы
     */
    public double getProgramVolume(int countInputParams, int countOutputParams) {
        if (countInputParams < 0 || countOutputParams < 0) {
            throw new IllegalArgumentException("Параметры не могут быть отрицательными");
        }
        int nStar = countInputParams + countOutputParams;
        // 2 добавляется, потому что в потенциальном языке нужно 2 оператора: функция и присваивание
        double x = nStar + 2.0;
        // log_2(x) = ln(x)/ln(2)
        return x * (Math.log(x) / Math.log(2));
    }

    /**
     * Метод для расчёта потенциального числа ошибок потенциальной программы
     * @param programVolume потенциальный объём программы на потенциальном языке
     * @param abstractionLevel уровень реализации языка (отношение числа функций/процедур к числу операторов)
     * @return ArgumentException или потенциальное число ошибок
     */
    public double getBugCount(double programVolume, double abstractionLevel) {
        if (abstractionLevel <= 0.0) {
            throw new IllegalArgumentException("Уровень реализации языка должен быть > 0");
        }

        // 3000 здесь – эмпирический коэффициент Холстеда, описание программы на английском языке
        // или же время мысленных различий при работе над программой
        return (programVolume * programVolume) / (3000.0 * abstractionLevel);
    }
}
