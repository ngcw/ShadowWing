package findarray;

public class MyFindArray implements FindArray {
    public int findArray(int[] array, int[] subArray) {
    	int i;
        for (i = 0; i < array.length; i++){
            for (int j = 0; j < subArray.length; j++){
                    if (array[i+j] != subArray[j]){
                        return -1;
                    }
                }
        }
        return i;
    }
}