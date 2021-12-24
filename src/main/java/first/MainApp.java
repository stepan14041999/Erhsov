package first;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainApp {

    private static final int MULTIPLIER = 4;
    private static final Random random = new Random();

    public static void main(String[] args) {
        int threadCount;
        int numberCount;

        if (args.length < 2) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите число N: ");
            numberCount = scanner.nextInt();

            System.out.println("Введите число M: ");
            threadCount = scanner.nextInt();
        } else {
            numberCount = Integer.parseInt(args[0]);
            threadCount = Integer.parseInt(args[1]);
        }


        List<Integer> prepared = Stream.generate(() -> random.nextInt() / 8)
                .limit(numberCount)
                .collect(Collectors.toList());

        System.out.println(prepared);

        List<List<Integer>> parts = splitList(prepared, threadCount);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CompletableFuture[] completables = new CompletableFuture[threadCount];

        for (int i = 0; i < parts.size(); i++) {
            List<Integer> part = parts.get(i);
            completables[i] = CompletableFuture.runAsync(() -> {
                part.stream()
                        .map(number -> number * MULTIPLIER)
                        .forEach(System.out::println);
            }, executorService);
        }

        try {
            CompletableFuture.allOf(completables).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static List<List<Integer>> splitList(List<Integer> originalList, int listResultCount) {
        List<List<Integer>> partitions = new ArrayList<>();

        for (int i = 0; i < listResultCount; i++) {
            partitions.add(new ArrayList<>());
        }

        for (int i = 0; i < originalList.size(); i++) {
            Integer integer = originalList.get(i);

            partitions.get(i % listResultCount).add(integer);
        }

        return partitions;
    }

}
