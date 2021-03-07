package men.suruceanu.perfectnumbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PerfectNumbersIntegrationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void checkPerfectNumberTest() {
        final String CHECK_PERFECT_NUMBER_PATH = "/perfect/number/check/{number}";
        Map<Long, Boolean> numbersToTestMap = new HashMap<Long, Boolean>() {{
            put(2L, false);
            put(6L, true);
            put(256L, false);
            put(8128L, true);

        }};

        numbersToTestMap.forEach((number, expectedResult) -> {
            MvcResult mvcResult;
            try {
                mvcResult = mockMvc.perform(get(CHECK_PERFECT_NUMBER_PATH, number)).andReturn();
                Assertions.assertEquals(200, (long) mvcResult.getResponse().getStatus());
                Assertions.assertEquals(expectedResult, Boolean.parseBoolean(mvcResult.getResponse().getContentAsString()));
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });

    }


    @Test
    public void getRangePerfectNumberTest() throws Exception {
        final String RANGE_PERFECT_NUMERS_PATH = "/perfect/number/range";

        MvcResult mvcResult = mockMvc.perform(get(RANGE_PERFECT_NUMERS_PATH).param("start", "1").param("end", "100")).andReturn();
        Assertions.assertEquals(200, (long) mvcResult.getResponse().getStatus());
        List<String> resultList = convertStringToList(mvcResult.getResponse().getContentAsString());
        List<String> expectedResultList = new ArrayList<String>() {{
            add("6");
            add("28");
        }};
        assertStringListResults(expectedResultList, resultList);

        mvcResult = mockMvc.perform(get(RANGE_PERFECT_NUMERS_PATH).param("start", "100").param("end", "500")).andReturn();
        Assertions.assertEquals(200, (long) mvcResult.getResponse().getStatus());
        resultList = convertStringToList(mvcResult.getResponse().getContentAsString());
        expectedResultList = new ArrayList<String>() {{
            add("496");
        }};
        assertStringListResults(expectedResultList, resultList);

        mvcResult = mockMvc.perform(get(RANGE_PERFECT_NUMERS_PATH).param("start", "1").param("end", "10000")).andReturn();
        Assertions.assertEquals(200, (long) mvcResult.getResponse().getStatus());
        resultList = convertStringToList(mvcResult.getResponse().getContentAsString());
        expectedResultList = new ArrayList<String>() {{
            add("6");
            add("28");
            add("496");
            add("8128");
        }};
        assertStringListResults(expectedResultList, resultList);
    }


    private List<String> convertStringToList(String toBeConverted) {
        return new ArrayList<>(Arrays.asList(toBeConverted.substring(1, toBeConverted.length() - 1).split(",")));
    }

    private void assertStringListResults(List<String> expectedList, List<String> actualList) {
        Assertions.assertEquals(expectedList.size(), actualList.size());
        expectedList.forEach(expectedResult -> Assertions.assertTrue(actualList.contains(expectedResult)));
    }
}
