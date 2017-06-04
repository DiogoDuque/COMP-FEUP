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
        writer.println("\nimport java.io.*;");
        writer.println("\nimport java.nio.file.*;");
        writer.println("\nimport java.nio.charset.*;");
    }

    private void begin() {
        writer.println("\npublic class Cflow {");
    }

    private void initialState() {
        int state = dfa.getInitialState().getId();
        writer.println("\tpublic static int currentState = " + state + ";");
        writer.println("\n\tpublic static int initialState = " + state + ";");
    }

    private void numberOfStates() {
        int number = dfa.getAllStates().size();
        writer.println("\n\tpublic static int numberOfStates = " + number + ";");
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

    private void transitionsMade() {
        writer.println("\n\tpublic static ArrayList<String> transitionsMade;");
        writer.println("\tstatic {");
        writer.println("\t\ttransitionsMade = new ArrayList<>();");
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
        writer.println("\t\ttransitionsMade.add(input);");
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
        writer.println("\t\ttry {");
        writer.println("\t\tfrequency();");
        writer.println("\t\t\tString content = replace(read(\"output/cflow/html/templates/index.template\", StandardCharsets.UTF_8));");
        writer.println("\t\t\twrite(\"html/index.html\", content);");
        writer.println("\t\t} catch (IOException e) {");
        writer.println("\t\t\te.printStackTrace();");
        writer.println("\t\t}");
        writer.println("\t}");
    }

    private void read() {
        writer.println("\n\tpublic static String read(String path, Charset encoding) throws IOException {");
        writer.println("\t\tbyte[] encoded = Files.readAllBytes(Paths.get(path));");
        writer.println("\t\treturn new String(encoded, encoding);");
        writer.println("\t}");
    }

    private void write() {
        writer.println("\n\tpublic static void write(String path, String content) throws IOException {");
        writer.println("\t\tnew File(path).getParentFile().mkdirs();");
        writer.println("\t\tPrintWriter out = new PrintWriter(path);");
        writer.println("\t\tout.println(content);");
        writer.println("\t\tout.close();");
        writer.println("\t}");
    }

    private void replace() {
        writer.println("\n\tpublic static String replace(String content) {");
        writer.println("\t\treturn content.replace(\"{{ dfa-states }}\", Integer.toString(numberOfStates))");
        writer.println("\t\t\t.replace(\"{{ dfa-final-states }}\", Integer.toString(finalStates.size()))");
        writer.println("\t\t\t.replace(\"{{ dfa-transitions }}\", Integer.toString(transitionsMade.size()));");
        writer.println("\t}");
    }

    /*
    Set<String> unique = new HashSet<String>(list);
    for (String key : unique) {
        System.out.println(key + ": " + Collections.frequency(list, key));
    }
    */
    private void frequency() {
        writer.println("\n\tpublic static void frequency() throws IOException {");
        writer.println("\t\tString data = \"name\\tvalue\\n\";");
        writer.println("\t\tSet<String> unique = new HashSet<String>(transitionsMade);");
        writer.println("\t\tfor (String key : unique) {");
        writer.println("\t\t\tdata += key + \"\\t\" + Collections.frequency(transitionsMade, key) + \"\\n\";");
        writer.println("\t\t}");
        writer.println("\t\twrite(\"html/data.tsv\", data);");
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
        numberOfStates();
        finalStates();
        transitionsMade();
        transitions();
        next();
        success();
        read();
        write();
        replace();
        frequency();
        end();
    }
}