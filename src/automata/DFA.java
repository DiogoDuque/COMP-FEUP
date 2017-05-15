package automata;

import java.util.*;

public class DFA {
	private DFiniteState initialState;
	private ArrayList<DFiniteState> finalStates;
    private HashSet<DFiniteState> allStates;

	public DFA(NFA nfa) {
        initialState = new DFiniteState(); //id 0
        NFiniteState root = nfa.getInitialState();
        iterateThroughNFA(root);
        finalStates = null;
    }


    private void iterateThroughNFA(NFiniteState state){
        /*DFiniteState dState; //get the DState to which transitions will be added
        if(statesMap.containsKey(setOfStates))
            dState=statesMap.get(setOfStates);
        else {
            dState = new DFiniteState();
            statesMap.put(setOfStates, dState);
        }*/

        HashSet<NFiniteState> epsilonClosure = state.getEpsilonClosure();
        System.out.println(epsilonClosure);

        
    }
}