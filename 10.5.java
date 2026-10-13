public class Main {
    static class Node {
        String name;
        Node next;

        Node(String name) {
            this.name = name;
        }
    }

    static class CircularPlayerList {
        private Node tail;

        public void addLast(String name) {
            Node node = new Node(name);

            if (tail == null) {
                tail = node;
                node.next = node; // First player points to itself.
            } else {
                node.next = tail.next; // Link to the head.
                tail.next = node;
                tail = node;
            }
        }

        public void printTurns(int turns) {
            if (tail == null) {
                System.out.println("No players");
                return;
            }

            Node current = tail.next; // Head of the circle.

            for (int i = 0; i < turns; i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(current.name);
                current = current.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CircularPlayerList players = new CircularPlayerList();
        players.addLast("Asha");
        players.addLast("Ravi");
        players.addLast("Neha");

        players.printTurns(7);
    }
}