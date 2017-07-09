package algo;

public class SelectSort {

    public void sort(int[] elements) {
        for (int i = 0; i < elements.length - 1; ++i) {
            int k = i;
            for (int j = i; j < elements.length; ++j) {
                if (elements[k] > elements[j]) {
                    k = j;
                }
            }
            if (k != i) {//交换元素
                int temp = elements[i];
                elements[i] = elements[k];
                elements[k] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] data = { 6, 3, 2, 5, 16, 23, 12, 8, 10, 9 };
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
        System.out.println();
        new SelectSort().sort(data);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
    }

}
