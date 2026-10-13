public class Main {
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static class SinglyLinkedList {
        private Node head;
        private int size;

        public void addFirst(int token) {
            Node newNode = new Node(token);
            newNode.next = head;
            head = newNode;
            size++;
        }

        public void addLast(int token) {
            Node newNode = new Node(token);

            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }

            size++;
        }

        public void insertAt(int index, int token) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            }

            if (index == 0) {
                addFirst(token);
                return;
            }

            Node previous = head;
            for (int i = 0; i < index - 1; i++) {
                previous = previous.next;
            }

            Node newNode = new Node(token);
            newNode.next = previous.next;
            previous.next = newNode;
            size++;
        }

        public void printList() {
            Node current = head;

            while (current != null) {
                System.out.print(current.data + " -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        SinglyLinkedList tokens = new SinglyLinkedList();

        tokens.addLast(101);
        tokens.addLast(102);
        tokens.addLast(103);

        tokens.addFirst(100);
        tokens.addLast(104);
        tokens.insertAt(2, 150);

        tokens.printList();
    }
}