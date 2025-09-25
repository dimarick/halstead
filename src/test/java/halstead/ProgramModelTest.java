package halstead;

import halstead.core.ProgramModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramModelTest {

    private ProgramModel programModel;

    @BeforeEach
    public void setUp() {
        programModel = new ProgramModel();
    }

    @Test
    void testGetProgramVolume() {
        var expected = 0.0;
        var actual = programModel.getProgramVolume(0,0);
        assertEquals(expected, actual);
    }

    @Test
    void testGetBugCount() {
        var expected = 0;
        var actual = programModel.getBugCount(0,0);
        assertEquals(expected, actual);
    }
}
