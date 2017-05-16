package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DFiniteState {
    private static int nextId = 0;
    private HashSet<Integer> nfaStatesIds;
    private HashMap<String, DFiniteState> transitions;
    private int id;

    DFiniteState(HashSet<NFiniteState> nfaStates, HashMap<String, DFiniteState> transitions) {
        nfaStatesIds = new HashSet<>();
        addNfaStatesIds(nfaStates);
        this.transitions = transitions;
        id=nextId;
        nextId++;
    }

    private void addNfaStatesIds(HashSet<NFiniteState> nfaStates){
        for(NFiniteState state : nfaStates)
            nfaStatesIds.add(state.getId());
    }

    public boolean addTransition(DFiniteState state, String input) {
        DFiniteState currentTransition = transitions.get(input);
        if (currentTransition == null) {
            transitions.put(input, state);
            return true;
        } else return false;
    }

    public ArrayList<DFiniteState> getChildren() {
        ArrayList<DFiniteState> children = new ArrayList<>();
        for (DFiniteState transition : transitions.values()) {
            children.add(transition);
        }
        return children;
    }

    public HashMap<String, DFiniteState> getTransitions() {
        return transitions;
    }

    int getId(){
        return id;
    }

    public boolean compareState(HashSet<NFiniteState> nfaStates){
        if(nfaStates.size() != nfaStatesIds.size())
            return false;
        for(NFiniteState state : nfaStates){
            if(!nfaStatesIds.contains(state.getId()))
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o){
        if(o==null)
            return false;

        DFiniteState state;
        if(o instanceof DFiniteState)
            state = (DFiniteState) o;
        else return false;

        return id==state.getId();
    }
}