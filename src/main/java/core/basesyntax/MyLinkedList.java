package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    public MyLinkedList() {
        tail = null;
        head = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        findIndexForAdd(index);
        Node<T> newNode = new Node<>(null, null, value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.next = current;
            newNode.previous = current.previous;
            current.previous.next = newNode;
            current.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNode(index);
        T previousValue = newNode.value;
        newNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removeNode = getNode(index);
        unlink(removeNode);
        size--;
        return removeNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        Node<T> removeNode = head;
        while (removeNode != null) {
            if (object == null ? removeNode.value == null : object.equals(removeNode.value)) {
                unlink(removeNode);
                size--;
                return true;
            }
            removeNode = removeNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index not found!" + index);
        }
    }

    private void findIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index not found!" + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private static class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }

    private void unlink(Node<T> node) {
        if (node != null) {
            if (node.previous != null) {
                node.previous.next = node.next;
            }

            if (node.next != null) {
                node.next.previous = node.previous;
            }

            if (tail == node) {
                tail = node.previous;
            }

            if (head == node) {
                head = node.next;
            }
        }
    }
}
