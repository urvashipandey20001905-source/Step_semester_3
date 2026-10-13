public class Main {
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node reverse(Node head) {
        Node previous = null;
        Node current = head;

        while (current != null) {
            Node next = current.next; // Save the rest of the list.
            current.next = previous;  // Reverse this link.
            previous = current;
            current = next;
        }

        return previous; // New head.
    }

    static Node createList(int... values) {
        Node head = null;
        Node tail = null;

        for (int value : values) {
            Node node = new Node(value);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }
        return head;
    }

    static void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        Node queue = createList(1, 2, 3, 4);
        printList(reverse(queue)); // 4 -> 3 -> 2 -> 1 -> null

        Node singlePersonQueue = createList(9);
        printList(reverse(singlePersonQueue)); // 9 -> null
    }
}