public class Main {
    public static void main(String[] args){	
        //@BasicBlock A
        int p = getRandom(1, 20);
        int q = getRandom(21, 40);
        int d  = Euclid.gcd(p, q);
        System.out.println("gcd(" + p + ", " + q + ") = " + d);
		//@BasicBlock F
    }

	public static int getRandom(int min, int max) {
		//@BasicBlock B
		return (int)(Math.random() * (max+1-min)) + min;
	}

}