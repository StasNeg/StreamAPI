package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.List;

public class AsIntStream implements IntStream {

    private List<Integer> intStreamList;

    private AsIntStream(List<Integer> intStreamList) {
        this.intStreamList = intStreamList;
        // To Do
    }


    public static IntStream of(int... values) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            res.add(values[i]);
        }

        return new AsIntStream(res);
    }

    @Override
    public Double average() {
        return sum() / (count() * 1.0);
    }

    @Override
    public Integer max() {
        int max = Integer.MIN_VALUE;
        for (Integer value : this.intStreamList) {
            if (max < value) max = value;
        }
        return max;
    }

    @Override
    public Integer min() {
        int minValue = Integer.MAX_VALUE;
        for (Integer value : this.intStreamList) {
            if (minValue > value) minValue = value;
        }
        return minValue;
    }

    @Override
    public long count() {
        return this.intStreamList.size();
    }

    @Override
    public Integer sum() {
        int sum = 0;
        for (int i = 0; i < this.intStreamList.size(); i++) {
            sum += this.intStreamList.get(i);

        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        List<Integer> result = new ArrayList<>();
        for (Integer value : this.intStreamList) {
            if (predicate.test(value)) {
                result.add(value);
            }
        }
        return new AsIntStream(result);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (Integer value : this.intStreamList) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        List<Integer> result = new ArrayList<>();
        for (Integer value : this.intStreamList) {
            result.add(mapper.apply(value));
        }
        return new AsIntStream(result);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        List<Integer> result = new ArrayList<>();
        for (Integer value : this.intStreamList) {
            int[] array = func.applyAsIntStream(value).toArray();
            for (int i = 0; i < array.length; i++) {
                result.add(array[i]);
            }
        }
        return new AsIntStream(result);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int start = identity;
        for (Integer value : this.intStreamList) {
            start = op.apply(start, value);
        }
        return start;
    }

    @Override
    public int[] toArray() {
        int[] res = new int[this.intStreamList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = this.intStreamList.get(i);
        }
        return res;
    }


}
