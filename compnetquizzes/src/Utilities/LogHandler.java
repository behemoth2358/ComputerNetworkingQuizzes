package Utilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandler {
    private final static Logger LOGGER = Logger.getLogger("Computer networking quizzes");

    public static LogHandler Instance = new LogHandler();

    private LogHandler() {
        try {
            LOGGER.addHandler(new FileHandler(GlobalConstants.LOG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LogError(String log) {
        LOGGER.log(Level.SEVERE, log);
    }

    public void LogError(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
    }

    public void LogInfo(String log) {
        LOGGER.log(Level.INFO, log);
    }
}
