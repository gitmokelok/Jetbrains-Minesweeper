package minesweeper;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // write your code here

        Random random = new Random();


        for (int i = 0; i< 9; i++) {
            int mineIndex =random.nextInt(0, 9);
            for (int j = 0; j < 9; j++) {
                if (mineIndex == j) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
