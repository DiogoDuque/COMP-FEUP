package automata;

import java.util.*;

public class NFiniteState {
    private static int nextId = 0;
    private int id;
    private HashMap<String, HashSet<NFiniteState>> transitions;

    NFiniteState() {
        id = nextId++;
        transitions = new HashMap<String, HashSet<NFiniteState>>();
    }

    void addTransition(NFiniteState state, String input) {
        HashSet<NFiniteState> currentTransitions = transitions.get(input);
        if (currentTransitions == null) {
            HashSet<NFiniteState> next = new HashSet<>();
            next.add(state);
            transitions.put(input, next);
        } else {
            currentTransitions.add(state);
        }
    }

    void merge(NFiniteState other) {
        HashMap<String, HashSet<NFiniteState>> others = other.getTransitions();
        transitions.putAll(others);
    }

    HashSet<NFiniteState> getEpsilonClosure(){
        HashSet<NFiniteState> epsilonClosure = transitions.get(NFA.EPSILON);

        if(epsilonClosure!=null){
            HashSet<NFiniteState> epsilonClosureClone = new HashSet<NFiniteState>(epsilonClosure);
            for(NFiniteState state : epsilonClosureClone){
                HashSet<NFiniteState> tmpEpsilonClosure = state.getEpsilonClosure();
                epsilonClosure.addAll(tmpEpsilonClosure);
            }
        }


        return epsilonClosure==null ? new HashSet<>() : epsilonClosure;
    }

    HashMap<String,HashSet<NFiniteState>> getNormalClosure(){
        HashMap<String,HashSet<NFiniteState>> closure = (HashMap<String,HashSet<NFiniteState>>)transitions.clone();
        closure.remove(NFA.EPSILON);

        //add e-transitions after input
        for(Map.Entry<String,HashSet<NFiniteState>> transitionSet : closure.entrySet()){
            HashSet<NFiniteState> transitions = transitionSet.getValue(),
                                    eTransitions = new HashSet<>();
            for(NFiniteState state : transitions){
                eTransitions.addAll(state.getEpsilonClosure());
            }
            transitions.addAll(eTransitions);
        }

        return closure;
    }

    ArrayList<NFiniteState> getChildren() {
        ArrayList<NFiniteState> children = new ArrayList<>();
        for (HashSet<NFiniteState> transition : transitions.values()) {
            children.addAll(transition);
        }
        return children;
    }

    HashMap<String, HashSet<NFiniteState>> getTransitions() {
        return transitions;
    }

    int getId(){
        return id;
    }

    @Override
    public boolean equals(Object o){
        if(o==null)
            return false;

        NFiniteState state;
        if(o instanceof NFiniteState)
            state = (NFiniteState) o;
        else return false;

        return id==state.getId();
    }

    @Override
    public String toString() {
        String ret = "{id="+id;
        Set<Map.Entry<String, HashSet<NFiniteState>>> entrySet = transitions.entrySet();
        if(!entrySet.isEmpty())
            ret += ", transitions[";
        for(Map.Entry<String, HashSet<NFiniteState>> entry : entrySet){
            ret+="(k="+entry.getKey()+", [";
            for(NFiniteState state : entry.getValue()){
                ret+=state.getId()+", ";
            }
            ret=ret.substring(0,ret.length()-2);
            ret+="])";
        }
        if(!entrySet.isEmpty())
            ret += "]";
        ret += "}";
        return ret;
    }
}