package sort;

public class QuickSort extends Sort {
    private int[] unsortedData;
    private int[] sortedData = null;

    public QuickSort() {
        this.unsortedData = null;
    }

    public QuickSort(int[] numbers) {
        setUnsortedData(numbers);
    }

    @Override
    public int[] getUnsortedData() {
        return unsortedData;
    }

    @Override
    public void setUnsortedData(int[] numbers) {
        this.unsortedData = numbers;
        this.sortedData = null;
    }

    @Override
    public void printUnsortedData() {
        for(int i = 0; i< unsortedData.length; i++) {
            System.out.print(unsortedData[i]+" ");
        }
        System.out.println();
    }
    private void swapSortedData(int origin, int destination) {
        int temp = sortedData[origin];
        sortedData[origin] = sortedData[destination];
        sortedData[destination] = temp;
    }

    private int partition(int left, int right) {
        int first = left;
        int pivot = sortedData[first];
        left++;
        /*
            After the while loop, the value at the index 'left-1' must be swapped with the pivot.
            Must check if 'left' and 'right' are the same.
            If so, must make 'right' is pointing the value, 1 index prior to 'left' (left-1).
         */
        while(left<=right) {
            while(sortedData[left]<pivot && left<right) {
                left++;
            }
            while(sortedData[right]>pivot && right>=left) {
                right--;
            }
            if(left < right) {
                swapSortedData(left, right);
            } else {
                break; // readability
            }
        }
        swapSortedData(first, right);
        return right;
    }

    private void performSort(int left, int right) {
        if(left < right) {
            int index = partition(left, right);
            performSort(left, index - 1);
            performSort(index + 1, right);
        }
    }

    @Override
    public int[] sort() {
        sortedData = getUnsortedData();
        performSort(0, unsortedData.length-1);
        return sortedData;
    }

    @Override
    public void printSortedData() {
        for(int i=0; i<sortedData.length; i++) {
            System.out.print(sortedData[i] + " ");
        }
        System.out.println();
    }

    @Override
    public int[] getSortedData() {
        return sortedData;
    }
}
