package ru.otus.homework.anton.sokolov;

public class Printer {

    public void go() throws InterruptedException {
        Monitor monitor = new Monitor();
        var thread1 = new Thread(new Print(monitor));
        var thread2 = new Thread(new Print(monitor));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

class Print implements Runnable {
    private final Monitor monitor;

    public Print(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        monitor.addThread(Thread.currentThread().getName());
        int counter = 0;
        while (counter < 10) {
            synchronized (monitor) {
                if (!monitor.canStart(threadName)) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
                    counter++;
                    monitor.wasStarted(threadName);
                    monitor.notify();
                }
            }
        }

        while (counter > 0) {
            synchronized (monitor) {
                if (!monitor.canStart(threadName)) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
                    counter--;
                    monitor.wasStarted(threadName);
                    monitor.notify();
                }
            }
        }
    }
}
