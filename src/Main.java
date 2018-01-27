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

public class Main {
    public static void foo() {
        System.out.println("Oh boi here I come!");
    }
    private Charset charset = Charset.forName("US-ASCII");
    private int[] huffCodes;

    private Path file = Paths.get("/input/input.txt");
    // Sorry if this relative path doesn't work. It's always been iffy for me with intellij
    // If it doesn't work, feel free to paste in the hard path for the input file

    public Main() {

    }

    public void countChars(){
        try(BufferedReader read = Files.newBufferedReader(file, charset)){
        }catch(IOException x){
            System.err.format("IOException %s%n",x);
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
