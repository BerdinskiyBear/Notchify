package ru.berdinskiybear.notchify;

import java.util.Random;

public class NotchificationStuffs {
    public static int calculateEnchantmentPower(Random random, int number, int bookshelves) {
        if (bookshelves > 15) {
            bookshelves = 15;
        }

        int j = random.nextInt(8) + 1 + (bookshelves >> 1) + random.nextInt(bookshelves + 1);
        switch (number) {
            case 0:
                return Math.max(j / 3, 1);
            case 1:
                return j * 2 / 3 + 1;
            default:
                return Math.max(j, bookshelves * 2);
        }


    }
}
