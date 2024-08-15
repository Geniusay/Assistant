package io.github.util.list;


import io.github.util.collection.list.SharedList;
import org.junit.Test;

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
       List<String> list2 = new ArrayList<>(List.of("X", "D", "F","X","Y"));
       SharedList<String> sharedList = SharedList.SharedListBuilder.merge(list, list2);

       //System.out.println(sharedList);
       SharedList<String> slice = SharedList.SharedListBuilder.slice(2, 6,sharedList);
        System.out.println(slice);
        SharedList<String> merge = SharedList.SharedListBuilder.merge(list, list2, slice);
        System.out.println(merge);
        System.out.println(merge.get(1));
        SharedList<String> subList = merge.subList(4, 5);
        System.out.println(subList);
        subList.set(1, "hello");
        System.out.println(subList);
        System.out.println(merge);
    }
}
