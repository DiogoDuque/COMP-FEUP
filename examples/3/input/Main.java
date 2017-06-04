import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //@BasicBlock A
        int[] arrayToSort = new int[100];
        int searchValue = randomFill();

        for(int i : arrayToSort){
            //@BasicBlock B
            arrayToSort[i] = randomFill();
        }

        Arrays.sort(arrayToSort);
        
        int index = Arrays.binarySearch(arrayToSort, searchValue);

        if (index < 0) {
            //@BasicBlock D
            System.out.println("The element " + searchValue + " was not found.");
        }
        else {
            //@BasicBlock E
            System.out.println("The index of element " + searchValue + " is: " + index);
        }
        //@BasicBlock F
    }

    public static int randomFill(){
        //@BasicBlock C
        Random rand = new Random();
        int randomNum = rand.nextInt();
        return randomNum;
    }
}