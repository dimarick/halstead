package halstead;

import halstead.core.ModuleProgramModel;
import halstead.core.ProgramModel;
import halstead.core.ProgrammerModel;
import halstead.dto.ProgramStat;

public class Main {
    public static void main(String[] args) {
        System.out.println("Демонстрация расчетов по метрикам Холстеда");
        System.out.println("======================================================================");

        // Задание № 1
        System.out.println("\n--- Задание № 1: Оценка потенциального числа ошибок ---\n");
        var programModel = new ProgramModel();

        // Входные данные
        var inputParams = 0; // Число входных параметров (n)
        var outputParams = 0; // Число выходных параметров (n2)
        var abstractionLevel = 0; // Уровень языка (λ)

        double programVolume = programModel.getProgramVolume(inputParams, outputParams);
        System.out.println("Расчетный потенциал объема программы (V*): " + programVolume);

        double bugCount = programModel.getBugCount(programVolume, abstractionLevel);
        System.out.println("Расчетное потенциальное число ошибок (B): " + bugCount);

        // Задание № 2
        System.out.println("\n--- Задание № 2: Расчет структурных параметров ---\n");
        var moduleProgramModel =  new ModuleProgramModel();

        // Входные данные
        var inputParamsForModules = 0; // Число входных параметров (n)
        var outputParamsForModules = 0; // Число выходных параметров (n2)
        var moduleCount = 0; // Условное количество модулей (K)
        var teamSize = 0; // Количество программистов в бригаде (m)
        var avgPerformance = 0; // Производительность (v)

        double calculatedModuleCount = moduleProgramModel.getCount(inputParamsForModules, outputParamsForModules);
        System.out.println("Расчетное количество модулей (K): " + calculatedModuleCount);

        double programSize = moduleProgramModel.getProgramSize(moduleCount);
        System.out.println("Расчетная длина программы (N): " + programSize);

        double moduleVolume = moduleProgramModel.getProgramVolume(moduleCount);
        System.out.println("Расчетный объем ПО (V): " + moduleVolume);

        double assemblerInstructions = moduleProgramModel.getAssemblerInstructions(programSize);
        System.out.println("Расчетное количество команд ассемблера (P): " + assemblerInstructions);

        double programProjectTime = moduleProgramModel.getProgramProjectTime(moduleCount, teamSize, avgPerformance);
        System.out.println("Расчетное календарное время программирования (Tk): " + programProjectTime);

        double programBugCount = moduleProgramModel.getProgramBugCount(moduleCount);
        System.out.println("Расчетное потенциальное количество ошибок (B): " + programBugCount);

        double programMeanTimeToFailure = moduleProgramModel.getProgramMeanTimeToFailure(moduleCount);
        System.out.println("Расчетное начальное время наработки на отказ (tн): " + programMeanTimeToFailure);

        // Задание № 3
        System.out.println("\n--- Задание № 3: Оценка рейтинга программиста ---\n");

        // Входные данные
        var initialRating = 0; // Начальный рейтинг R0
        var newProgramVolume = 0; // Объем программы в Кбайт
        ProgramStat[] stats = {
            new ProgramStat(0,0),
            new ProgramStat(0,0),
            new ProgramStat(0,0),
            new ProgramStat(0,0)
        };

        double newRating = ProgrammerModel.getRating(initialRating, abstractionLevel, stats);
        System.out.println("Новый рейтинг программиста (Ri): " + newRating);

        double bugForecast = ProgrammerModel.getBugForecast(newRating, abstractionLevel, newProgramVolume);
        System.out.println("Ожидаемое число ошибок в новой программе (B_n+1): " + bugForecast);

        System.out.println("\n======================================================================");
    }
}
