import java.util.AbstractMap.SimpleEntry;


public class BinarySearchTree<E extends Comparable<E>> {
    class Node {
        E value;
        Node leftChild = null;
        Node rightChild = null;
        Node(E value) {
            this.value = value;
        }
        @Override
        public boolean equals(Object obj) {
            if ((obj instanceof BinarySearchTree.Node) == false)
                return false;
            @SuppressWarnings("unchecked")
            Node other = (BinarySearchTree<E>.Node)obj;
            return other.value.compareTo(value) == 0 &&
                    other.leftChild == leftChild && other.rightChild == rightChild;
        }
    }

    protected Node root = null;

    protected void visit(Node n) {
        System.out.println(n.value);
    }

    public boolean contains(E val) {
        return contains(root, val);
    }

    protected boolean contains(Node n, E val) {
        if (n == null || val == null) return false;

        if (n.value.equals(val)) {
            return true;
        } else if (n.value.compareTo(val) > 0) {
            return contains(n.leftChild, val);
        } else {
            return contains(n.rightChild, val);
        }
    }

    public boolean add(E val) {
        if (root == null) {
            root = new Node(val);
            return true;
        }
        return add(root, val);
    }

    protected boolean add(Node n, E val) {
        if (n == null) {
            return false;
        }
        int cmp = val.compareTo(n.value);
        if (cmp == 0) {
            return false; // this ensures that the same value does not appear more than once
        } else if (cmp < 0) {
            if (n.leftChild == null) {
                n.leftChild = new Node(val);
                return true;
            } else {
                return add(n.leftChild, val);
            }
        } else {
            if (n.rightChild == null) {
                n.rightChild = new Node(val);
                return true;
            } else {
                return add(n.rightChild, val);
            }
        }
    }

    public boolean remove(E val) {
        return remove(root, null, val);
    }

    protected boolean remove(Node n, Node parent, E val) {
        if (n == null) return false;

        if (val.compareTo(n.value) == -1) {
            return remove(n.leftChild, n, val);
        } else if (val.compareTo(n.value) == 1) {
            return remove(n.rightChild, n, val);
        } else {
            if (n.leftChild != null && n.rightChild != null){
                n.value = maxValue(n.leftChild);
                remove(n.leftChild, n, n.value);
            } else if (parent == null) {
                root = n.leftChild != null ? n.leftChild : n.rightChild;
            } else if (parent.leftChild == n){
                parent.leftChild = n.leftChild != null ? n.leftChild : n.rightChild;
            } else {
                parent.rightChild = n.leftChild != null ? n.leftChild : n.rightChild;
            }
            return true;
        }
    }

    protected E maxValue(Node n) {
        Node currentNode = n == null ? root : n;
        if (currentNode.rightChild == null) {
            return currentNode.value;
        } else {
            return maxValue(currentNode.rightChild);
        }
    }


    /*********************************************
     *
     * IMPLEMENT THE METHODS BELOW!
     *
     *********************************************/


    // Method #1.
    public Node findNode(E val) {
        SimpleEntry<Node, Integer> nodeBundle = findNodeDepth(root, val);
        if (nodeBundle == null) {
            return null;
        }

        Node node = nodeBundle.getKey();
        return node;
    }

    // Method #2.
    protected int depth(E val) {
        SimpleEntry<Node, Integer> nodeBundle = findNodeDepth(root, val);
        if (nodeBundle == null) {
            return -1;
        }

        int depth = nodeBundle.getValue();
        return depth;
    }

    private SimpleEntry<Node, Integer> findNodeDepth(Node n, E val) {
        if (!contains(n, val)) {
            return null;
        }

        Node currentNode = n;
        int depth = 0;
        while (true) {
            Node nextNode = iterateTree(currentNode, val);
            if (nextNode == null) {
                break;
            }

            currentNode = nextNode;
            depth++;
        }

        SimpleEntry<Node, Integer> nodeBundle =
                new SimpleEntry<>(currentNode, depth);
        return nodeBundle;
    }

    private Node iterateTree(Node node, E val) {
        if (node == null || val == null) {
            throw new IllegalArgumentException();
        }

        E value = node.value;
        if (val.compareTo(value) == 0) {
            return null;
        }
        else if (val.compareTo(value) < 0) {
            return node.leftChild;
        }
        else {
            return node.rightChild;
        }
    }

    // Method #3.
    protected int height(E val) {
        Node node = findNode(val);
        if (node == null) {
            return -1;
        }

        int height = getGreatestHeight(node, 0);
        return height;
    }

    private int getGreatestHeight(Node n, int startingHeight) {
        Node currentNode = n;
        int height = startingHeight;
        while (hasChild(currentNode)) {
            // If we have a child, increment height regardless of branch.
            height++;
            if (hasBothChild(currentNode)) {
                int leftPath = getGreatestHeight(currentNode.leftChild, height);
                int rightPath = getGreatestHeight(currentNode.rightChild, height);
                height = leftPath > rightPath ? leftPath : rightPath;
                break;
            }
            else if (hasLeftChild(currentNode)) {
                currentNode = currentNode.leftChild;
            }
            else {
                currentNode = currentNode.rightChild;
            }
        }

        return height;
    }


    private boolean hasLeftChild(Node n) {
        return n.leftChild != null;
    }

    private boolean hasRightChild(Node n) {
        return n.rightChild != null;
    }

    private boolean hasChild(Node n) {
        return hasLeftChild(n) || hasRightChild(n);
    }

    private boolean hasBothChild(Node n) {
        return hasLeftChild(n) && hasRightChild(n);
    }

//    // Method #4.
    protected boolean isBalanced(Node n) {
        if (n == null || !contains(root, n.value)) {
            return false;
        }

        int heightLeft = -1;
        int heightRight = -1;
        if (hasLeftChild(n)) {
            heightLeft = height(n.leftChild.value);
        }

        if (hasRightChild(n)) {
            heightRight = height(n.rightChild.value);
        }

        int delta = Math.abs(heightLeft - heightRight);
        return delta == 0 || delta == 1;
    }

    // Method #5. .
    public boolean isBalanced() {
        boolean treeBalance = isBalanced(root);
        boolean leftBalance = isBalanced(root.leftChild);
        boolean rightBalance = isBalanced(root.rightChild);
        return treeBalance && leftBalance && rightBalance;
    }
}
