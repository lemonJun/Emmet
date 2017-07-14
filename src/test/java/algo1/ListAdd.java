package algo1;

public class ListAdd {

    public static void main(String[] args) {
        ListNode list1_11 = new ListNode(3);
        ListNode list1_12 = new ListNode(1);
        ListNode list1_13 = new ListNode(5);
        list1_11.next = list1_12;
        list1_12.next = list1_13;
        ListNode list2_11 = new ListNode(5);
        ListNode list2_12 = new ListNode(9);
        ListNode list2_13 = new ListNode(2);
        list2_11.next = list2_12;
        list2_12.next = list2_13;

        ListNode node = new ListAdd().addLists(list1_11, list2_11);
        while (node != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
    }

    public ListNode addLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list1;
        }
        if (list2 == null) {
            return list2;
        }
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        int carry = 0;
        while (list1 != null && list2 != null) {
            int sum = carry + list1.val + list2.val;
            if (sum > 9) {
                node.next = new ListNode(sum % 10);
                carry = 1;
            } else {
                node.next = new ListNode(sum);
                carry = 0;
            }
            node = node.next;
            list1 = list1.next;
            list2 = list2.next;
        }

        if (list1 != null) {
            node.next = new ListNode(list1.val + carry);
            list1 = list1.next;
            node = node.next;
            node.next = list1;
            return dummy.next;
        } else if (list2 != null) {
            node.next = new ListNode(list2.val + carry);
            list2 = list2.next;
            node = node.next;
            node.next = list2;
            return dummy.next;
        } else {
            if (carry == 1) {
                node.next = new ListNode(1);
            }
            return dummy.next;
        }

    }

}
