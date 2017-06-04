public class Main {
    public static void main(String[] args) {

        //@BasicBlock A
        int[] arrayToSort = {5, 1, 3, 0, 10, 8, 111};
        bubbleSort(arrayToSort);
        
        for(int number : arrayToSort){
            //@BasicBlock G
            System.out.println(number);
        }

        //@BasicBlock H
    }

    public static void bubbleSort(int[] numArray) {
        //@BasicBlock B
        int n = numArray.length;
        int temp = 0;

        for (int i = 0; i < n; i++) {
            //@BasicBlock C
            for (int j = 1; j < (n - i); j++) {
                //@BasicBlock D
                if (numArray[j - 1] > numArray[j]) {
                    //@BasicBlock E
                    temp = numArray[j - 1];
                    numArray[j - 1] = numArray[j];
                    numArray[j] = temp;
                }
            }
        }
        //@BasicBlock F
    }
}
