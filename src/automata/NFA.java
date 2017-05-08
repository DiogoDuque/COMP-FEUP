package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import parser.SimpleNode;

public class NFA {
    static final String EPSILON = "ε";

    private NFiniteState initialState;
    private NFiniteState finalState;

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
            case "Repeat":
                String type = (String) root.jjtGetValue();
                if (type.equals("*")) {
                    NFA = kleenestar(root);
                }
                if (type.equals("+")) {
                    NFA = kleeneplus(root);
                }
                if (type.equals("?")) {
                    NFA = optional(root);
                }
                break;
            case "PositiveSet":
                NFA = positiveset(root);
                break;
            case "Range":
                NFA = range(root);
                break;
            // TODO: Adicionar os restantess
        }
        return NFA;
    }

    public static NFA character(SimpleNode root) {
        NFA NFA = new NFA();
        String value = (String) root.jjtGetValue();
        
        NFiniteState q0 = new NFiniteState();
        NFiniteState q1 = new NFiniteState();
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

        NFiniteState q0 = new NFiniteState();
        NFiniteState q1 = new NFiniteState();

        q0.addTransition(left.getInitialState(), EPSILON);
        q0.addTransition(right.getInitialState(), EPSILON);
        left.getFinalState().addTransition(q1, EPSILON);
        right.getFinalState().addTransition(q1, EPSILON);

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

    public static NFA kleenestar(SimpleNode root) {
        NFA NFA = new NFA();
        SimpleNode child = (SimpleNode) root.jjtGetChild(0);
        NFA inner = init(child);
        
        NFiniteState q0 = new NFiniteState();
        NFiniteState q1 = new NFiniteState();

        q0.addTransition(q1, EPSILON);
        q0.addTransition(inner.getInitialState(), EPSILON);
        inner.getFinalState().addTransition(q1, EPSILON);
        inner.getFinalState().addTransition(inner.getInitialState(), EPSILON);

        NFA.setInitialState(q0);
        NFA.setFinalState(q1);
        return NFA;
    }

    public static NFA kleeneplus(SimpleNode root) {
        NFA NFA = new NFA();
        SimpleNode child = (SimpleNode) root.jjtGetChild(0);
        NFA inner = init(child);

        NFiniteState q0 = new NFiniteState();
        NFiniteState q1 = new NFiniteState();

        q0.addTransition(inner.getInitialState(), EPSILON);
        inner.getFinalState().addTransition(inner.getInitialState(), EPSILON);
        inner.getFinalState().addTransition(q1, EPSILON);

        NFA.setInitialState(q0);
        NFA.setFinalState(q1);
        return NFA;
    }

    public static NFA optional(SimpleNode root) {
        NFA NFA = new NFA();
        SimpleNode child = (SimpleNode) root.jjtGetChild(0);
        NFA inner = init(child);

        NFiniteState q0 = new NFiniteState();
        NFiniteState q1 = new NFiniteState();

        q0.addTransition(inner.getInitialState(), EPSILON);
        q0.addTransition(q1, EPSILON);
        inner.getFinalState().addTransition(q1, EPSILON);

        NFA.setInitialState(q0);
        NFA.setFinalState(q1);
        return NFA;
    }

    public static NFA positiveset(SimpleNode root) {
        NFA NFA = new NFA();
        int numChildren = root.jjtGetNumChildren();

        NFiniteState q0 = new NFiniteState();
        NFiniteState q1 = new NFiniteState();

        for (int i = 0; i < root.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) root.jjtGetChild(i);
            NFA inner = init(child);

            q0.addTransition(inner.getInitialState(), EPSILON);
            inner.getFinalState().addTransition(q1, EPSILON);
        }

        NFA.setInitialState(q0);
        NFA.setFinalState(q1);
        return NFA;
    }

    public static NFA range(SimpleNode root) {
        NFA NFA = new NFA();
        SimpleNode first = (SimpleNode) root.jjtGetChild(0);
        SimpleNode second = (SimpleNode) root.jjtGetChild(1);

        int begin = ((String) first.jjtGetValue()).charAt(0);
        int end = ((String) second.jjtGetValue()).charAt(0);

        NFiniteState q0 = new NFiniteState();
        NFiniteState q1 = new NFiniteState();

        for (int i = begin; i <= end; i++) {
            String value = Character.toString((char) i);
            q0.addTransition(q1, value);
        }

        NFA.setInitialState(q0);
        NFA.setFinalState(q1);
        return NFA;
    }

    public void setInitialState(NFiniteState state) {
        initialState = state;

    }

    public void setFinalState(NFiniteState state) {
        finalState = state;
    }

    public NFiniteState getInitialState() {
        return initialState;
    }

    public NFiniteState getFinalState() {
        return finalState;
    }

    public ArrayList<NFiniteState> getAllStates() {
        Stack<NFiniteState> frontier = new Stack<>();
        ArrayList<NFiniteState> visited =new ArrayList<>();
        NFiniteState initial = initialState;

        frontier.push(initial);
        while (!frontier.empty()) {
            NFiniteState current = frontier.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                ArrayList<NFiniteState> children = current.getChildren();
                for (NFiniteState child : children) {
                    frontier.add(child);
                }
            }
        }
        return visited;
    }

    public void display() {
        Graph graph = new MultiGraph("NFA");
        int nodes = 0;
        int edges = 0;

        String styles = "node { text-alignment: above; text-background-mode: plain; text-background-color: yellow; text-padding: 5, 0; size: 30px; }" +
                        "edge { text-alignment: along; text-background-mode: plain; text-background-color: yellow; text-size: 14; text-padding: 5, 0; }";
        graph.addAttribute("ui.stylesheet", styles);

        ArrayList<NFiniteState> states = getAllStates();

        for (NFiniteState state : states) {
            Node node = graph.addNode("N" + nodes++);
            if (state.equals(initialState)) {
                node.addAttribute("ui.label", "START");
            }
            if (state.equals(finalState)) {
                node.addAttribute("ui.label", "END");
            }
        }

        for (NFiniteState state : states) {
            String src = "N" + states.indexOf(state);
            HashMap<String, HashSet<NFiniteState>> transitions = state.getTransitions();
            for (String input : transitions.keySet()) {
                for (NFiniteState child : transitions.get(input)) {
                    String dst = "N" + states.indexOf(child);
                    Edge edge = graph.addEdge("E" + edges++, src, dst, true);
                    edge.addAttribute("ui.label", input);
                }
            }
        }

        graph.display();
    }
}