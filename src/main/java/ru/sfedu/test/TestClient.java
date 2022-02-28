package ru.sfedu.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class TestClient {
    private static final Logger log = LogManager.getLogger(TestClient.class);

    public TestClient() {
        log.debug("TestClient: starting application");
    }

    public static void main(String[] args) throws IOException {
    }

}
