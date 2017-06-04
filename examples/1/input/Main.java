public class Main {
    public static void main(String[] args) {
        //@BasicBlock A
        int i = 0;
        for (i = 0; i < 10; i++) {
            //@BasicBlock B
            if (i % 2 == 0) {
                //@BasicBlock C
                System.out.println("Even!");
            } else {
                //@BasicBlock D
                System.out.println("Odd!");
            }
            //@BasicBlock E
            continue;
        }
        //@BasicBlock F
    }
}