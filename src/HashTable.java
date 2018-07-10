import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class HashTable {
    private static int MOD = 3947;
    private ArrayList<String>[] table;
    private int numberOfWords = 0;

    HashTable(){
        table = new ArrayList[MOD];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ArrayList<>();
        }
    }

    void fill(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String nextWord = scanner.next();
                int position = position(hashValue(nextWord));
                table[position].add(nextWord);
                numberOfWords++;
        }
    }

    void fill(File file, int lowerBound, int upperBound) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String nextWord = scanner.next();
            if (nextWord.matches("[a-z]{" + lowerBound + "," + upperBound + "}")) {
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
        for (char aChar : chars) {
            //hashValue += (long) ((long) (chars[i]) * Math.pow(26, chars.length - i - 1)); //old hash function
            hashValue += Math.pow(2, aChar - 97);       //this way each char has a unique number
        }
        return hashValue;
    }

    private int position(long hashValue) {
        return (int) (hashValue % MOD);
    }

    void print() {

        for (int i = 0; i < table.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i).append(": ").append(table[i]);
            System.out.println(sb.toString());
        }
    }

    int longestChain() {
        int size = 0;
        for (ArrayList<String> aTable : table) {
            if (size < aTable.size()) size = aTable.size();
        }
        return size;
    }

    int getCollisions() {
        int collisions = 0;
        for (ArrayList<String> aTable : table) {
            collisions += (aTable.size() <= 1) ? 0 : aTable.size() - 1;
        }
        return collisions;
    }

    ArrayList<String> getPermutations(String word) {
        return table[position(hashValue(word))];
    }

    static int getMOD() {
        return MOD;
    }

    static void setMOD(int MOD) {
        HashTable.MOD = MOD;
    }

    int getNumberOfWords() {
        return numberOfWords;
    }
}
