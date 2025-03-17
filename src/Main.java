import java.awt.event.PaintEvent;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        PerfectCell<String> cell = new PerfectCell<>(5, "");

        for (int i = 0; i <= cell.numNodes; i ++)
        {
            cell.set(i, Integer.toBinaryString(i));
        }

//        cell.set(1, "organism");
//        cell.set(0b11, "animal");
//        cell.set(0b110, "horse");
//        cell.set(0b111, "whale");
//        cell.set(0b1110, "blue whale");
//        cell.set(0b1111, "killer whale");
        cell.set(0b10, "plant");
        cell.set(0b100, "tree");
        cell.set(0b101, "bush");
        cell.set(0b1001, "oak");
        cell.set(0b1000, "birch");

        cell.set("", "organism");
        cell.set("r", "animal");
        cell.set("rl", "horse");
        cell.set("rr", "whale");
        cell.set("rrl", "blue whale");
        cell.set("rrr", "killer whale");
//        cell.set("l", "plant");
//        cell.set("ll", "tree");
//        cell.set("lr", "bush");
//        cell.set("llr", "oak");
//        cell.set("lll", "birch");

        System.out.println(cell);

        System.out.println(cell.get("lrrl")); //10110

        System.out.println(cell.getRow(3));
        System.out.println(cell.getPath(0b1111));
        System.out.println(cell.getPath("llr"));

        PerfectCell<Integer> sorter = new PerfectCell<>(17, 0);

        for (int i = 0; i <= sorter.numNodes; i ++)
        {
            sorter.set(i, i);
        }

        System.out.println(sorter);

        sorter.sort();

        System.out.println(sorter);

        Scanner scan = new Scanner(System.in);
        System.out.println("done");
        int num = scan.nextInt();

        System.out.println(sorter.binarySearch(num));
        System.out.println(sorter.get(sorter.binarySearch(num)));
    }
}