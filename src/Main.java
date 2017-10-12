import java.util.*;

public class Main {

    public static void main(String[] args) {
        IntervalTree tree = new IntervalTree();
        tree.IntervalInsert(0,3);
        tree.IntervalInsert(5,8);
        tree.IntervalInsert(6,10);
        tree.IntervalInsert(8,9);
        tree.IntervalInsert(15,23);
        tree.IntervalInsert(16,21);
        tree.IntervalInsert(17,19);
        tree.IntervalInsert(19,20);
        tree.IntervalInsert(25,30);
        tree.IntervalInsert(26,26);

        System.out.println("Pom is: " + tree.FindPom());

        tree = new IntervalTree();
        tree.IntervalInsert(4,4);
        tree.IntervalInsert(0,5);
        tree.IntervalInsert(10,11);
        tree.IntervalInsert(11,13);
        tree.IntervalInsert(9,14);
        tree.IntervalInsert(7,15);
        tree.IntervalInsert(13,14);
        tree.IntervalInsert(13,15);
        tree.IntervalInsert(21,22);
        tree.IntervalInsert(20,24);
        tree.IntervalInsert(22,25);
        tree.IntervalInsert(27,30);

        System.out.println("Pom is: " + tree.FindPom());
    }






}
