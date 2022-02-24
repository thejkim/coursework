import sort.QuickSort;

public class SortingAlgorithms {
    public static void main(String[] args) {
        int[] array = {2,4,6,3,1,5};
        QuickSort sort = new QuickSort(array);
        sort.sort();
        sort.printSortedData();
    }
}
