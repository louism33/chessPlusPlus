package com.github.louism33.axolotl.timemanagement;

import com.github.louism33.axolotl.search.EngineBetter;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TimeManagementTest {

    @BeforeAll
    static void setup() {
        EngineBetter.resetFull();
    }

    @AfterAll
    static void reset() {
        EngineBetter.resetFull();
    }

    @Test
    void dontAllocateBelowZeroTest() {
        for (int i = 0; i < 100_000; i++) {
            Random r = new Random();

            int maxTime = r.nextInt(60_000_000) + 5001;
            int enemyTime = r.nextInt(60_000_000);
            int increment = r.nextInt(10_000);
            int movesToGo = r.nextInt(100);
            int fullMoves = r.nextInt(10000);

            long allocateTime = TimeAllocator.allocateTime(maxTime, enemyTime, increment, movesToGo, fullMoves);

            Assert.assertTrue(allocateTime > 0);
            Assert.assertTrue(allocateTime < maxTime);
        }
    }

    @Test
    void dominantTest(){
        long allocateTime = TimeAllocator.allocateTime(485370, 38948, 6000, 0, 200);

        System.out.println(allocateTime);

    }

}