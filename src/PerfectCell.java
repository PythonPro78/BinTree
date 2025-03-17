import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class PerfectCell<T extends Comparable<? super T>>
{
    private T type;

    private Node head;
    public final int height;
    public final int numNodes;

    public void hi()
    {
        System.out.println(head.getLeftChild().getRightChild().get());
    }

    // CONSTRUCTORS
    public PerfectCell(int height, T defualt)
    {
        this.height = height;
        numNodes = (int) Math.pow(2, height) - 1;

        if (height == 0)
        {
            head = null;
            return;
        }

        head = new Node(defualt, null);

        for (int i = 1; i <= (int)Math.pow(2, height-1) - 1; i ++)
        {
            Node current = atIndex(i);

            current.setLeftChild(new Node(defualt, current));
            current.setRightChild(new Node(defualt, current));
        }
    }

    public PerfectCell(int height)
    {
        this.height = height;
        numNodes = (int) Math.pow(2, height) - 1;

        if (height == 0)
        {
            head = null;
            return;
        }

        head = new Node(null, null);

        for (int i = 1; i <= numNodes; i ++)
        {
            Node current = atIndex(i);

            current.setLeftChild(new Node(null, current));
            current.setRightChild(new Node(null, current));
        }
    }

    // GETTER SETTER
    public T get(int index)
    {
        Node node = atIndex(index);

        if (node == null)
            return null;

        return node.get();
    }

    public T get(String path)
    {
        return get(codeToInt(path));
    }

    public ArrayList<T> getRow(int row)
    {
        if (row >= height || row < 0) return null;

        int width = (int)Math.pow(2, row);
        ArrayList<T> arr = new ArrayList<>();

        for (int i = 0; i < width; i ++)
        {
            arr.add(get((1<<row) + i));
        }

        return arr;
    }

    public ArrayList<T> getPath(int index)
    {
        if (index >= numNodes || index < 0) return null;

        ArrayList<T> arr = new ArrayList<>();

        while (index > 0)
        {
            arr.addFirst(get(index));

            index = index >> 1;
        }

        return arr;
    }

    public ArrayList<T> getPath(String path)
    {
        return getPath(codeToInt(path));
    }

    public void set(int index, T value)
    {
        Node node = atIndex(index);

        if (node == null)
            return;

        node.set(value);
    }

    public void set(String path, T value)
    {
        set(codeToInt(path), value);
    }

    // BRAIN METHODS
    private Node atIndex(int index)
    {
        Node current = head;
        int digit = (int)(Math.log(index)/Math.log(2)) - 1;

        if (index == 0 || index == 1) return head;

        while (current.getLeftChild() != null && digit >= 0)
        {
            int dir = index & (1 << digit);

            current = dir == 0 ? current.getLeftChild() : current.getRightChild();

            digit --;
        }

        return current;
    }

    public int linearSearch(T value)
    {
        for (int i = 0; i < numNodes; i ++)
            if (get(i) == value)
                return i;

        return -1;
    }

    public int binarySearch(T value)
    {
        Node current = head;
        int index = 1;

        for (int i = 0; i < height; i ++)
        {
            if (current.get().compareTo(value) == 0)
                return index;

            if (current.get().compareTo(value) < 0)
            {
                current = current.getRightChild();
                index = (index << 1) + 1;
            }
            else
            {
                current = current.getLeftChild();
                index = (index << 1);
            }
        }

        return -1;
    }

    public void sort()
    {
        ArrayList<T> sorted = sort(head);

        fillInOrder(head, sorted, 1);
    }

    private ArrayList<T> sort(Node start)
    {
        if (start.getLeftChild() == null)
        {
            ArrayList<T> arr = new ArrayList<>();
            arr.add(start.get());
            return arr;
        }

        ArrayList<T> leftBranch = sort(start.getLeftChild());
        ArrayList<T> rightBranch = sort(start.getRightChild());

        ArrayList<T> sorted = new ArrayList<>();
        boolean addedStart = false;

        int leftI = 0, rightI = 0;
        while (leftI < leftBranch.size() || rightI < rightBranch.size() || !addedStart)
        {
            T left = leftI<leftBranch.size() ? leftBranch.get(leftI) : null;
            T right = rightI<rightBranch.size() ? rightBranch.get(rightI) : null;
            T top = start.get();

            if (!addedStart && (left == null || left.compareTo(top) > 0) && (right == null || right.compareTo(top) > 0))
            {
                sorted.add(start.get());
                addedStart = true;
            }
            else if (left != null && (right == null || left.compareTo(right) <= 0))
            {
                sorted.add(left);
                leftI ++;
            }
            else
            {
                sorted.add(right);
                rightI ++;
            }
        }

        return sorted;
    }

    private void fillInOrder(Node node, ArrayList<T> values, int index)
    {
        if (node.getLeftChild() == null)
        {
            node.set(values.getFirst());
            values.removeFirst();
            return;
        }

        fillInOrder(node.getLeftChild(), values, index<<1);

        node.set(values.getFirst());
        values.removeFirst();

        fillInOrder(node.getRightChild(), values, (index<<1) + 1);
    }

    private int codeToInt(String path)
    {
        path = path.toLowerCase();
        int index = 1;

        for (int i = 0; i < path.length(); i ++)
        {
            boolean left = path.charAt(i) == '0' || path.charAt(i) == 'l';
            index = (index << 1) | (left ? 0 : 1);
        }

        return index;
    }

    public String toString()
    {
        String output = "";

        for (int row = 0; row < height; row ++)
        {
            for (int col = 0; col < 1 << row; col ++)
            {
                output += get((1 << row) + col) + ", ";
            }

            output += "\n";
        }

        return output;
    }

    private class Node
    {
        private T value;

        private Node child1, child2, parent;

        // CONSTRUCTORS
        public Node(T value, Node parent)
        {
            this.value = value;

            this.parent = parent;
            child1 = null;
            child2 = null;
        }

        public Node(T value)
        {
            this.value = value;

            parent = null;
            child1 = null;
            child2 = null;
        }

        public Node()
        {
            value = null;

            parent = null;
            child1 = null;
            child2 = null;
        }

        // GETTER/SETTER
        public void set(T value) {this.value = value;}
        public void setLeftChild(Node node) {child1 = node;}
        public void setRightChild(Node node) {child2 = node;}
        public void setParent(Node node) {parent = node;}

        public T get() {return value;}
        public Node getLeftChild() {return child1;}
        public Node getRightChild() {return child2;}
        public Node getParent() {return parent;}

        // BRAIN METHODS
    }
}
