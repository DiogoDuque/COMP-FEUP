package automata;

import java.util.*;

public class NFiniteState {
    private static int nextId = 0;
    private int id;
    private HashMap<String, ArrayList<NFiniteState>> transitions;

    NFiniteState() {
        id = nextId++;
        transitions = new HashMap<String, ArrayList<NFiniteState>>();
    }

    void addTransition(NFiniteState state, String input) {
        ArrayList<NFiniteState> currentTransitions = transitions.get(input);
        if (currentTransitions == null) {
            ArrayList<NFiniteState> next = new ArrayList<>();
            next.add(state);
            transitions.put(input, next);
        } else {
            currentTransitions.add(state);
        }
    }

    void merge(NFiniteState other) {
        HashMap<String, ArrayList<NFiniteState>> others = other.getTransitions();
        transitions.putAll(others);
    }

    private HashMap<String,HashSet<NFiniteState>> getClosure(){
        HashMap<String,HashSet<NFiniteState>> closure = new HashMap<>(); //closure for this state
        HashSet<Map.Entry<String, HashSet<NFiniteState>>> transitionPairs = new HashSet(transitions.entrySet()); //LinkedHashSet< Pair<value,List<state>> >

        for(Map.Entry<String, HashSet<NFiniteState>> transition : transitionPairs){ //handle each pair <value, List<state>>
            if(transition.getKey().equals(NFA.EPSILON)){ //compute and add all transitions derived from EPSILON
                HashSet<NFiniteState> states = transition.getValue();
                for(NFiniteState state : states){ //compute every EPSILON derivated state
                    HashMap<String,HashSet<NFiniteState>> stateClosure = state.getClosure();
                    HashSet<Map.Entry<String, HashSet<NFiniteState>>> statePairs = new HashSet(stateClosure.entrySet());
                    for(Map.Entry<String, HashSet<NFiniteState>> tmpTransition : statePairs){ //compute every transition from a derived state
                        HashSet<NFiniteState> transitionClosure = closure.get(tmpTransition.getKey());
                        if(transitionClosure==null)
                            closure.put(tmpTransition.getKey(),tmpTransition.getValue());
                        else transitionClosure.addAll(tmpTransition.getValue());
                    }
                }

            } else { //add transitions to closure
                HashSet<NFiniteState> transitionClosure = closure.get(transition.getKey());
                if(transitionClosure == null)
                    closure.put(transition.getKey(), transition.getValue());
                else transitionClosure.addAll(transition.getValue());
            }
        }

        return closure;
    }

    ArrayList<NFiniteState> getChildren() {
        ArrayList<NFiniteState> children = new ArrayList<>();
        for (ArrayList<NFiniteState> transition : transitions.values()) {
            children.addAll(transition);
        }
        return children;
    }

    HashMap<String, ArrayList<NFiniteState>> getTransitions() {
        return transitions;
    }

    private int getId(){
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
}