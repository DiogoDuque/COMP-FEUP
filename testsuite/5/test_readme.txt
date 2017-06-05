1. Go to the project root

2. Run cflow:
	./cflow "ABC(D)?(E(F)?)*G?(H|I)J" testsuite/5/input testsuite/5/output

3. Run the generated DFA and the program:
	cd testsuite/5/; ./run

4. See (positive) results:
	firefox html/index.html