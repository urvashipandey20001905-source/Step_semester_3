public class Main {
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node removeAll(Node head, int code) {
        // Remove matching nodes from the front.
        while (head != null && head.data == code) {
            head = head.next;
        }

        // Remove matching nodes after the new head.
        Node current = head;
        while (current != null && current.next != null) {
            if (current.next.data == code) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }

        return head;
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
        Node orders1 = createList(5, 3, 5, 8, 5);
        printList(removeAll(orders1, 5)); // 3 -> 8 -> null

        Node orders2 = createList(5, 5, 5);
        printList(removeAll(orders2, 5)); // null
    }
}