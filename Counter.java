package org.example;

import java.io.*;
import java.util.*;

public class Counter {

    private final Map<String, List<Integer>> idx = new HashMap<>();

    public void makeIndex(String file, String out) throws Exception {
        parse(file);
        writeIndex(out);
    }

    private void parse(String book) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(book)))) {
            String text;
            while ((text = br.readLine()) != null) {
                process(text);
            }
        }
    }

    private void writeIndex(String out) throws Exception {
        try (FileWriter fw = new FileWriter(new File(out))) {
            Map<String, List<Integer>> temp = new TreeMap<>(idx);


            for (Map.Entry<String, List<Integer>> entry : temp.entrySet()) {
                fw.write(entry.getKey() + "\t" + entry.getValue().size() + "\n");
            }
        }
    }

    private void process(String line) {
        String[] words = line.split(";");
        for (String word : words) {
            addWord(word);
        }
    }

    private void addWord(String word) {
        int count = 1;
        List<Integer> list;
        if (idx.containsKey(word)) {
            list = idx.get(word);
        } else {
            list = new ArrayList<>();
        }
        list.add(count);
        idx.put(word, list);
    }

    public static void main(String[] args) throws Exception {
        String source = args[0];
        String destination = args[1];
        new Counter().makeIndex(source, destination);
    }
}
