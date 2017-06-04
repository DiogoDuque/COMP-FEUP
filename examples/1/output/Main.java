

// default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)



public class Main {
    public static void main(String[] args) {
        // @BasicBlock A
        cflow.Cflow.next("A");
        int i = 0;
        for (i = 0; i < 10; i++) {
            // @BasicBlock B
            cflow.Cflow.next("B");
            if ((i % 2) == 0) {
                // @BasicBlock C
                cflow.Cflow.next("C");
                System.out.println("Even!");
            }else {
                // @BasicBlock D
                cflow.Cflow.next("D");
                System.out.println("Odd!");
            }
            // @BasicBlock E
            cflow.Cflow.next("E");
            continue;
        }
        // @BasicBlock F
        cflow.Cflow.next("F");
        cflow.Cflow.success();
    }
}

