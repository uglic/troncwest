package ru.uglic.troncwest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import ru.uglic.troncwest.testdata.TestData;

@SpringBootTest
abstract public class AbstractSpringTest {
    @Autowired
    protected TestData testData;
    protected static boolean isClearSqlAfterEachTest;

    @BeforeEach
    void beforeEachModifyingTest() {
        if (isClearSqlAfterEachTest) {
            if (TestTransaction.isActive()) {
                TestTransaction.flagForRollback();
                LoggerFactory.getLogger(AbstractSpringTest.class).info("Test transaction manually rolled back");
                TestTransaction.end();
            }
        }
    }

    @AfterEach
    void afterEachForModifyingTest() {
        if (isClearSqlAfterEachTest) {
            testData.restoreAll();
        }
    }
}