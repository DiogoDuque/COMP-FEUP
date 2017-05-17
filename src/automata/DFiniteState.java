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

    void addTransition(String input, DFiniteState state) {
        DFiniteState currentTransition = transitions.get(input);
        if (currentTransition == null)
            transitions.put(input, state);
    }

    ArrayList<Integer> getNfaStatesIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(NFiniteState state : nfaStates){
            ids.add(state.getId());
        }
        return ids;
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

    boolean isComposedBy(HashSet<NFiniteState> nfaStates){
        if(this.nfaStates.size() != nfaStates.size())
            return false;
        for(NFiniteState state : nfaStates){
            if(!this.nfaStates.contains(state))
                return false;
        }
        return true;
    }

    boolean isComposedBy(NFiniteState nfaState){
        return this.nfaStates.contains(nfaState);
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
        String s = "{id="+id+"(";
        for(NFiniteState state : nfaStates){
            s+=state.getId()+", ";
        }
        s = s.substring(0,s.length()-2);
        s+=")"+", [";

        for(Map.Entry<String, DFiniteState> transition : transitions.entrySet()){
            s += "("+transition.getKey()+", "+transition.getValue().getId()+"), ";
        }

        if(transitions.entrySet().size()!=0)
            s = s.substring(0,s.length()-2);

        s += "]}";

        return s;
    }

    /**
     * Makes a transition.
     * @param input value used to trigger the transition.
     * @return returns the new DFiniteState, null if the transition does not exist.
     */
    public DFiniteState transitionThrough(String input){
        return transitions.get(input);
    }
}