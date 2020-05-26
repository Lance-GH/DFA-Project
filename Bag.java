import java.util.Iterator;

public class Bag<Item> implements Iterable<Item>
{
    private Struct first; // first node in list

    private class Struct
    {
        Item item;
        Struct next;
    }
    public void add(Item item)
    { 
        Struct oldfirst = first;
        first = new Struct();
        first.item = item;
        first.next = oldfirst;
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