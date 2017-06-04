public class Euclid {

    public static int gcd(int p, int q) {
        //@BasicBlock C
        while (q != 0) {
            //@BasicBlock D
            int temp = q;
            q = p % q;
            p = temp;
        }
        //@BasicBlock E
        return p;
    }
}