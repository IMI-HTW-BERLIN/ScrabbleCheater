import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    private static File file = new File("C:\\Users\\David\\eclipse-workspace\\INFO2\\2018-07-04-ScrabbleCheater\\twl.txt");

    public static void main(String[] args) throws FileNotFoundException {
        HashTable ht = new HashTable();
        ht.fill(file,7,7);

        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            String next;
            while(!(next = scanner.nextLine()).equals("stop"))System.out.println(ht.getPermutations(next));}).start();

        ht.print();

        System.out.println("Longest Chain: " + ht.longestChain());
        System.out.println("Collisions: " + ht.getCollisions());
        System.out.println("Number of Words: " + ht.getNumberOfWords());
    }
}
