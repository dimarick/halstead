package halstead.core;

import halstead.dto.ProgramStat;

public class ProgrammerModel {
    public enum CoefficientType {
        LAMBDA_PLUS_R,
        LAMBDA_TIMES_R,
        INV_LAMBDA_INV_R
    }

    private static double getCoefficient(double abstractionLevel, CoefficientType type, double rating) {
        return switch (type) {
            case CoefficientType.LAMBDA_PLUS_R -> abstractionLevel + rating;
            case CoefficientType.LAMBDA_TIMES_R -> abstractionLevel * rating;
            case CoefficientType.INV_LAMBDA_INV_R -> 1.0 / abstractionLevel + 1.0 / rating;
        };
    }

    /**
     * Метод для расчёта рейтинга в i момент времени
     * @param initialRating начальное значение рейтинга R_0
     * @param abstractionLevel уровень реализации языка
     * @param type тип коэффициента c, необходимый для расчёта
     * @param programStats статистика программы: объём, количество ошибок
     * @return рейтинг в i момент времени
     */
    public static double getRating(double initialRating, double abstractionLevel, CoefficientType type, ProgramStat[] programStats) {
        double rPrev = initialRating;

        double sumV = 0.0;
        for (ProgramStat ps: programStats) {
            sumV += ps.getSize();
        }

        double sumDeltaBc = 0.0;
        for (ProgramStat ps: programStats) {
            if (ps.getBugCount() > 0) {
                double c = getCoefficient(abstractionLevel, type, rPrev);

                if (c == 0) c = 1e-6;

                sumDeltaBc += ps.getBugCount() / c;
            }
        }

        rPrev = rPrev * (1 + 1e-3 * (sumV - sumDeltaBc));
        return rPrev;
    }

    /**
     * Метод для расчёта ожидаемого числа ошибок
     * @param rating рейтинг в i момент времени
     * @param abstractionLevel уровень реализации языка
     * @param programVolume объём предполагаемой программы
     * @param type тип коэффициента c, необходимый для расчёта
     * @return Ожидаемое число ошибок в новой программе
     */
    public static double getBugForecast(double rating, double abstractionLevel, double programVolume, CoefficientType type) {
        double c = getCoefficient(abstractionLevel, type, rating);
        return c * programVolume;
    }
}
