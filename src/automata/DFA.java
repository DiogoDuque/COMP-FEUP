package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Comparator;

public class DFA {
	private DFiniteState initialState;
	private ArrayList<DFiniteState> finalStates;

	private DFA() {
        initialState = null;
        finalStates = null;
    }

    public void init(NFA nfa){
    	Comparator<DFiniteState> comparator = new Comparator<DFiniteState>(){
    		@Override
    		public int compare(DFiniteState state1, DFiniteState state2){
    			return state1.getId()-state2.getId();
    		}
    	};
    	TreeSet<DFiniteState> setTemplate = new TreeSet<DFiniteState>(comparator);
    	HashMap<TreeSet<DFiniteState>,Integer> dfaStates;
    	NFiniteState nfaInitialState = nfa.getInitialState();
    	//TODO start iteration for closures
    }
}