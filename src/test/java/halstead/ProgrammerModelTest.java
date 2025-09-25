package halstead;

import halstead.core.ProgrammerModel;
import halstead.dto.ProgramStat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProgrammerModelTest {

    @Test
    void testGetRating() {
        ProgramStat[] stats = {new ProgramStat(0, 0)};
        double expected = 0.0;
        double actual = ProgrammerModel.getRating(0, 0, stats);
        assertEquals(expected, actual);
    }

    @Test
    void testGetBugForecast() {
        double expected = 0.0;
        double actual = ProgrammerModel.getBugForecast(0, 0, 0);
        assertEquals(expected, actual);
    }
}
