package io.github.util.list;

import io.github.util.collection.list.SharedList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class SharedListJMHTest {

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();

    ArrayList<String> list = new ArrayList<>();

    Random random = new Random();

    SharedList<String> merge;
    @Setup
    public void setup() {
        for (int i = 0; i < 10000000; i++) {
            list1.add("a" + i);
            list2.add("A" + i);
            list3.add("b" + i);
        }
        merge = SharedList.SharedListBuilder.merge(list1, list2, list3);
        list.addAll(list1);
        list.addAll(list2);
        list.addAll(list2);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void foreachSharedList() {

        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(100000) + 1;
            merge.get(index);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void foreachCopyList() {
        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(100000) + 1;
            list.get(index);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SharedListJMHTest.class.getSimpleName())
                .forks(2)
                .warmupIterations(2)
                .measurementIterations(2)
                .build();

        new Runner(opt).run();
    }
}
