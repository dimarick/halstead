package halstead;

import halstead.core.ModuleProgramModel;
import halstead.core.ProgramModel;
import halstead.core.ProgrammerModel;
import halstead.dto.ProgramStat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: Укажите путь к файлу конфигурации в качестве аргумента командной строки.");
            return;
        }

        String configFilePath = args[0];
        var properties = new Properties();

        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Ошибка: Не удалось прочитать файл конфигурации: " + configFilePath);
            throw new RuntimeException(e);
        }

        System.out.println("\n----- Демонстрация расчетов по метрикам Холстеда -----\n");
        System.out.println("=======================================================");

        // Задание № 1
        System.out.println("\n--- Задание № 1: Оценка потенциального числа ошибок ---\n");
        var programModel = new ProgramModel();

        // Входные данные
        var inputParams = Integer.parseInt(properties.getProperty("task1.inputParams")); // Число входных параметров (n)
        var outputParams = Integer.parseInt(properties.getProperty("task1.outputParams")); // Число выходных параметров (n2)
        var abstractionLevel = Double.parseDouble(properties.getProperty("task1.abstractionLevel")); // Уровень языка (λ)

        double programVolume = programModel.getProgramVolume(inputParams, outputParams);
        System.out.println("Расчетный потенциал объема программы (V*): " + programVolume);

        double bugCount = programModel.getBugCount(programVolume, abstractionLevel);
        System.out.println("Расчетное потенциальное число ошибок (B): " + bugCount);
        System.out.println("\n=======================================================");

        // Задание № 2
        System.out.println("\n--- Задание № 2: Расчет структурных параметров ---\n");
        var moduleProgramModel =  new ModuleProgramModel();

        // Входные данные
        var moduleCount = Double.parseDouble(properties.getProperty("task2.moduleCount")); // Условное количество модулей (K)
        var teamSize = Integer.parseInt(properties.getProperty("task2.teamSize")); // Количество программистов в бригаде (m)
        var avgPerformance = Double.parseDouble(properties.getProperty("task2.avgPerformance")); // Производительность (v)

        double calculatedModuleCount = moduleProgramModel.getCount(inputParams, outputParams);
        System.out.println("Расчетное количество модулей (K): " + calculatedModuleCount);

        double programSize = moduleProgramModel.getProgramSize(moduleCount);
        System.out.println("Расчетная длина программы (N): " + programSize);

        double moduleVolume = moduleProgramModel.getProgramVolume(moduleCount);
        System.out.println("Расчетный объем ПО (V): " + moduleVolume);

        double assemblerInstructions = moduleProgramModel.getAssemblerInstructions(programSize);
        System.out.println("Расчетное количество команд ассемблера (P): " + assemblerInstructions);

        double programProjectTime = moduleProgramModel.getProgramProjectTime(moduleCount, teamSize, avgPerformance);
        System.out.println("Расчетное календарное время программирования (Tk): " + programProjectTime);

        double programBugCount = moduleProgramModel.getProgramBugCount(moduleVolume);
        System.out.println("Расчетное потенциальное количество ошибок (B): " + programBugCount);

        double programMeanTimeToFailure = moduleProgramModel.getProgramMeanTimeToFailure(8, programProjectTime, programBugCount);
        System.out.println("Расчетное начальное время наработки на отказ (tн): " + programMeanTimeToFailure);
        System.out.println("\n=======================================================");

        // Задание № 3
        System.out.println("\n--- Задание № 3: Расчет рейтинга программиста ---\n");
        // Пример коэффициента LAMBDA_PLUS_R
        System.out.println("\n--- Коэффициент 1/ (lambda + R) ---\n");
        var programmerModel = new ProgrammerModel(abstractionLevel, ProgrammerModel.CoefficientType.LAMBDA_PLUS_R);

        // Входные данные
        var initialRating = Double.parseDouble(properties.getProperty("task3.initialRating")); // Начальный рейтинг R0
        var newProgramVolume = Double.parseDouble(properties.getProperty("task3.newProgramVolume")); // Объем программы в Кбайт
        ProgramStat[] stats = parseProgramStats(properties.getProperty("task3.programStats"));

        double newRating = programmerModel.getRating(initialRating, stats);
        System.out.println("Новый рейтинг программиста (Ri): " + newRating);

        double bugForecast = programmerModel.getBugForecast(newRating, newProgramVolume);
        System.out.println("Ожидаемое число ошибок в новой программе (B_n+1): " + bugForecast);

        System.out.println("\n=======================================================");

        // Пример коэффициента LAMBDA_TIMES_R
        System.out.println("\n--- Коэффициент 1 / (lambda * R) ---\n");
        programmerModel = new ProgrammerModel(abstractionLevel, ProgrammerModel.CoefficientType.LAMBDA_TIMES_R);
        newRating = programmerModel.getRating(initialRating, stats);
        System.out.println("Новый рейтинг программиста (Ri): " + newRating);

        bugForecast = programmerModel.getBugForecast(newRating, newProgramVolume);
        System.out.println("Ожидаемое число ошибок в новой программе (B_n+1): " + bugForecast);

        System.out.println("\n=======================================================");

        // Пример коэффициента INV_LAMBDA_INV_R
        System.out.println("\n--- Коэффициент 1/lambda + 1/R ---\n");
        programmerModel = new ProgrammerModel(abstractionLevel, ProgrammerModel.CoefficientType.INV_LAMBDA_INV_R);
        newRating = programmerModel.getRating(initialRating, stats);
        System.out.println("Новый рейтинг программиста (Ri): " + newRating);

        bugForecast = programmerModel.getBugForecast(newRating, newProgramVolume);
        System.out.println("Ожидаемое число ошибок в новой программе (B_n+1): " + bugForecast);

        System.out.println("\n=======================================================");
    }

    private static ProgramStat[] parseProgramStats(String statsString) {
        if (statsString == null || statsString.isEmpty()) {
            return new ProgramStat[0];
        }

        return Arrays.stream(statsString.split(";"))
            .map(String::trim)
            .map(pair -> pair.split(","))
            .filter(values -> values.length == 2)
            .map(values -> {
                try {
                    int size = Integer.parseInt(values[0].trim());
                    int bugCount = Integer.parseInt(values[1].trim());
                    return new ProgramStat(size, bugCount);
                }  catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .toArray(ProgramStat[]::new);
    }
}
