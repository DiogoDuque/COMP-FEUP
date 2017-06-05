1. Go to the project root

2. Run cflow:
	./cflow "A(BCE|BDE)+F" testsuite/1/input testsuite/1/output

3. Run the generated DFA and the program:
	cd testsuite/1/; ./run

4. See (positive) results:
	firefox html/index.html