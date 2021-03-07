package men.suruceanu.perfectnumbers;

import men.suruceanu.perfectnumbers.dto.InvalidRangeException;
import men.suruceanu.perfectnumbers.services.PerfectNumbersService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PerfectNumbersServiceUnitTests {

    @InjectMocks
    private PerfectNumbersService perfectNumbersService;

    @Test
    public void isPerfectNumberTest() {
        Assertions.assertTrue(perfectNumbersService.isPerfectNumber(6L));
        Assertions.assertFalse(perfectNumbersService.isPerfectNumber(-6L));
        Assertions.assertTrue(perfectNumbersService.isPerfectNumber(8128L));
        Assertions.assertFalse(perfectNumbersService.isPerfectNumber(1000000L));
    }

    @Test
    public void getPerfectNumbersByRangeTest() throws InvalidRangeException {

        List<Long> results = perfectNumbersService.getPerfectNumbersByRange(1L, 100L);
        Assertions.assertEquals(results.size(), 2);

        results = perfectNumbersService.getPerfectNumbersByRange(100L, 500L);
        Assertions.assertEquals(results.size(), 1);

        results = perfectNumbersService.getPerfectNumbersByRange(1L, 10000L);
        Assertions.assertEquals(results.size(), 4);

    }

    @Test
    public void wrongStartPerfectNumbersByRangeTest() {
        try {
            perfectNumbersService.getPerfectNumbersByRange(-1L, 100L);
        } catch (InvalidRangeException e) {
            Assertions.assertEquals("You have entered an invalid range. Please make sure the range is valid", e.getMessage());
        }
    }

    @Test
    public void wrongEndPerfectNumbersByRangeTest() {
        try {
            perfectNumbersService.getPerfectNumbersByRange(1L, -100L);
        } catch (InvalidRangeException e) {
            Assertions.assertEquals("You have entered an invalid range. Please make sure the range is valid", e.getMessage());
        }
    }

    @Test
    public void startHigherThenEndPerfectNumbersByRangeTest() {
        try {
            perfectNumbersService.getPerfectNumbersByRange(1000L, 100L);
        } catch (InvalidRangeException e) {
            Assertions.assertEquals("Start of range cannot be higher number then end!", e.getMessage());
        }
    }
}