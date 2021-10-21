package ru.sfedu.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Test.utils.ConfigurationUtil;

import java.io.IOException;

public class TestClient {
    private static Logger log = LogManager.getLogger(TestClient.class);

    public static void main (String[] args) throws IOException {
        logBasicSystemInfo();

        log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));
        log.info(ConfigurationUtil.getConfigurationEntry(Constants.APP_NAME));
        log.info(ConfigurationUtil.getConfigurationEntry(Constants.PATH_TO_HOME));

        log.error("Heyyy");
    }

    public TestClient() {
        log.debug("ru.sfedu.Test.TestClient: starting application.........");
    }

    private static void logBasicSystemInfo(){
        log.info("Launching the application...");
        log.info("Operating System: " + System.getProperty("os.name") + " "
                        + System.getProperty("os.version"));
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
    }

}
