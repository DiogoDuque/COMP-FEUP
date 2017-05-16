package automata;

import java.util.*;

public class DFA {
	private DFiniteState initialState;
	private ArrayList<DFiniteState> finalStates;
    private HashSet<DFiniteState> allStates;

	public DFA(NFA nfa) {
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

        //TODO translate transitions from NFA_states to DFA_states
        //When translating, check if dfaState already exists (if a dfa_state already contains all nfa_states in this one) and handle it

        //DFiniteState dfaState = new DFiniteState(nfaStates, dfaTransitions);
        //states.add(dfaState);

        /*
        for(Map.Entry<String,HashSet<NFiniteState>> mapping : transitions.entrySet()){
            for(HashSet<NFiniteState> futureDfaState : mapping.getValue()){
                iterateThroughNFA(futureDfaState);
            }
        }
        */
    }
}