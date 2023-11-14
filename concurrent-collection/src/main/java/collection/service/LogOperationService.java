package collection.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogOperationService implements AutoCloseable{
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final String insertFileName = "inserted.txt";
    private final String deleteFileName = "deleted.txt";

    public void logInsert(Object o) {
        executorService.execute(() -> {
            try {
                Files.writeString(Paths.get(insertFileName),
                        o.toString() + System.lineSeparator(),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                System.out.println(Thread.currentThread().getName() + " Insert: " + o);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void logDelete(Object o) {
        executorService.execute(() -> {
            try {
                Files.writeString(Paths.get(deleteFileName),
                        o.toString() + System.lineSeparator(),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                System.out.println(Thread.currentThread().getName() + " Delete: " + o);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void close() {
        executorService.shutdown();
    }
}
