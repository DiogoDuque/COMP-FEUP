package automata;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;

public class DFA {
	private DFiniteState initialState;
	private ArrayList<DFiniteState> finalStates;
    private HashSet<DFiniteState> allStates;

	public DFA(NFA nfa) {
	    allStates = new HashSet<>();
	    finalStates = new ArrayList<>();
        NFiniteState root = nfa.getInitialState();
        HashSet<NFiniteState> rootSet = new HashSet<>();
        rootSet.add(root);
        iterateThroughNFA(rootSet);
        addInitialState(nfa);
        addFinalState(nfa);
    }

    private void addFinalState(NFA nfa) {
        NFiniteState nfaFinalState = nfa.getFinalState();
        for(DFiniteState dfaState : allStates){
            if(dfaState.isComposedBy(nfaFinalState)){
                finalStates.add(dfaState);
            }
        }
    }

    private void addInitialState(NFA nfa) {
        NFiniteState nfaInitialState = nfa.getInitialState();
        for(DFiniteState dfaState : allStates){
            if(dfaState.isComposedBy(nfaInitialState)){
                initialState = dfaState;
                return;
            }
        }
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
                dfaState.addTransition(dfaTransition.getKey(), dfaTransition.getValue());
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
            if(dfaState.isComposedBy(nfaStates))
                return dfaState;
        }
        return null;
    }

    public HashSet<DFiniteState> getAllStates(){
        return allStates;
    }

    public void display() {
        Graph graph = new MultiGraph("DFA");
        int nodes = 0;
        int edges = 0;

        String styles = "node { text-alignment: above; text-background-mode: plain; text-background-color: yellow; text-padding: 5, 0; size: 30px; }" +
                "edge { text-alignment: along; text-background-mode: plain; text-background-color: yellow; text-size: 14; text-padding: 5, 0; }";
        graph.addAttribute("ui.stylesheet", styles);

        ArrayList<DFiniteState> states = new ArrayList<>(getAllStates());

        for (DFiniteState state : states) {
            Node node = graph.addNode("N" + nodes++);
            node.addAttribute("ui.label",state.getId());
            boolean isInitialState = state.equals(initialState), isFinalState = finalStates.contains(state);
            if (isInitialState) {
                if(isFinalState)
                    node.addAttribute("ui.label", "START/END("+state.getNfaStatesIds()+")");
                else node.addAttribute("ui.label", "START("+state.getNfaStatesIds()+")");
            } else if (isFinalState) {
                node.addAttribute("ui.label", "END("+state.getNfaStatesIds()+")");
            }
        }

        for (DFiniteState state : states) {
            String src = "N" + states.indexOf(state);

            HashMap<String, DFiniteState> transitions = state.getTransitions();
            for(Map.Entry<String, DFiniteState> transition : transitions.entrySet()){
                String dst = "N" + states.indexOf(transition.getValue());
                Edge edge = graph.addEdge("E" + edges++, src, dst, true);
                edge.addAttribute("ui.label", transition.getKey());
            }
        }

        graph.display();
    }
}