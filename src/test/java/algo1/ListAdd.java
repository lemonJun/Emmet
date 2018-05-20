package algo1;

/**
 * 513+295 这是一个跟正常数字相反的列表
 *
 * 
 * @author zhou
 * @version 1.0
 * @date  2018年5月20日 上午10:46:47
 * @see 
 * @since
 */
public class ListAdd {

    public static void main(String[] args) {
        ListNode list1_11 = new ListNode(3);
        ListNode list1_12 = new ListNode(1);
        ListNode list1_13 = new ListNode(5);
        ListNode list1_14 = new ListNode(9);
        list1_11.next = list1_12;
        list1_12.next = list1_13;
        list1_13.next = list1_14;
        ListNode list2_11 = new ListNode(5);
        ListNode list2_12 = new ListNode(9);
        ListNode list2_13 = new ListNode(6);
        list2_11.next = list2_12;
        list2_12.next = list2_13;

        ListNode node = new ListAdd().addLists(list1_11, list2_11);
        while (node != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
        System.out.println("==");
        ListNode node2 = new ListAdd().addListNode2(list1_11, list2_11);
        while (node2 != null) {
            System.out.print(node2.val + "->");
            node2 = node2.next;
        }
        
        System.out.println("==");
        ListNode node3 = new ListAdd().addTwoNumbers(list1_11, list2_11);
        while (node3 != null) {
            System.out.print(node3.val + "->");
            node3 = node3.next;
        }
    }

    /**
     * 只有这一个才是对的
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode root = new ListNode(0); // 头结点
        ListNode r = root;
        root.next = l1;

        int carry = 0; // 初始进位
        int sum;
        while (p1 != null && p2 != null) {
            sum = p1.val + p2.val + carry;
            p1.val = sum % 10; // 本位的结果
            carry = sum / 10; // 本次进位

            r.next = p1;
            r = p1; // 指向最后一个相加的结点
            p1 = p1.next;
            p2 = p2.next;

        }

        if (p1 == null) {
            r.next = p2;
        } else {
            r.next = p1;
        }

        // 最后一次相加还有进位
        if (carry == 1) {
            // 开始时r.next是第一个要相加的结点
            while (r.next != null) {
                sum = r.next.val + carry;
                r.next.val = sum % 10;
                carry = sum / 10;
                r = r.next;
            }

            // 都加完了还有进位，就要创建一个新的结点
            if (carry == 1) {
                r.next = new ListNode(1);
            }
        }

        return root.next;
    }

    public ListNode addListNode2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode tmp = new ListNode(0);
        ListNode node = tmp;
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
            return tmp.next;
        }
        if (list2 != null) {
            node.next = new ListNode(list2.val + carry);
            list2 = list2.next;
            node = node.next;
            node.next = list2;
            return tmp.next;
        } else {
            if (carry == 1) {
                node.next = new ListNode(1);
            }
            return tmp.next;
        }
    }

    public ListNode addLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
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
