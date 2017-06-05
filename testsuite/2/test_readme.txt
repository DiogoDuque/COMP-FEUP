1. Go to the project root

2. Run cflow:
	./cflow "AB(C(DE?)*)*FG*H" testsuite/2/input testsuite/2/output

3. Run the generated DFA and the program:
	cd testsuite/2/; ./run

4. See (positive) results:
	firefox html/index.html