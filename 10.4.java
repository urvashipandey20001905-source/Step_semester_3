public class Main {
    static class Node {
        String name;
        Node prev;
        Node next;

        Node(String name) {
            this.name = name;
        }
    }

    static class Train {
        private Node head;
        private Node tail;

        public void addLast(String name) {
            Node node = new Node(name);

            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        }

        // Removes the first coach with this name.
        public boolean remove(String name) {
            Node current = head;

            // Find the coach.
            while (current != null && !current.name.equals(name)) {
                current = current.next;
            }

            if (current == null) {
                return false;
            }

            // Connect the previous coach to the next coach.
            if (current.prev != null) {
                current.prev.next = current.next;
            } else {
                head = current.next;
            }

            // Connect the next coach to the previous coach.
            if (current.next != null) {
                current.next.prev = current.prev;
            } else {
                tail = current.prev;
            }

            return true;
        }

        public void printForward() {
            System.out.print("Forward: ");
            Node current = head;

            while (current != null) {
                System.out.print(current.name);
                if (current.next != null) System.out.print(" <-> ");
                current = current.next;
            }
            System.out.println();
        }

        public void printBackward() {
            System.out.print("Backward: ");
            Node current = tail;

            while (current != null) {
                System.out.print(current.name);
                if (current.prev != null) System.out.print(" <-> ");
                current = current.prev;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Train train = new Train();
        train.addLast("Engine");
        train.addLast("A1");
        train.addLast("B1");
        train.addLast("B2");
        train.addLast("Guard");

        train.remove("B1");

        train.printForward();
        train.printBackward();
    }
}