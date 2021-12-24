package second;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainApp {

    public static void main(String[] args) {
        if (args.length >= 1) {
            String arg = args[0];
            if ("serial".equals(arg)) {
                serial(args);
            } else {
                parallel(args);
            }
        }
    }

    private static void parallel(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberToScan;
        int threadCount;

        if (args.length >= 3) {
            numberToScan = Integer.parseInt(args[1]);
            threadCount = Integer.parseInt(args[2]);
        } else {
            System.out.println("Введите число N: ");
            numberToScan = scanner.nextInt();

            System.out.println("Введите число потоков: ");
            threadCount = scanner.nextInt();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        int[] simpleNumbers = new int[numberToScan];
        int partials = numberToScan / threadCount;

        Arrays.fill(simpleNumbers, 0);
        simpleNumbers[0] = 1;

        List<Future<?>> futures = new ArrayList<>();

        futures.add(executorService.submit(() -> {
            for (int i = 2; i <= partials; i++) {
                for (int j = i + 1; j <= numberToScan; j++) {
                    if (j % i == 0) {
                        simpleNumbers[j - 1] = 1;
                    }
                }
            }
        }));

        for (int k = 1; k < threadCount - 1; k++) {
            for (int i = partials * k + 1; i <= partials * (k + 1); i++) {
                for (int j = i + 1; j <= numberToScan; j++) {
                    if (j % i == 0) {
                        simpleNumbers[j - 1] = 1;
                    }
                }
            }
        }

        futures.add(executorService.submit(() -> {
            for (int i = partials * (threadCount - 1) + 1; i <= numberToScan; i++) {
                for (int j = i + 1; j <= numberToScan; j++) {
                    if (j % i == 0) {
                        simpleNumbers[j - 1] = 1;
                    }
                }
            }
        }));

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < simpleNumbers.length; i++) {
            int simpleNumber = simpleNumbers[i];

            if (simpleNumber == 0) {
                System.out.println(i + 1);
            }
        }
    }

    private static void serial(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberToScan;

        if (args.length < 2) {
            System.out.println("Введите число N: ");
            numberToScan = scanner.nextInt();
        } else {
            numberToScan = Integer.parseInt(args[1]);
        }

        int[] simpleNumbers = new int[numberToScan];

        Arrays.fill(simpleNumbers, 0);
        simpleNumbers[0] = 1;

        for (int i = 2; i <= numberToScan; i++) {
            for (int j = i + 1; j <= numberToScan; j++) {
                if (j % i == 0) {
                    simpleNumbers[j - 1] = 1;
                }
            }
        }

        for (int i = 0; i < simpleNumbers.length; i++) {
            int simpleNumber = simpleNumbers[i];

            if (simpleNumber == 0) {
                System.out.println(i + 1);
            }
        }
    }

}
