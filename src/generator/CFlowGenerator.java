package generator;

import java.io.PrintWriter;
import java.io.File;

import automata.DFA;
import automata.DFiniteState;

public class CFlowGenerator {
    private DFA dfa;
    private PrintWriter writer;

    public CFlowGenerator(DFA dfa, String path) throws Exception {
        this.dfa = dfa;
        if (path.substring(path.length() - 1).equals("/")) {
            new File(path + "cflow").mkdirs();
            writer = new PrintWriter(path + "cflow/Cflow.java");
        } else {
            new File(path + "/cflow").mkdirs();
            writer = new PrintWriter(path + "/cflow/Cflow.java");
        }
    }

    private void imports() {
        writer.println("package cflow;");
        writer.println("\nimport java.util.*;");
    }

    private void begin() {
        writer.println("\npublic class Cflow {");
    }

    private void initialState() {
        int state = dfa.getInitialState().getId();
        writer.println("\tpublic static int currentState = " + state + ";");
        writer.println("\n\tpublic static int initialState = " + state + ";");
    }

    private void finalStates() {
        writer.println("\n\tpublic static ArrayList<Integer> finalStates;");
        writer.println("\tstatic {");
        writer.println("\t\tfinalStates = new ArrayList<>();");
        for (DFiniteState dfastate : dfa.getFinalStates()) {
            int state = dfastate.getId();
            writer.println("\t\tfinalStates.add(" + state + ");");
        }
        writer.println("\t}");
    }

    private void transitions() {
        writer.println("\n\tpublic static Map<Integer, Map<String, Integer>> transitions;");
        writer.println("\tstatic {");
        writer.println("\t\ttransitions = new HashMap<>();");
        for (DFiniteState dfastate : dfa.getAllStates()) {
            int state = dfastate.getId();
            writer.println("\n\t\tHashMap<String, Integer> transitions" + state + " = new HashMap<>();");
            dfastate.getTransitions().forEach((k, v) -> {
                writer.println("\t\ttransitions" + state + ".put(\"" + k + "\", " + v.getId() + ");");
            });
            writer.println("\t\ttransitions.put(" + state + ", transitions" + state + ");");
        }
        writer.println("\t}");
    }

    private void next() {
        writer.println("\n\tpublic static void next(String input) {");
        writer.println("\t\tif (transitions.get(currentState) != null && transitions.get(currentState).get(input) != null) {");
        writer.println("\t\t\tcurrentState = transitions.get(currentState).get(input);");
        writer.println("\t\t} else {");
        writer.println("\t\t\tcurrentState = -1;");
        writer.println("\t\t}");
        writer.println("\t}");
    }

    private void success() {
        writer.println("\n\tpublic static void success() {");
        writer.println("\t\tif (finalStates.contains(currentState)) {");
        writer.println("\t\t\tSystem.out.println(\"[CFLOW: Success]\");");
        writer.println("\t\t} else {");
        writer.println("\t\t\tSystem.out.println(\"[CFLOW: Failed]\");");
        writer.println("\t\t}");
        writer.println("\t}");
    }

    private void end() {
        writer.println("}");
        writer.close();
    }

    public void generate() {
        imports();
        begin();
        initialState();
        finalStates();
        transitions();
        next();
        success();
        end();
    }
}