package ru.netology;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int[] receipt1 = createRandomArray(30);
        int[] receipt2 = createRandomArray(20);
        int[] receipt3 = createRandomArray(40);

        long sum1 = getSumElements(receipt1);
        long sum2 = getSumElements(receipt2);
        long sum3 = getSumElements(receipt3);

        System.out.println("Массив 1. Сумма элементов: " + sum1);
        System.out.println("Массив 2. Сумма элементов: " + sum2);
        System.out.println("Массив 3. Сумма элементов: " + sum3);
        System.out.println("Ожидаемый результат: " + (sum1 + sum2 + sum3));

        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        LongAdder stat = new LongAdder();
        Arrays.stream(receipt1).forEach(i -> executorService.submit(() -> stat.add(i)));
        Arrays.stream(receipt2).forEach(i -> executorService.submit(() -> stat.add(i)));
        Arrays.stream(receipt3).forEach(i -> executorService.submit(() -> stat.add(i)));

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Полученный результат: " + stat.longValue());
        executorService.shutdown();
    }

    private static int[] createRandomArray(int count) {
        int[] array = new int[count];
        Random random = new Random();
        for (int i=0; i < array.length; i++){
            array[i] = random.nextInt(50);
        }
        /*System.out.println("Создан массив из " + count + " элементов:");
        System.out.println(Arrays.toString(array));*/

        return array;
    }

    public static long getSumElements(int[] array) {
        /*int result = 0;
        for (int i=0; i < array.length; i++){
            result += array[i];
        }*/

        return Arrays.stream(array).sum();
    }
}
