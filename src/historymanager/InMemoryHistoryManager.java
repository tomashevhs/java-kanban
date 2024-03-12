
package historymanager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    HashMap<Integer, Node<Task>> idWithNode = new HashMap<>();

    static class Node<Task> {
        public Task data;
        public Node<Task> next;
        public Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<Task> head;

    private Node<Task> tail;

    public void removeNode(Node<Task> node) {
        if (node == tail) {
            node.prev = tail;
            node.prev.next = null;
        } else if (node == head) {
            node.next.prev = null;
        } else if (node == tail && node == head) {
            tail = null;
            head = null;
            node.next = head;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;

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
        ArrayList<Task> Tasks = new ArrayList<>();
        Node<Task> currentNode = head;
        while (currentNode != null) {
            Tasks.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return Tasks;
    }

    @Override
    public void add(Task task) {
        if (idWithNode.containsKey(task.getId())) {
            removeNode(idWithNode.remove(task.getId()));
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
}