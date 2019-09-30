package ru.uglic.troncwest;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.uglic.troncwest.testdata.TestData;

@SpringBootTest
abstract public class AbstractSpringTest {
    @Autowired
    protected TestData testData;
    protected static boolean isClearSqlAfterEachTest;

    @AfterEach
    void afterEachForModifyingTest() {
        if (isClearSqlAfterEachTest) {
            testData.restoreAll();
        }
    }
}