package core;

import halstead.core.ProgrammerModel;
import halstead.dto.ProgramStat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

public class ProgrammerModelTest {

    @ParameterizedTest
    @EnumSource(ProgrammerModel.CoefficientType.class)
    public void testGetRatingNoBugs(ProgrammerModel.CoefficientType type) {
        ProgrammerModel programmerModel = new ProgrammerModel(1.5, type);

        double initialRating = 2000;
        ProgramStat[] stats = {
            new ProgramStat(15, 0),
            new ProgramStat(13, 0),
            new ProgramStat(14, 0),
        };

        double rating = programmerModel.getRating(initialRating, stats);

        assertTrue(rating > initialRating, "При отсутствии багов рейтинг должен расти (" + type + ")");
    }

    @ParameterizedTest
    @EnumSource(ProgrammerModel.CoefficientType.class)
    // Этот тест из-за большого нач. рейтинга падает только при огромном числе багов
    public void testRatingFallsIfManyBugs(ProgrammerModel.CoefficientType type) {
        ProgrammerModel programmerModel = new ProgrammerModel(1.8, type);

        double initialRating = 3000;
        ProgramStat[] stats = {
            new ProgramStat(2, 10000),
            new ProgramStat(3, 200000),
            new ProgramStat(4, 400000),
        };

        double rating = programmerModel.getRating(initialRating, stats);

        assertTrue(rating < initialRating,
            "При большом числе багов рейтинг должен падать (" + type + ")");
    }

    @ParameterizedTest
    @EnumSource(ProgrammerModel.CoefficientType.class)
    void testGetBugForecast(ProgrammerModel.CoefficientType type) {
        ProgrammerModel model = new ProgrammerModel(1.8, type);

        double rating = 3000;
        double programVolume = 10.0;

        double forecast = model.getBugForecast(rating, programVolume);

        assertTrue(forecast > 0.0,
            "Прогноз должен быть положительным (" + type + ")");
    }

    @ParameterizedTest
    @EnumSource(ProgrammerModel.CoefficientType.class)
    void testRatingIncreasesIfFewerBugsThanForecast(ProgrammerModel.CoefficientType type) {
        ProgrammerModel model = new ProgrammerModel(1.5, type);

        double initialRating = 1000;
        double programVolume = 20.0;

        double forecast = model.getBugForecast(initialRating, programVolume);

        int actualBugs = (int) Math.max(0, forecast / 2);

        ProgramStat[] stats = {
            new ProgramStat(20, actualBugs)
        };

        double newRating = model.getRating(initialRating, stats);

        assertTrue(newRating > initialRating,
            "Если багов меньше прогноза, рейтинг должен расти (" + type + ")");
    }

    @ParameterizedTest
    @EnumSource(ProgrammerModel.CoefficientType.class)
    void testRatingDecreasesIfMoreBugsThanForecast(ProgrammerModel.CoefficientType type) {
        ProgrammerModel model = new ProgrammerModel(1.5, type);

        double initialRating = 1000;
        double programVolume = 20.0;

        double forecast = model.getBugForecast(initialRating, programVolume);

        int actualBugs = (int) Math.ceil(forecast * 2);

        ProgramStat[] stats = {
            new ProgramStat(20, actualBugs)
        };

        double newRating = model.getRating(initialRating, stats);

        assertTrue(newRating < initialRating,
            "Если багов больше прогноза, рейтинг должен падать (" + type + ")");
    }

    @ParameterizedTest
    @EnumSource(ProgrammerModel.CoefficientType.class)
    void testRatingConsistency(ProgrammerModel.CoefficientType type) {
        ProgrammerModel model = new ProgrammerModel(1.5, type);

        ProgramStat[] stats = {
            new ProgramStat(2, 0),
            new ProgramStat(3, 0),
            new ProgramStat(4, 3),
            new ProgramStat(5, 4)
        };

        double initialRating = 1000;

        double rFull = model.getRating(initialRating, stats);

        double rStep = initialRating;
        for (ProgramStat ps : stats) {
            rStep = model.getRating(rStep, new ProgramStat[]{ps});
        }

        double rSplit = model.getRating(initialRating, new ProgramStat[]{stats[0], stats[1]});
        rSplit = model.getRating(rSplit, new ProgramStat[]{stats[2], stats[3]});

        assertEquals(rFull, rStep, 1e-6,
            "Результат должен быть одинаковым при последовательном применении");
        assertEquals(rFull, rSplit, 1e-6,
            "Результат должен быть одинаковым при разбиении на пакеты");
    }

}
