package core;

import halstead.core.ProgramModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramModelTest {
    private final ProgramModel programModel = new ProgramModel();

    @Test
    public void testVolumeValidInput() {
        double programVolume = programModel.getProgramVolume(3, 2);
        int nStar = 3 + 2;
        double expected = (nStar + 2) * (Math.log(nStar + 2) / Math.log(2));
        assertEquals(expected, programVolume, 1e-6);
    }

    @Test
    public void testVolumeInvalidInputParams() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> programModel.getProgramVolume(0, 2));
        assertTrue(exception.getMessage().contains("countInputParams должен быть > 0"));
    }

    @Test
    public void testVolumeInvalidOutputParams() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> programModel.getProgramVolume(3, -2));
        assertTrue(exception.getMessage().contains("countOutputParams должен быть > 0"));
    }

    @Test
    public void testBugsValidInput() {
        double programVolume = 50.0;
        double abstractionLevel = 1.5;
        double expected = (programVolume * programVolume / (3000.0 * abstractionLevel));
        assertEquals(expected, programModel.getBugCount(programVolume, abstractionLevel), 1e-6);
    }

    @Test
    public void testBugsZeroAbsLevel() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> programModel.getBugCount(50, 0));
        assertTrue(exception.getMessage().contains("abstractionLevel должен быть > 0"));
    }

    @Test
    public void testBugsNegativeAbsLevel() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> programModel.getBugCount(50, -1.2));
        assertTrue(exception.getMessage().contains("abstractionLevel должен быть > 0"));
    }
}
