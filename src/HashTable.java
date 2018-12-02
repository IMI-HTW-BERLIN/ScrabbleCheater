import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class HashTable {
    private int MOD = 3947;
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
            hashValue += Math.pow(2, aChar - 97);       //this way each char has a unique number
        }
        return hashValue;
    }

    private int position(long hashValue) {
        return (int) (hashValue % MOD);
    }

    void print() {

        for (int i = 0; i < table.length; i++) {
            System.out.println(String.valueOf(i) + ": " + table[i]);
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
        char[] charsOfWord = word.toCharArray();
        ArrayList<String> permutations= new ArrayList<>();
        Arrays.sort(charsOfWord);
        for (String string : table[position(hashValue(word))]) {
            char[] charsOfString = string.toCharArray();
            Arrays.sort(charsOfString);
            if(Arrays.equals(charsOfString, charsOfWord)) permutations.add(string);
        }
        return permutations;
    }

    int getMOD() {
        return MOD;
    }

    void setMOD(int MOD) {
        this.MOD = MOD;
    }

    int getNumberOfWords() {
        return numberOfWords;
    }

    void resize(int MOD) {
        setMOD(MOD);
        ArrayList<String>[] temp = new ArrayList[MOD];
        if(temp.length > table.length) {
            for (int i = 0; i < temp.length; i++) {
                temp[i] = new ArrayList<>();
            }
            System.arraycopy(table, 0, temp, 0, table.length); //Really handy!

            table = temp;
        }else {
            table = new ArrayList[MOD];
            for (int i = 0; i < temp.length; i++) {
                table[i] = new ArrayList<>();
            }
        }
    }
}
