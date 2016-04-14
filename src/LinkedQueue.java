import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Yulong Tan
 * 4.8.16
 *
 * LinkedList implementation of a Queue, with First In, First Out structure.
 */

public class LinkedQueue<E> {
    private QueueNode front; // reference to the front
    private QueueNode back; // reference to the back
    private int size; // reference to the size of the queue

    public LinkedQueue() {
        this.front = null;
        this.back = this.front;
        this.size = 0;
    }

    public void add(E data) {
        if (this.isEmpty()) {
            this.front = new QueueNode(data);
            this.back = this.front;
            this.front.prev = null;
        } else {
            QueueNode next = new QueueNode(data);
            this.back.next = next;
            QueueNode prev = this.back;
            this.back = this.back.next;
            next.prev = prev;
        }
        this.size++;
    }

    public void addAll(LinkedQueue other) {
        int size = other.size();
        for (int i = 0; i < size; i++) {
            E next = (E) other.remove();
            this.add(next);
            other.add(next);
        }
    }

    public void clear() {
        this.size = 0;
        this.front = null;
    }

    public boolean contains(E e) {
        if (this.front == null) {
            return false;
        } else if (this.front.data.equals(e)) {
            return true;
        } else {
            QueueNode current = this.front.next;
            while (current != null) {
                if (current.data.equals(e)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }

    // Returns index of first occurence of e.
    // Returns -1 if not found
    public int indexOf(E e) {
        if (!this.contains(e)) {
            return -1;
        } else {
            int index = 0;
            if (this.front.data.equals(e)) {
                return 0;
            } else {
                QueueNode current = this.front.next;
                while (current != null) {
                    index++;
                    if (current.data.equals(e)) {
                        current = null;
                    } else {
                        current = current.next;
                    }
                }
                return index;
            }
        }
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public QueueNode nodeAt(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return this.front;
        } else {
            QueueNode current = this.front;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }

    public E peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return (E) this.front.data;
    }

    public E remove() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.remove(0);
    }

    public E remove(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + this.size);
        }
        E data;
        this.size--;
        if (index == 0) {
            data = (E) this.front.data;
            this.front = this.front.next;
            if (this.front != null) {
                this.front.prev = null;
            }
        } else {
            QueueNode current = this.front;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            data = (E) current.next.data;
            current.next = current.next.next;
        }
        return data;
    }

    // Removes all occurrences of e
    public void removeAll(E e) {
        if (this.contains(e)) {
            while (this.back.data.equals(e)) {
                this.back = this.back.prev;
                this.back.next = null;
                this.size--;
            }
            QueueNode current = this.front;
            while (current.next != null) {
                if (current.next.data.equals(e)) {
                    current.next = current.next.next;
                    this.size--;
                } else {
                    current = current.next;
                }
            }
            if (this.front.data.equals(e)) {
                this.front = this.front.next;
                this.size--;
            }
        }
    }

    // Should rearrange all the links randomly
    public void shuffle() {
        LinkedQueue<E> storage = new LinkedQueue<>();
        storage.addAll(this);
        Random r = new Random();
        this.clear();
        int rand = r.nextInt(storage.size() - 1);
        this.add((E) storage.nodeAt(rand).data);
        storage.remove(rand);
        while (storage.size() > 1) {
            int random = r.nextInt(storage.size() - 1);
            this.add((E) storage.nodeAt(random).data);
            storage.remove(random);
        }
        // Adds in the last one
        this.add((E) storage.remove());
    }

    public int size() {
        return this.size;
    }

    public List<E> toArray() {
        List<E> newList = new ArrayList<>();
        for (QueueNode current = this.front; current != null; current = current.next) {
            newList.add((E) current.data);
        }
        return newList;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        } else {
            String result = "[" + this.front.data;
            QueueNode current = this.front.next;
            while (current != null) {
                result += ", " + current.data;
                current = current.next;
            }
            return result + "]";
        }
    }
}
