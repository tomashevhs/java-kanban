package HistoryManager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager<T> implements HistoryManager {

    HashMap<Integer, Node<Task>> idWithNodes = new HashMap<>();

    private Node<Task> head;

    private Node<Task> tail;

    static class Node<Task> {
        public Task task;
        public Node<Task> next;
        public Node<Task> prev;

        public Node(Node<Task> prev, Task task, Node<Task> next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }

    public void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        idWithNodes.put(task.getId(), head);
    }

    public void removeNode(Node<Task> node) {
        if (node == tail) {
            node.prev = tail;
            node.prev.next = null;
        } else if (node == head) {
            node.next = head;
            node.prev = null;
        } else if (node == tail && node == head) {
            node.next = null;
            node.prev = null;
        } else {
            node.prev.next = null;
            node.next.prev = null;
        }
    }

    public List<Task> getTasks() {
        ArrayList<Task> Tasks = new ArrayList<>();
        Node<Task> currentNode = head;
        while (currentNode != null) {
            Tasks.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return Tasks;
    }

    @Override
    public void add(Task task) {
        if (idWithNodes.containsKey(task.getId())) {
            removeNode(idWithNodes.get(task.getId()));
        }
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(idWithNodes.get(id));
    }
}


