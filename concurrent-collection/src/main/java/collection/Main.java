package collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        try (LoggedList<String> loggedList = new LoggedList<>(new ArrayList<>())) {

            ExecutorService executorService = Executors.newFixedThreadPool(5);

            List<Callable<Object>> taskCollection = new ArrayList<>();
            for (int i = 0; i < 2000; i++) {
                int finalI = i;
               taskCollection.add(() -> loggedList.add("test" + finalI));
            }

            executorService.invokeAll(taskCollection);
            System.out.println(loggedList);
            executorService.shutdown();
            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}