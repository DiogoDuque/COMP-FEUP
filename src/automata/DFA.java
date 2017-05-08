package automata;

import java.util.*;

public class DFA {
	private DFiniteState initialState;
	private ArrayList<DFiniteState> finalStates;

	private DFA() {
        initialState = null;
        finalStates = null;
    }

    public static DFA init(NFA nfa){
        HashMap<HashSet<NFiniteState>,DFiniteState> statesMap = new HashMap<>(); //Map<NStatesSet,DState> //DFA State comes from a set of NFA States
    	HashSet<NFiniteState> initialState = new HashSet<>();
        initialState.add(nfa.getInitialState());
    	iterateThroughNFA(statesMap, initialState);

        return new DFA(); //TODO return the real DFA
    }

    private static void iterateThroughNFA(HashMap<HashSet<NFiniteState>,DFiniteState> statesMap, HashSet<NFiniteState> setOfStates){
        DFiniteState dState; //get the DState to which transitions will be added
        if(statesMap.containsKey(setOfStates))
            dState=statesMap.get(setOfStates);
        else {
            dState = new DFiniteState();
            statesMap.put(setOfStates, dState);
        }

        HashMap<String,HashSet<NFiniteState>> transitionsMap = new HashMap<>();
        for(NFiniteState nState : setOfStates){ //add transitions do dState
            HashMap<String,HashSet<NFiniteState>> closure = nState.getClosure();
            Set<Map.Entry<String, HashSet<NFiniteState>>> entrySet = closure.entrySet();
            for(Map.Entry<String, HashSet<NFiniteState>> entry : entrySet){
                String value = entry.getKey();
                HashSet<NFiniteState> states = entry.getValue();
                if(transitionsMap.containsKey(value))
                    transitionsMap.get(value).addAll(states);
                else transitionsMap.put(value,states);
            }
        }

        //TODO continue iteration

        //DEBUG
        Set<Map.Entry<String, HashSet<NFiniteState>>> entrySet = transitionsMap.entrySet();
        for(Map.Entry<String, HashSet<NFiniteState>> entry : entrySet){
            System.out.println("key="+entry.getKey()+", "+entry.getValue());
        }
    }
}