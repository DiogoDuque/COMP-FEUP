package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DFiniteState {
    private static int nextId = 0;
    private HashSet<NFiniteState> nfaStates;
    private HashMap<String, DFiniteState> transitions;
    private int id;

    DFiniteState(HashSet<NFiniteState> nfaStates, HashMap<String, DFiniteState> transitions) {
        this.nfaStates = new HashSet<>();
        this.nfaStates.addAll(nfaStates);
        this.transitions = transitions;
        id=nextId;
        nextId++;
    }

    DFiniteState(HashSet<NFiniteState> nfaStates) {
        this.nfaStates = new HashSet<>();
        this.nfaStates.addAll(nfaStates);
        this.transitions = new HashMap<>();
        id=nextId;
        nextId++;
    }

    boolean addTransition(String input, DFiniteState state) {
        DFiniteState currentTransition = transitions.get(input);
        if (currentTransition == null) {
            transitions.put(input, state);
            return true;
        } else return false;
    }


    HashMap<String, DFiniteState> getTransitions() {
        return transitions;
    }

    int getId(){
        return id;
    }

    HashSet<NFiniteState> getNfaStates() {
        return nfaStates;
    }

    boolean compareState(HashSet<NFiniteState> nfaStates){
        if(nfaStates.size() != nfaStates.size())
            return false;
        for(NFiniteState state : nfaStates){
            if(!nfaStates.contains(state))
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

    @Override
    public String toString(){
        String s = "{id="+id+", [";
        for(Map.Entry<String, DFiniteState> transition : transitions.entrySet()){
            s += "("+transition.getKey()+", "+transition.getValue().getId()+"), ";
        }

        if(transitions.entrySet().size()!=0)
            s = s.substring(0,s.length()-2);

        s += "]}";

        return s;
    }
}