/*
* This code was provided by
* (Robert Lafore. 2002.Data Structures and Algorithms in Java(2 ed.). Sams, Indianapolis, IN, USA)
* And modified for use in the CSCI 232 Assignment 1
* */

import java.util.Stack;

//////////////////////////////////////////////////////////////
class Node implements Comparable<Node> {
    public int iData;           // data item (key)
    public char cha;            // character associated with the node
    public double freq;        // data item - frequency of a character
    public Node leftChild;      // this Node's left child
    public Node rightChild;     // this Node's right child

    public int compareTo(Node that) {
        return (int) (this.freq - that.freq);
    }

    public Node() {

    }

    public static void printData(Node n){
        System.out.println(n.cha+" "+n.freq);
    }

    public Node(int i, double f, Node rChild, Node lChild) {
        iData = i + 1; // Adjusts char to actual value. I'm also leaving it as an int that is then cast to char, so I don't have to change any of the other tree code
        cha = (char) iData; // takes the value of iData and casts it back into a char
        freq = f;
    }

    public boolean isLeaf() { // Checks if the node is a leaf
        return ((leftChild == null) && (rightChild == null));
    }

    public void displayNode() { // display ourself
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(freq);
        System.out.print("} ");
    }
} // end Class Node
////////////////////////////////////////////////////////////////

class Tree {
    private Node root;                 // first Node of Tree

    public Tree() {                    // constructor
        root = null;                   // no nodes in tree yet
    }

    public void setRootRight(Node r) {
        Node parent;
        root.rightChild = r;
    }

    public void setRootLeft(Node l) {
        root.leftChild = l;
    }

    public void setParentToRoot(Node n) {
    }

    public Node find(int key) {      // find node with given key
        Node current = root;         // (assumes non-empty tree)
        while (current.iData != key) {          // while no match
            if (key < current.iData) {          // go left?
                current = current.leftChild;
            } else {                              // or go right?
                current = current.rightChild;
            }
            if (current == null)                 // if no child
            {                                   // didn't find it
                return null;
            }
        }
        return current;                         // found it
    }  //end find()


    public void insert(int id, double dd) {
        Node newNode = new Node();    // make new Node
        newNode.iData = id;           // insert data
        newNode.freq = dd;
        newNode.leftChild = null;
        newNode.rightChild = null;
        if (root == null) {            // no node in root
            root = newNode;
        } else {                        // root occupied
            Node current = root;      // start at root
            Node parent;
            while (true) {            // exits internally
                parent = current;
                if (id < current.iData) {              // go left?
                    current = current.leftChild;
                    if (current == null) {             // if the end of the line
                        parent.leftChild = newNode;   // insert on left
                        return;
                    }
                } //end if go left
                else {                                // or go right?
                    current = current.rightChild;
                    if (current == null)               // if the end of the line
                    {                                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    } // end insert()


    public boolean delete(int key) {             // delete node with given key
        Node current = root;                     // (assumes non-empty list)
        Node parent = root;
        boolean isLeftChild = true;

        while (current.iData != key) {           // search for Node
            parent = current;
            if (key < current.iData) {           // go left?
                isLeftChild = true;
                current = current.leftChild;
            } else {                               // or go right?
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) {                // end of the line,
                return false;                    // didn't find it
            }
        }
        //found the node to delete

        //if no children, simply delete it
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {              // if root,
                root = null;                    // tree is empty
            } else if (isLeftChild) {
                parent.leftChild = null;        // disconnect
            }                                   // from parent
            else {
                parent.rightChild = null;
            }
        }
        //if no right child, replace with left subtree
        else if (current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        }

        //if no left child, replace with right subtree
        else if (current.leftChild == null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        } else { // two children, so replace with inorder successor
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }

            //connect successor to current's left child
            successor.leftChild = current.leftChild;
        } // end else two children
        // (successor cannot have a left child)
        return true;              // success
    }// end delete()


    //returns node with next-highest value after delNode
    //goes right child, then right child's left descendants
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;        // go to the right child
        while (current != null) {                 // until no more
            successorParent = successor;          // left children
            successor = current;
            current = current.leftChild;
        }

        if (successor != delNode.rightChild) {    // if successor not right child,
            //make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }


    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal: ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
            default:
                System.out.print("Invalid traversal type\n");
                break;
        }
        System.out.println();
    }


    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }


    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }


    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }


    public void displayTree() {
        Stack<Node> globalStack = new Stack<Node>();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                ".................................................................");
        while (isRowEmpty == false) {
            Stack<Node> localStack = new Stack<Node>();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }

            while (globalStack.isEmpty() == false) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null ||
                            temp.rightChild != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }

                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(' ');
                }
            } // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            } // end while isRowEmpty is false
            System.out.println(
                    ".................................................................");
        } // end displayTree()
    } // end class Tree
}
////////////////////////////////////////////////////////////////

/*
class TreeApp {

	public static void main(String[] args) throws IOException {
		int value;

		Tree theTree = new Tree();

		theTree.insert(50, 1.5);
		theTree.insert(25, 1.2);
		theTree.insert(75, 1.7);
		theTree.insert(12, 1.5);
		theTree.insert(37, 1.2);
		theTree.insert(43, 1.7);
		theTree.insert(30, 1.5);
		theTree.insert(33, 1.2);
		theTree.insert(87, 1.7);
		theTree.insert(93, 1.5);
		theTree.insert(97, 1.5);

		while(true) {
			System.out.print("Enter first letter of show, ");
			System.out.print("insert, find, delete, or traverse ");
			int choice = getChar();
			switch(choice)
			{
			case 's':
				theTree.displayTree();
				break;

			case 'i':
				System.out.print("Enter the value to insert: ");
				value = getInt();
				theTree.displayTree();
				theTree.insert(value, value + 0.9);
				break;

			case 'f':
				System.out.print("Enter the value to find: ");
				value = getInt();
				Node found = theTree.find(value);
				if(found != null) {
					System.out.print("Found: ");
					found.displayNode();
					System.out.print("\n");
				}
				else {
					System.out.print("Could not find ");
					System.out.print(value + "\n");
				}
				break;

			case 'd':
				System.out.print("Enter the value to delete: ");
				value = getInt();
				boolean didDelete = theTree.delete(value);
				if (didDelete) {
					System.out.print("Deleted: " + value + '\n');
				}
				else {
					System.out.print("Could not delete value: " + value + '\n');
				}
				break;

			case 't':
				System.out.print("Enter type 1, 2, 3: ");
				value = getInt();
				theTree.traverse(value);
				break;
				
			default:
				System.out.print("Invalid entry\n");
				break;
			} // end switch
		} // end while
	} // end main()

	
	private static String getString() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	
	private static int getChar() throws IOException {
		String s = getString();
		return s.charAt(0);
	}
	
	private static int getInt() throws IOException {
		String s = getString();
		return Integer.parseInt(s);
	}	
}  // end TreeApp class
////////////////////////////////////////////////////////////////
*/
