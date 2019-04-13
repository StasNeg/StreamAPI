package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.List;

public class AsIntLazyStream implements IntStream {

    private Generator intLazy;

    private AsIntLazyStream(Generator intLazy) {
        this.intLazy = intLazy;
    }


    public static IntStream of(int... values) {
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            temp.add(values[i]);
        }
        return new AsIntLazyStream(
                generatorContext ->
                        temp.forEach(item -> generatorContext.emit(item))
        );
    }


    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        return new AsIntLazyStream(generatorContext -> intLazy.generate(
                value -> func.applyAsIntStream(value).forEach(newValue -> generatorContext.emit(newValue))));
    }

    @Override
    public Double average() {
        return sum() / (count() * 1.0);
    }

    @Override
    public Integer max() {
        final Integer[] maxValue = new Integer[1];
        maxValue[0] = Integer.MIN_VALUE;
        intLazy.generate(x -> {
            if (maxValue[0] < x) maxValue[0] = x;
        });
        return maxValue[0];
    }

    @Override
    public Integer min() {
        final Integer[] minValue = new Integer[1];
        minValue[0] = Integer.MAX_VALUE;
        intLazy.generate(x -> {
            if (minValue[0] > x) minValue[0] = x;
        });
        return minValue[0];
    }

    @Override
    public long count() {
        final Integer[] sum = new Integer[1];
        sum[0] = 0;
        intLazy.generate(x -> {
            sum[0]++;
        });
        return sum[0];
    }

    @Override
    public Integer sum() {
        final Integer[] sum = new Integer[1];
        sum[0] = 0;
        intLazy.generate(x -> {
            sum[0] += x;
        });
        return sum[0];
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        return new AsIntLazyStream(generatorContext -> intLazy.generate(value -> {
            if (predicate.test(value)) {
                generatorContext.emit(value);
            }
        }));
    }

    @Override
    public void forEach(IntConsumer action) {
        intLazy.generate(action::accept);
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntLazyStream(generatorContext -> intLazy.generate(
                value -> generatorContext.emit(mapper.apply(value))
        ));
    }


    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        final Integer[] sum = new Integer[1];
        sum[0] = identity;
        intLazy.generate(x -> sum[0] = op.apply(sum[0], x));
        return sum[0];
    }

    @Override
    public int[] toArray() {
        List<Integer> res = new ArrayList<>();
        intLazy.generate(x -> res.add(x));
        int[] result = new int[res.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = res.get(i);
        }
        return result;
    }


}
