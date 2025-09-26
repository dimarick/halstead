package halstead.core;

public class ModuleProgramModel {
    private static void checkParams(String paramName, double value) {
        if (value <= 0) {
            throw new IllegalArgumentException(
                String.format("Параметр %s должен быть > 0, переданное значение: %.2f",  paramName, value)
            );
        }
    }


    /**
     * Метод для расчёта числа модулей
     * @param countInputParams количество входных параметров
     * @param countOutputParams количество выходных параметров
     * @return ArgumentException или число модулей double, в зависимости от числа уровней
     */
    public double getCount(int countInputParams, int countOutputParams) {
        checkParams("countInputParams", countInputParams);
        checkParams("countOutputParams", countOutputParams);

        int nStar = countInputParams + countOutputParams;
        double k = nStar / 8.0;

        if (k > 8) {
            k = (nStar / 8.0) + (nStar / 64.0);
        }

        return k;
    }

    /**
     * Метод для расчёта длины программы
     * @param moduleCount число модулей
     * @return Длину программы в словах
     */
    public double getProgramSize(double moduleCount) {
        return (220 * moduleCount) + (moduleCount * ( Math.log(moduleCount) / Math.log(2) ));
    }

    /**
     * Метод для расчёта объёма ПО
     * @param moduleCount число модулей
     * @return Объём ПО в единицах информации
     */
    public double getProgramVolume(double moduleCount) {
        return moduleCount * 220 * (Math.log(48) / Math.log(2));
    }

    /**
     * Метод для расчёта количества команд ассемблера
     * @param moduleProgramSize длина программы в словах
     * @return количество команд в единицах
     */
    public double getAssemblerInstructions(double moduleProgramSize) {
        return (3 * moduleProgramSize) / 8.0;
    }

    /**
     * Метод для расчёта календарного времени
     * @param moduleProgramSize длина программы в словах
     * @param programmerTeamSize число программистов
     * @param programmerAvgPerformance производительность
     * @return Время для написания программы, в единицах измерения (обычно дни)
     */
    public double getProgramProjectTime(double moduleProgramSize, double programmerTeamSize, double programmerAvgPerformance) {
        return  getAssemblerInstructions(moduleProgramSize) / (programmerTeamSize * programmerAvgPerformance);
    }

    /**
     * Метод для расчёта потенциального количества ошибок
     * @param moduleProgramVolume объём ПО в единицах информации
     * @return Число потенциальных ошибок в единицах
     */
    public double getProgramBugCount(double moduleProgramVolume) {
        return (moduleProgramVolume / 3000);
    }

    /**
     * Метод для расчёта времени наработки на отказ
     * @param dayLength время рабочего дня, в часах
     * @param moduleProgramProjectTime календарное время программирования, в днях
     * @param moduleProgramBugCount потенциальное число ошибок, в единицах
     * @return время наработки на отказ (время до первого сбоя), в часах
     */
    public double getProgramMeanTimeToFailure(double dayLength, double moduleProgramProjectTime, double moduleProgramBugCount) {
        double projectTimeHours = dayLength * moduleProgramProjectTime;
        return projectTimeHours / (2 * Math.log(moduleProgramBugCount));
    }
}
