package algo1;

import java.util.concurrent.TimeUnit;

public class ListReverse {

    public static void main(String[] args) {
        try {
            Node node1 = new Node(1);
            Node node2 = new Node(3);
            Node node3 = new Node(5);
            node1.setNext(node2);
            node2.setNext(node3);

            for (Node n = node1; n != null; n = n.getNext()) {
                System.out.println(n.toString());
            }
            System.out.println("==");
            Node rehead = reverse(node1);
            for (Node n = rehead; n != null; n = n.getNext()) {
                System.out.println(n.toString());
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Node reverse(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node rehead = reverse(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return rehead;
    }

    static class Node {

        private int value;
        private Node next = null;

        Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }

    }

}
