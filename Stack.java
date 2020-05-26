import java.util.Iterator;

public class Stack<Item> implements Iterable<Item>
{
    private Struct first; 
    private int N; 

    private class Struct
    { 
        Item item;
        Struct next;
    }

    public boolean isEmpty() { return first == null; } 
    public int size() { return N; }

    public void push(Item item)
    { 
        Struct oldfirst = first;
        first = new Struct();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public Item pop()
    { 
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public Iterator<Item> iterator()
    { 
        return new ListIterator(); 
    }

    private class ListIterator implements Iterator<Item>
    {
        private Struct current = first;

        public boolean hasNext()
        { 
            return current != null; 
        }

        public void remove() { }

        public Item next()
        {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    

}