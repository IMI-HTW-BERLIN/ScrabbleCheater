import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTable {
    private static final int MOD = 3947;
//    private static final int MOD = 24029;
    private ArrayList<String>[] table;
    private int numberOfWords = 0;
    private static File file = new File("C:\\Users\\David\\eclipse-workspace\\INFO2\\2018-07-04-ScrabbleCheater\\twl.txt");

    public static void main(String[] args) throws FileNotFoundException {
        HashTable ht = new HashTable(file);
        Scanner scanner = new Scanner(System.in);
//        ht.print();
        new Thread(() -> {
            String next;
            while(!(next = scanner.nextLine()).equals("stop"))System.out.println(ht.getPermutations(next));}).start();

        System.out.println("Longest Chain: " + ht.longestChain());
        System.out.println("Collisions: " + ht.getCollisions());
        System.out.println("Number of Words: " + ht.numberOfWords);
    }

    private HashTable(File file) throws FileNotFoundException {
        table = new ArrayList[MOD];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ArrayList<>();
        }
        fill(file);
    }

    private void fill(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String nextWord = scanner.next();
            if (nextWord.matches("[a-z]{7}")) {
                int position = position(hashValue(nextWord));
                table[position].add(nextWord);
                numberOfWords++;
            }
        }
    }

    private long hashValue(String word) {
        char[] chars = word.toCharArray();
        //Arrays.sort(chars);
        long hashValue = 0;
        for (int i = 0; i < chars.length; i++) {
            //hashValue += (long) ((long) (chars[i]) * Math.pow(26, chars.length - i - 1)); //old hash function
            hashValue += Math.pow(2,chars[i]-97);       //this way each char has a unique number
        }
        return hashValue;
    }

    private int position(long hashValue) {
        return (int) (hashValue % MOD);
    }

    public void print() {

        for (int i = 0; i < table.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i).append(": ").append(table[i]);
            System.out.println(sb.toString());
        }
    }

    private int longestChain() {
        int size = 0;
        for (ArrayList<String> aTable : table) {
            if (size < aTable.size()) size = aTable.size();
        }
        return size;
    }

    private int getCollisions() {
        int collisions = 0;
        for (ArrayList<String> aTable : table) {
            collisions += (aTable.size() <= 1) ? 0 : aTable.size() - 1;
        }
        return collisions;
    }

    private ArrayList<String> getPermutations(String word) {
        return table[position(hashValue(word))];
    }
}
