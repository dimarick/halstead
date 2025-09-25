package halstead;

import halstead.core.ModuleProgramModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModuleProgramModelTest {

    private ModuleProgramModel moduleProgramModel;

    @BeforeEach
    void setUp() {
        moduleProgramModel = new ModuleProgramModel();
    }

    @Test
    void testGetCount() {
        var expected = 0.0;
        assertEquals(expected, moduleProgramModel.getCount(0, 0));
    }

    @Test
    void testGetProgramSize() {
        var expected = 0.0;
        assertEquals(expected, moduleProgramModel.getProgramSize(0));
    }

    @Test
    void testGetProgramVolume() {
        var expected = 0.0;
        assertEquals(expected, moduleProgramModel.getProgramVolume(0));
    }

    @Test
    void testGetAssemblerInstructions() {
        var expected = 0.0;
        assertEquals(expected, moduleProgramModel.getAssemblerInstructions(0));
    }

    @Test
    void testGetProgramProjectTime() {
        var expected = 0.0;
        assertEquals(expected, moduleProgramModel.getProgramProjectTime(0, 0, 0));
    }

    @Test
    void testGetProgramBugCount() {
        var expected = 0.0;
        assertEquals(expected, moduleProgramModel.getProgramBugCount(0));
    }

    @Test
    void testGetProgramMeanTimeToFailure() {
        var expected = 0.0;
        assertEquals(expected, moduleProgramModel.getProgramMeanTimeToFailure(0));
    }
}
