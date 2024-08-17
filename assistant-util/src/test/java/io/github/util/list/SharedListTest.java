package io.github.util.list;


import io.github.common.SafeBag;
import io.github.util.collection.list.SharedList;
import org.junit.Test;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class SharedListTest {

    private List<String> list = new ArrayList<>(List.of("a", "b", "c","e","f"));

    @Test
    public void testSliceSharedList(){
        SharedList<String> sharedList = SharedList.SharedListBuilder.slice(1, 3, list);
        System.out.println(sharedList);
        System.out.println(sharedList.get(0));
        System.out.println(sharedList.get(1));
        System.out.println(sharedList.get(3));
    }

    @Test
    public void testFreedomList(){
        SharedList<String> freedomList = SharedList.SharedListBuilder.freedomList(1, list);
        System.out.println(freedomList);
        freedomList.add("x");
        freedomList.add("y");
        freedomList.add("z");
        System.out.println(freedomList);

        freedomList.set(0,"hello");
        System.out.println(list);
        System.out.println(freedomList);
    }

    @Test
    public void testMergeSharedList(){
        List<String> list1 = new ArrayList<>(List.of("a", "b", "c","d","e"));
        List<String> list2 = new ArrayList<>(List.of("X", "D", "F","X","Y"));
        System.out.println("list1: "+list1);
        System.out.println("list2: "+list2);

        SharedList<String> mergeList = SharedList.SharedListBuilder.merge(list1, list2);

        System.out.println("merge list1 and list2 : "+ mergeList);

        SharedList<String> slice = SharedList.SharedListBuilder.slice(2, 6, mergeList);
        System.out.println("slice mergeList from 2 to 6 : "+ slice);

        SharedList<String> merge2List = SharedList.SharedListBuilder.merge(list1, list2, slice);
        System.out.println("merge list1 and list2 and slice : "+ merge2List);
        System.out.println("merge2 get index 1: "+ merge2List.get(1));

        SharedList<String> subList = merge2List.subList(4, 5);
        System.out.println("merge2 subList from 4 to 5 :"+ subList);

        subList.set(1, "hello");
        System.out.println("sublist after set sublist index 1 to hello: "+subList);
        System.out.println("merge2 after set sublist index 1 to hello: "+merge2List);
    }

    @Test
    public void testTime() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> copyList = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            list1.add("a" + i);
            list2.add("A" + i);
            list3.add("b" + i);
        }

        SafeBag<SharedList<String>> sharedListSafeBag = new SafeBag<>();

        logTime(() -> {
            sharedListSafeBag.setData(SharedList.SharedListBuilder.merge(list1, list2, list3));
        }, "merge");

        logTime(() -> {
            copyList.addAll(list1);
            copyList.addAll(list2);
            copyList.addAll(list3);
        }, "copy");


        logTime(() -> {
            int i = 0;
            for (String datum : copyList) {
                i++;
            }
            System.out.println(i);
        }, "foreach copy");

        logTime(() -> {
            int i = 0;
            SharedList<String> data = sharedListSafeBag.getData();
            for (String datum : data) {
                i++;
            }
            System.out.println(i);
        }, "foreach merge");
    }

    public void logTime(Runnable runnable,String name){
        long time = System.currentTimeMillis();
        runnable.run();
        System.out.println(name + " time: "+(System.currentTimeMillis()-time));
    }
}
