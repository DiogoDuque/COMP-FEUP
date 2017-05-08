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

    HashMap<String,HashSet<NFiniteState>> getClosure(){
        return getClosure(new HashSet<>(), false);
    }

    private HashMap<String,HashSet<NFiniteState>> getClosure(HashSet<NFiniteState> alreadySeen, boolean onlyEpsilon){
        alreadySeen.add(this);
        HashSet<NFiniteState> epsilonTransitions = new HashSet<>();
        HashMap<String,HashSet<NFiniteState>> closure = new HashMap<>(); //closure for this state
        HashSet<Map.Entry<String, HashSet<NFiniteState>>> transitionPairs = new HashSet(transitions.entrySet()); //LinkedHashSet< Pair<value,List<state>> >


        for(Map.Entry<String, HashSet<NFiniteState>> transition : transitionPairs){ //handle each pair <value, List<state>>
            if(transition.getKey().equals(NFA.EPSILON)){ //compute and add all transitions derived from EPSILON
                HashSet<NFiniteState> states = transition.getValue();
                for(NFiniteState state : states){ //compute every EPSILON derivated state
                    if(alreadySeen.contains(state))
                        continue;

                    epsilonTransitions.add(this);
                    HashMap<String,HashSet<NFiniteState>> stateClosure = state.getClosure(alreadySeen, onlyEpsilon);
                    HashSet<Map.Entry<String, HashSet<NFiniteState>>> statePairs = new HashSet(stateClosure.entrySet());
                    for(Map.Entry<String, HashSet<NFiniteState>> tmpTransition : statePairs){ //compute every transition from a derived state
                        HashSet<NFiniteState> transitionClosure = closure.get(tmpTransition.getKey());
                        if(transitionClosure==null)
                            closure.put(tmpTransition.getKey(),tmpTransition.getValue());
                        else transitionClosure.addAll(tmpTransition.getValue());
                    }
                }

            } else if(!onlyEpsilon) { //add transitions to closure (if there are already some transitions for this key, dont replace but add)
                HashSet<NFiniteState> transitionClosure = closure.get(transition.getKey());
                if(transitionClosure == null)
                    closure.put(transition.getKey(), transition.getValue());
                else transitionClosure.addAll(transition.getValue());
                HashMap<String,HashSet<NFiniteState>> tmpEpsilonTransitions = getClosure(alreadySeen,true);
                epsilonTransitions.addAll(tmpEpsilonTransitions.get(NFA.EPSILON));
            }
        }

        if(closure.entrySet().size()==0 && onlyEpsilon)
            closure.put(NFA.EPSILON,epsilonTransitions);
        else for(Map.Entry<String, HashSet<NFiniteState>> entry : closure.entrySet()){
            entry.getValue().addAll(epsilonTransitions);
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
        String ret = new String();
        Set<Map.Entry<String, HashSet<NFiniteState>>> entrySet = transitions.entrySet();
        for(Map.Entry<String, HashSet<NFiniteState>> entry : entrySet){
            ret+="("+entry.getKey()+", "+entry.getValue().size()+")";
        }
        return ret;
    }
}