1. Go to the project root

2. Run cflow:
	./cflow "AC(BC)*(D|E)F" testsuite/3/input testsuite/3/output

3. Run the generated DFA and the program:
	cd testsuite/3/; ./run

4. See (positive) results:
	firefox html/index.html