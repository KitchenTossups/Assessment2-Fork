import static org.junit.jupiter.api.Assertions.*;

import com.oshewo.panic.Calculation;
import org.junit.jupiter.api.Test;

class PiazzaPanicTest {
    @Test
    public void testBark() {
        String expectedString = "woof";
        assertEquals(expectedString, "woof");
    }

    @Test
    public void testFindMax() {
        assertEquals(4, Calculation.findMax(new int[]{1, 3, 4, 2}));

    }


}