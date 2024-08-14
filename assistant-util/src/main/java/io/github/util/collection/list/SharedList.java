package io.github.util.collection.list;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SharedList<T> extends AbstractList<T> implements List<T>, Serializable {

    private static final int MAX_LENGTH = Integer.MAX_VALUE;

    private final int maxLength;

    private final int startPtr; // 共享数组开始的指针

    private int totalLength;

    private List<T> elementData;

    private List<SharedList<T>> sharedLists;

    private List<Integer> sizeList;


    private static <T> SharedList<T> freezeSharedList(List<T> list){
        return freezeSharedList(0, list);
    }

    /**
     * 定格list，当前的elementData为list，将当前的list变为共享list，maxLength为当前的size，startPtr为0
     * @param list
     * @param startPtr
     * @return SharedList
     */
    private static <T> SharedList<T> freezeSharedList(int startPtr, List<T> list){
        return freezeSharedList(list.size(), startPtr, list);
    }

    private static <T> SharedList<T> freezeSharedList(int startPtr, int length, List<T> list){
        return new SharedList<>(length, startPtr, list);
    }

    /**
     * 自由sharedList，当前elementData为空list，将传入的list放入共享list中。
     * @param startPtr
     * @param maxLength
     * @param sharedList
     * @return SharedList
     */
    private static <T> SharedList<T> freedomSharedList(int startPtr,int maxLength, List<T> sharedList){
        return new SharedList<>(maxLength, startPtr, new ArrayList<>(), freezeSharedList(sharedList));
    }

    public static <T> SharedList<T> slice(int index, int length, List<T> list){
        int size = list.size();
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException(String.format("index %s out of range [0,%s]", index, size));
        }
        if(length < 0 || index + length > size){
            throw new IndexOutOfBoundsException(String.format("slice index + length %s out of range [0,%s]", index + length, size));
        }

        return freezeSharedList(index, length, list);
    }

    public static <T> SharedList<T> slice(List<T> list){
        return freezeSharedList(0,  list.size(), list);
    }

    public static <T> SharedList<T> merge(List<T> list){
        return new SharedList<>(MAX_LENGTH, 0, list);
    }

    public static <T> SharedList<T> merge(List<T>...lists){
        List<SharedList<T>> sharedList = Arrays.stream(lists).map(SharedList::freezeSharedList).collect(Collectors.toList());
        return new SharedList<>(MAX_LENGTH, 0, new ArrayList<>(), sharedList);
    }

    private SharedList(List<T> elementData){
        this(MAX_LENGTH, 0, elementData, new ArrayList<>());
    }

    private SharedList(int maxLength, int startPtr, List<T> elementData){
        this(maxLength, startPtr, elementData, new ArrayList<>());
    }

    private SharedList(SharedList<T> sharedLists) {
        this(MAX_LENGTH, 0, new ArrayList<>(), new ArrayList<>(List.of(sharedLists)));
    }

    private SharedList(int maxLength, int startPtr, SharedList<T> sharedList) {
        this(maxLength, startPtr, new ArrayList<>(), new ArrayList<>(List.of(sharedList)));
    }

    private SharedList(int maxLength, int startPtr, List<T> elementData, SharedList<T> sharedList) {
        this(maxLength, startPtr, elementData, new ArrayList<>(List.of(sharedList)));
    }

    private SharedList(int maxLength, int startPtr, List<T> elementData, List<SharedList<T>> sharedLists) {
         this(maxLength, startPtr, elementData, sharedLists, true);
    }

    private SharedList(int maxLength, int startPtr, List<T> elementData, List<SharedList<T>> sharedLists, boolean needPutSelf){

        this.maxLength = maxLength;
        this.startPtr = startPtr;
        this.elementData = elementData;
        this.sharedLists = sharedLists;
        this.totalLength = 0;
        this.sizeList = new ArrayList<>();
        for (SharedList<T> sharedList : sharedLists) {
            appendSharedList(sharedList);
        }
        calculateTotalLength(Math.min(elementData.size() - startPtr, maxLength));
        this.sizeList.add(totalLength);
        if(needPutSelf){
            this.sharedLists.add(this);
        }
    }

    private void appendSharedList(SharedList<T> sharedList) {
          sharedLists.add(sharedList);
          calculateTotalLength(sharedList.totalLength());
          calculateSizeList(sharedList);
    }

    private void calculateTotalLength(int num){
        if(totalLength > maxLength - num){
            throw new RuntimeException(String.format("total length %s out of max length", num));
        }
        totalLength += num;
    }

    private void calculateSizeList(SharedList<T> sharedList){
        int sizeNum = sizeList.isEmpty()?sharedList.totalLength() : sharedList.totalLength() + sizeList.get(sizeList.size() - 1);
        sizeList.add(sizeNum);
    }

    public SharedList<T> subList(int index, int length){

        indexOutOfTotalLength(index);

        if(length < 0 || index + length > totalLength){
            throw new IndexOutOfBoundsException(String.format("slice length out of range [0,%s]", length));
        }

        return new SharedList<>(length, index, elementData, sharedLists, false);
    }

    int lastAccess = -1;

    private SharedList<T> findShared(int index){

        indexOutOfTotalLength(index);

        if(sharedLists.size() == 1){
            return this;
        }

        int ptr = index;
        if(lastAccess != -1 && inSizeRange(lastAccess, ptr)){
            return sharedLists.get(lastAccess);
        }

        int l = 0;
        int r = sizeList.size()-1;
        int mid = (l + r) >> 1;
        while(l < r){
            if(inSizeRange(mid, ptr)){
                lastAccess = mid;
                return sharedLists.get(mid);
            }else if(ptr >= sizeList.get(mid)){
                l = mid+1;
            }else{
                r = mid-1;
            }
        }
        throw new RuntimeException("cannot find correct shared list");
    }

    private boolean inSizeRange(int sizeIndex, int ptr){
         int l = sizeIndex == 0?0:sizeList.get(sizeIndex-1);
         int r = sizeList.get(sizeIndex);
         return ptr>=l&&ptr<r;
    }

    private int realPtr(int index){
        return startPtr + index;
    }

    private int endPtr(){
        return startPtr + totalLength;
    }

    @Override
    public T get(int index) {

        SharedList<T> shared = findShared(index);

        if(shared == this){
            int realPtr = realPtr(index);
            if(elementData.size() <= realPtr){
                return null;
            }
            return this.elementData.get(realPtr);
        }
        return shared.get(index);
    }

    @Override
    public int size() {
        return elementData.size();
    }

    public int totalLength(){
        return this.totalLength;
    }


    public void indexOutOfTotalLength(int index){

        int target;

        if(index < 0){
            throw new IndexOutOfBoundsException(String.format("index %s < 0", index));
        }

        if(index > (target = totalLength()-1)){
            throw new IndexOutOfBoundsException(String.format("index %s out of last index %s",index, target));
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("1", "2", "3", "4", "5"));
        List<String> list2 = new ArrayList<>(List.of("6", "7", "8", "9", "10"));
        SharedList<String> slice = SharedList.slice(0, 5, list);
        System.out.println(slice.get(1));
        SharedList<String> subSlice = slice.subList(1, 3);
        System.out.println(subSlice.get(1));
    }

}
