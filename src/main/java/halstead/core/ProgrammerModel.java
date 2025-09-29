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
            case LAMBDA_PLUS_R -> {
                double divider = abstractionLevel + rating;
                yield divider == 0 ? Double.POSITIVE_INFINITY : 1 / divider;
            }
            case LAMBDA_TIMES_R -> {
                double divider = abstractionLevel * rating;
                yield divider == 0 ? Double.POSITIVE_INFINITY : 1 / divider;
            }
            case INV_LAMBDA_INV_R -> {
                if (abstractionLevel == 0 || rating == 0) {
                    yield Double.POSITIVE_INFINITY;
                }
                yield 1.0 / abstractionLevel + 1.0 / rating;
            }
        };
    }

    /**
     * Метод для расчёта рейтинга в i момент времени
     * @param initialRating начальное значение рейтинга R_0
     * @param programStats статистика программы: объём, количество ошибок
     * @return рейтинг в i момент времени
     */
    public double getRating(double initialRating, ProgramStat[] programStats) {
        if (programStats.length == 0) {
            return initialRating;
        }

        double totalVolume = 0.0;
        double totalAdjustedBugs = 0.0;

        for (int i = 0; i < programStats.length; i++) {
            ProgramStat ps = programStats[i];
            double bugCount = ps.getBugCount();
            double c = getCoefficient(
                abstractionLevel,
                type,
                getRating(
                    initialRating,
                    Arrays.stream(programStats)
                        .limit(i)
                        .filter(stat -> stat.getBugCount() > 0)
                        .toArray(ProgramStat[]::new)
                )
            );

            totalAdjustedBugs += (bugCount / c);
            totalVolume += ps.getSize();
        }

        return initialRating * (1 + 1e-3 * (totalVolume - totalAdjustedBugs));
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
