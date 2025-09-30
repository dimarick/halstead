package core;

import halstead.core.ModuleProgramModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModuleProgramModelTest {

    private final ModuleProgramModel model = new ModuleProgramModel();

    @Test
    void testGetCountValid() {
        double result1 = model.getCount(4, 4);
        assertEquals(1.0, result1, 1e-6);

        double result2 = model.getCount(40, 40);
        double expected2 = (80 / 8.0) + (80 / 64.0);
        assertEquals(expected2, result2, 1e-6);
    }

    @Test
    void testGetCountInvalid() {
        assertThrows(IllegalArgumentException.class, () -> model.getCount(0, 5));
        assertThrows(IllegalArgumentException.class, () -> model.getCount(5, -1));
    }

    @Test
    void testGetProgramSize() {
        double moduleCount = 10;
        double expected = 220 * moduleCount + moduleCount * (Math.log(moduleCount) / Math.log(2));
        assertEquals(expected, model.getProgramSize(moduleCount), 1e-6);
    }

    @Test
    void testGetProgramVolume() {
        double moduleCount = 10;
        double expected = moduleCount * 220 * (Math.log(48) / Math.log(2));
        assertEquals(expected, model.getProgramVolume(moduleCount), 1e-6);
    }

    @Test
    void testGetAssemblerInstructions() {
        double programSize = 1000;
        double expected = 3 * programSize / 8.0;
        assertEquals(expected, model.getAssemblerInstructions(programSize), 1e-6);
    }

    @Test
    void testGetProgramProjectTime() {
        double programSize = 1000;
        double teamSize = 5;
        double avgPerformance = 10;
        double assemblerInstructions = model.getAssemblerInstructions(programSize);
        double expected = assemblerInstructions / (teamSize * avgPerformance);
        assertEquals(expected, model.getProgramProjectTime(programSize, teamSize, avgPerformance), 1e-6);
    }

    @Test
    void testGetProgramBugCount() {
        double programVolume = 5000;
        double expected = programVolume / 3000;
        assertEquals(expected, model.getProgramBugCount(programVolume), 1e-6);
    }

    @Test
    void testGetProgramMeanTimeToFailure() {
        double dayLength = 8;
        double projectTime = 100;
        double bugCount = 20;
        double expected = (dayLength * projectTime) / (2 * Math.log(bugCount));
        assertEquals(expected, model.getProgramMeanTimeToFailure(dayLength, projectTime, bugCount), 1e-6);
    }
}
