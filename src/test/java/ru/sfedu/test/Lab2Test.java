package ru.sfedu.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.test.lab2.api.DataProviderHibernate;
import ru.sfedu.test.lab2.model.Beeean;
import ru.sfedu.test.lab2.model.Neeested;

import java.util.Date;

public class Lab2Test {
    private static final Logger log = LogManager.getLogger(Lab2Test.class);
    DataProviderHibernate dataProvider = new DataProviderHibernate();

    @Test
    public void test() {
        Beeean beeean = new Beeean(1,
                "New",
                new Date(2022, 2, 28),
                true,
                new Neeested());
        log.info(dataProvider.appendEntity(beeean));
        log.info(dataProvider.getAllEntity());
    }
}
