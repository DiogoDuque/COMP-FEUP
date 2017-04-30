package automata;

import java.util.ArrayList;

import automata.FiniteState;
import parser.CFlowTreeConstants;
import parser.SimpleNode;

public class NFA {
    private FiniteState initialState;
    private FiniteState finalState;

    private NFA() {
        initialState = null;
        finalState = null;
    }

    public static NFA init(SimpleNode root) {
        NFA NFA = new NFA();
        switch (root.toString()) {
            case "Expression":
                NFA = init((SimpleNode) root.jjtGetChild(0));
                break;
            case "Char":
                NFA = character(root);
                break;
            case "Union":
                NFA = union(root);
                break;
            case "Concat":
                NFA = concatenation(root);
                break;
            // TODO: Adicionar os restantess
        }
        return NFA;
    }

    public static NFA character(SimpleNode root) {
        NFA NFA = new NFA();
        String value = (String) root.jjtGetValue();
        
        FiniteState q0 = new FiniteState();
        FiniteState q1 = new FiniteState();
        q0.addTransition(q1, value);

        NFA.setInitialState(q0);
        NFA.setFinalState(q1);
        return NFA;
    }

    public static NFA union(SimpleNode root) {
        NFA NFA = new NFA();
        SimpleNode first = (SimpleNode) root.jjtGetChild(0);
        SimpleNode second = (SimpleNode) root.jjtGetChild(1);

        NFA left = init(first);
        NFA right = init(second);

        FiniteState q0 = new FiniteState();
        FiniteState q1 = new FiniteState();

        q0.addTransition(left.getInitialState(), null);
        q0.addTransition(right.getInitialState(), null);
        left.getFinalState().addTransition(q1, null);
        right.getFinalState().addTransition(q1, null);

        NFA.setInitialState(q0);
        NFA.setFinalState(q1);
        return NFA;
    }

    public static NFA concatenation(SimpleNode root) {
        NFA NFA = new NFA();
        SimpleNode first = (SimpleNode) root.jjtGetChild(0);
        SimpleNode second = (SimpleNode) root.jjtGetChild(1);

        NFA left = init(first);
        NFA right = init(second);
        left.getFinalState().merge(right.getInitialState());

        NFA.setInitialState(left.getInitialState());
        NFA.setFinalState(right.getFinalState());
        return NFA;
    }

    public void setInitialState(FiniteState state) {
        initialState = state;

    }

    public void setFinalState(FiniteState state) {
        finalState = state;
    }

    public FiniteState getInitialState() {
        return initialState;
    }

    public FiniteState getFinalState() {
        return finalState;
    }

    public void dump() {
        if (initialState != null) {
            initialState.dump("");
        }
    }
}