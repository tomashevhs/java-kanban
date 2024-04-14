
package historymanager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private HashMap<Integer, Node<Task>> idWithNode;
    private Node<Task> head;
    private Node<Task> tail;

    public InMemoryHistoryManager(HashMap<Integer, Node<Task>> idWithNode) {
        this.idWithNode = idWithNode;
    }

    public InMemoryHistoryManager() {

    }


    private void removeNode(Node<Task> node) {
        if (node != null) {
            if (node == head && node == tail) {
                head = null;
                tail = null;
            } else if (node == head) {
                node.next.prev = null;
                head = node.next;
            } else if (node == tail) {
                node.prev.next = null;
                tail = node.prev;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        }
    }

    public void linkLast(Task element) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, element, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }

        idWithNode.put(element.getId(), newNode);
    }

    public List<Task> getTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> currentNode = head;
        while (currentNode != null) {
            tasks.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return tasks;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        } else {
            if (idWithNode.containsKey(task.getId())) {
                removeNode(idWithNode.get(task.getId()));
            }
        }

        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTask();
    }

    @Override
    public void remove(int id) {
        removeNode(idWithNode.get(id));
        idWithNode.remove(id);
    }

    static class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}