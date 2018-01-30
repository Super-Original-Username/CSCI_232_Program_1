/*
* Authors: Alex, Ethan, Zan
*
* Overview: Huffman Code generating/decoding algorithm. CSCI 232 Assignment 1
* */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    public static void foo() {
        System.out.println("Oh boi here I come!");
    }

    private Charset charset = Charset.forName("US-ASCII");
    private String[] huffCodes = new String[255];
    private int[] freq = new int[255];
    private String line;

    private Path file = Paths.get("/input/input.txt");
    // Sorry if this relative path doesn't work. It's always been iffy for me with intellij
    // If it doesn't work, feel free to paste in the hard path for the input file

    public Main() {

    }

    // Takes the frequency array and feeds any characters with a frequency greater than 0 into the PriorityQueue
    public void makeQueue() {
        PriorityQueue q = new PriorityQueue(255);
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                Node n = new Node();
                n.iData = i;
                n.dData = freq[i];
                q.add(n);
            }
        }
        buildTree(q);
    }

    // Takes an input of a PriorityQueue and converts it into a Huffman Tree
    public void buildTree(PriorityQueue queue) {
        while (queue.peek() != null) {
            Tree t = new Tree();
        }
    }

    public void countChars() {
        try (BufferedReader read = Files.newBufferedReader(file, charset)) {
            while ((line = read.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    freq[(int) line.charAt(i)] += 1;
                }
            }
        } catch (IOException x) {
            System.err.format("IOException %s%n", x);
        }
    }

    public void Start() {
        countChars();

    }

    public static void main(String[] args) {
        Tree huff = new Tree(); // Initializes the tree that will be used to generate the Huffman Code
        PriorityQueue q = new PriorityQueue();
        foo();
    }

}
