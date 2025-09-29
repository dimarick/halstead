package halstead.core;

import halstead.dto.ProgramStat;

import java.util.Arrays;

public class ProgrammerModel {
    public enum CoefficientType {
        LAMBDA_PLUS_R,
        LAMBDA_TIMES_R,
        INV_LAMBDA_INV_R
    }

    private final double abstractionLevel;
    private final CoefficientType type;

    public ProgrammerModel(double abstractionLevel, CoefficientType type) {
        this.abstractionLevel = abstractionLevel;
        this.type = type;
    }

    private double getCoefficient(double abstractionLevel, CoefficientType type, double rating) {
        return switch (type) {
            case LAMBDA_PLUS_R -> 1 / (abstractionLevel + rating);
            case LAMBDA_TIMES_R -> 1 / (abstractionLevel * rating);
            case INV_LAMBDA_INV_R -> 1.0 / abstractionLevel + 1.0 / rating;
        };
    }

    /**
     * Метод для расчёта рейтинга в i момент времени
     * @param initialRating начальное значение рейтинга R_0
     * @param programStats статистика программы: объём, количество ошибок
     * @return рейтинг в i момент времени
     */
    public double getRating(double initialRating, ProgramStat[] programStats) {
        double currentRating = initialRating;

        for (int i = 0; i< programStats.length; i++) {
            ProgramStat ps = programStats[i];
            double bugCount = ps.getBugCount();
            double c = getCoefficient(abstractionLevel, type, getRating(initialRating, Arrays.copyOfRange(programStats, 0, i)));
            if (c == 0) c = 1e-6;

            double deltaBc = bugCount > 0 ? (bugCount / c) : 0.0;
            currentRating = currentRating * (1 + 1e-3 * (ps.getSize() - deltaBc));
        }

        return currentRating;
    }


    /**
     * Метод для расчёта ожидаемого числа ошибок
     * @param rating рейтинг в i момент времени
     * @param programVolume объём предполагаемой программы
     * @return Ожидаемое число ошибок в новой программе
     */
    public double getBugForecast(double rating, double programVolume) {
        double c = getCoefficient(abstractionLevel, type, rating);
        return c * programVolume;
    }
}
