package automata;

import java.util.*;

public class DFA {
	private DFiniteState initialState;
	private ArrayList<DFiniteState> finalStates;
    private HashSet<DFiniteState> allStates;

	public DFA(NFA nfa) {
	    allStates = new HashSet<>();
        NFiniteState root = nfa.getInitialState();
        HashSet<NFiniteState> rootSet = new HashSet<>();
        rootSet.add(root);
        iterateThroughNFA(rootSet);
        finalStates = null;
    }


    private void iterateThroughNFA(HashSet<NFiniteState> states){
	    //get the set of NFA_states that will be a DFA_state
        HashSet<NFiniteState> nfaStates = new HashSet<>(states);
        for(NFiniteState state : states){
            nfaStates.addAll(state.getEpsilonClosure());
        }

        //get the transitions from all NFA_states
        HashMap<String,HashSet<NFiniteState>> transitions = new HashMap<>();
        for(NFiniteState state : nfaStates){ //check each state
            HashMap<String,HashSet<NFiniteState>> stateTransitions = state.getNormalClosure();
            for(Map.Entry<String,HashSet<NFiniteState>> transitionsSet : stateTransitions.entrySet()){ //check each transition
                if(transitions.containsKey(transitionsSet.getKey())) //se já existem transicoes com esta chave
                    transitions.get(transitionsSet.getKey()).addAll(transitionsSet.getValue()); //adicionar as novas às antigas
                else transitions.put(transitionsSet.getKey(),transitionsSet.getValue()); //senao, inserir nova transicao
            }
        }

        //check if DFA with these NFA_states already exists. If so, add transitions. Else, create state and add transitions
        DFiniteState dfaState = getDfaState(nfaStates);
        if(dfaState != null){ //if dfaState already exists, just add transitions
            int noOfTransitions = dfaState.getTransitions().size();
            HashMap<String, DFiniteState> dfaTransitions = mapNfaTransitionsToDfa(transitions);
            for(Map.Entry<String, DFiniteState> dfaTransition : dfaTransitions.entrySet()){
                boolean worked = dfaState.addTransition(dfaTransition.getKey(), dfaTransition.getValue());
                if(!worked)
                    System.out.println("DEBUG: COULD NOT ADD TRANSITION");
            }
            if(noOfTransitions == dfaState.getTransitions().size()) //no new transitions were added
                return;
        } else {//translate transitions from NFA_states to DFA_states, and create new DFA_state
            dfaState = new DFiniteState(nfaStates, mapNfaTransitionsToDfa(transitions));
            allStates.add(dfaState);
        }

        for(Map.Entry<String, DFiniteState> tmpTransition : dfaState.getTransitions().entrySet()) {
            iterateThroughNFA(tmpTransition.getValue().getNfaStates());
        }

    }

    private HashMap<String,DFiniteState> mapNfaTransitionsToDfa(HashMap<String,HashSet<NFiniteState>> nfaTransitions){
        HashMap<String,DFiniteState> dfaTransitions = new HashMap<>();

        for(Map.Entry<String,HashSet<NFiniteState>> nfaTransition : nfaTransitions.entrySet()){ //map each input's transition
            String input = nfaTransition.getKey();
            HashSet<NFiniteState> nfaStates = nfaTransition.getValue();
            DFiniteState dfaState = getDfaState(nfaStates);
            if(dfaState == null) {
                dfaState = new DFiniteState(nfaStates);
                allStates.add(dfaState);
            }
            dfaTransitions.put(input,dfaState);
        }

        return dfaTransitions;
    }

    private DFiniteState getDfaState(HashSet<NFiniteState> nfaStates){
        for(DFiniteState dfaState : allStates){
            if(dfaState.compareState(nfaStates))
                return dfaState;
        }
        return null;
    }

    public HashSet<DFiniteState> getAllStates(){
        return allStates;
    }
}