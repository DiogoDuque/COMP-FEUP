public class Main {
    public static void main(String[] args){	
        //@BasicBlock A
        int random = getRandom(1, 100);

		if (isPrime(random)) {
			//@BasicBlock H
			System.out.println(random + " is a prime number!");
		}
		else {
			//@BasicBlock I
			System.out.println( random + " is not a prime number!");
		}
		//@BasicBlock J
	}

	public static int getRandom(int min, int max) {
		//@BasicBlock B
		return (int)(Math.random() * (max+1-min)) + min;
	}

	public static boolean isPrime(int n) {
		//@BasicBlock C
		if (n % 2 == 0){
			//@BasicBlock D
			return false;
		}
		
		for(int i = 3; i * i <= n; i += 2) {
			//@BasicBlock E
			if(n % i == 0){
				//@BasicBlock F
				return false;
			}
		}
		//@BasicBlock G
		return true;
	}
}