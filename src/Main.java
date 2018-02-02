/*
* Authors: Alex, Ethan, Zan
*
* Overview: Huffman Code generating/decoding algorithm. CSCI 232 Assignment 1
* */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PriorityQueue;

public class Main {

    private Charset charset = Charset.forName("US-ASCII");
    private String[] huffCodes = new String[256];
    private int[] freq = new int[256];
    private String line;
    private Tree t;
    private String code = "";
    private String encoded = "";

    private Path file = Paths.get("input/input.txt");
    private Path outFile = Paths.get("output/output.txt");
    // Sorry if this relative path doesn't work. It's always been iffy for me with intellij
    // If it doesn't work, feel free to paste in the full path for the input file

    public Main() {

    }


    // Takes the frequency array and feeds any characters with a frequency greater than 0 into the PriorityQueue
    public void makeQueue() {
        PriorityQueue<Node> q = new PriorityQueue(256);
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                Node n = new Node(i, freq[i], null, null);
                q.add(n);
            }
        }
        t = new Tree(buildTree(q));
    }

    // Takes an input of a PriorityQueue and converts it into a Huffman Tree
    public Node buildTree(PriorityQueue queue) {
        while (queue.size() > 1) {
            //Node.printData((Node)queue.poll());
            Node root = new Node();
            Node l = (Node) queue.poll();
            Node r = (Node) queue.poll();
            int fTot = (int) (l.freq + r.freq);
            root.freq = fTot;
            root.leftChild = l;
            root.rightChild = r;
            queue.add(root);
        }
        return (Node) queue.poll();
    }

    public void makeTable(String[] tab, String s, Node n) {
        if (!n.isLeaf()) {
            makeTable(tab, s + '0', n.leftChild);
            makeTable(tab, s + '1', n.rightChild);
        } else
            tab[n.cha] = s;
    }

    public void countChars() {
        try (BufferedReader read = Files.newBufferedReader(file, charset)) {
            while ((line = read.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    freq[(int) line.charAt(i)] += 1;
                }
                freq[(int)'\n'] +=1;
            }
            read.close();
        } catch (IOException x) {
            System.err.format("IOException %s%n", x);
        }
    }

    public void encode() {
        String temp;
        try (BufferedReader read = Files.newBufferedReader(file, charset)) {
            while ((line = read.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    encoded += huffCodes[(int) line.charAt(i)];
                }
                encoded += huffCodes[10];
            }
            encoded = encoded.substring(0, encoded.length()-huffCodes[10].length());
            System.out.println(encoded);
            read.close();
        } catch (IOException x) {
            System.err.format("IOException %s%n", x);
        }
    }

    public void decode() {
        Node n = t.getRoot();
        try(BufferedWriter writer = Files.newBufferedWriter(outFile,charset)) {
            for (int i = 0; i < encoded.length(); i++) {

                if (encoded.charAt(i) == '0') {
                    n = n.leftChild;
                } else if (encoded.charAt(i) == '1') {
                    n = n.rightChild;
                }
                if (n.isLeaf()) {
                    System.out.print((char)n.iData);
                    writer.write((char)n.iData);
                    n = t.getRoot();
                }
            }
        }catch(IOException x){
            System.err.format("IOException %s%n",x);
        }
    }

    public void printTable(){
        System.out.println("\nCharacter,  Frequency,  Code");
        for(int i = 0; i < huffCodes.length ;i++){
            if(freq[i]!=0){
                System.out.println(((char) i) + "     "+freq[i]+",    "+huffCodes[i]);
            }

        }
    }


    public void start() {
        countChars();
        makeQueue();
        //t.displayTree(); // Apparently the treeApp program wasn't really designed to display unbalanced trees, it becomes super unclear at around level 3 of the tree
        makeTable(huffCodes, code, t.getRoot());
        encode();
        decode();
        printTable();
    }

    public static void main(String[] args) {
        new Main().start();
    }

}
