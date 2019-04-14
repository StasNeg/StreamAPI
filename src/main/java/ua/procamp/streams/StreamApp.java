package ua.procamp.streams;

import ua.procamp.streams.stream.AsIntLazyStream;
import ua.procamp.streams.stream.AsIntStream;
import ua.procamp.streams.stream.IntStream;

public class StreamApp {

    public static int streamOperations(IntStream intStream) {
        int res = intStream
                .filter(x -> x > 0) // 1, 2, 3
                .map(x -> x * x) // 1, 4, 9
                .flatMap(x -> AsIntStream.of(x - 1, x, x + 1)) // 0, 1, 2, 3, 4, 5, 8, 9, 10
                .reduce(0, (a, b) -> a + b); // 42
        return res;
    }



    public static int[] streamToArray(IntStream intStream) {
        int[] intArr = intStream.toArray();
        return intArr;
    }

    public static String streamForEach(IntStream intStream) {
        StringBuilder str = new StringBuilder();
        intStream.forEach(x -> str.append(x));
        return str.toString();
    }

    public static void main(String[] args) {
        IntStream intStream = AsIntLazyStream.of(-1);
        intStream.forEach(x -> System.out.println(x));
        System.out.println("Minimum =" + intStream.min());
        System.out.println("Maximum =" + intStream.max());
        System.out.println("average =" + intStream.average());
        System.out.println("count =" + intStream.count());
        System.out.println("summ =" + intStream.sum());
        intStream.flatMap(x->AsIntStream.of(x - 1, x, x + 1)).forEach(i-> System.out.println("new_: " + i));
    }
}
