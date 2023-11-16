package collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try (LoggedList<String> loggedList = new LoggedList<>(new ArrayList<>())) {
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            List<Callable<Object>> taskCollection = new ArrayList<>();
            for (int i = 0; i < 2000; i++) {
                int finalI = i;
                taskCollection.add(() -> loggedList.add("test" + finalI));
            }
            executorService.invokeAll(taskCollection);
            logger.info(loggedList.toString());
            executorService.shutdown();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

