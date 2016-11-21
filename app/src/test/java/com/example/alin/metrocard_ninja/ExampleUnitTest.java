package com.example.alin.metrocard_ninja;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void bonusValue_isCorrect() {
        assertEquals(0.74, DisplayMessageActivity.bonusValue(6.75), 0.001);
        assertEquals(0.61, DisplayMessageActivity.bonusValue(5.50), 0.001);
        assertEquals(1.10, DisplayMessageActivity.bonusValue(10.00), 0.001);
        assertEquals(1.65, DisplayMessageActivity.bonusValue(15.00), 0.001);
        assertEquals(2.20, DisplayMessageActivity.bonusValue(20.00), 0.001);
        assertEquals(3.00, DisplayMessageActivity.bonusValue(27.25), 0.001);
        assertEquals(6.00, DisplayMessageActivity.bonusValue(54.5), 0.001);
    }

    @Test
    public void numberOfRides_isCorrect() {
        assertEquals(9, DisplayMessageActivity.numberOfRides(24.75));
        assertEquals(11, DisplayMessageActivity.numberOfRides(30.25));
        assertEquals(20, DisplayMessageActivity.numberOfRides(55.00));
        assertEquals(22, DisplayMessageActivity.numberOfRides(60.50));
        assertEquals(29, DisplayMessageActivity.numberOfRides(79.75));
    }

    @Test
    public void LeavesZeroBalance_isCorrect() {
        assertEquals(true, DisplayMessageActivity.leavesZeroBalance(11.00));
        assertEquals(true, DisplayMessageActivity.leavesZeroBalance(35.75));
        assertEquals(true, DisplayMessageActivity.leavesZeroBalance(60.50));
        assertEquals(true, DisplayMessageActivity.leavesZeroBalance(66.00));
        assertEquals(false, DisplayMessageActivity.leavesZeroBalance(10));
        assertEquals(false, DisplayMessageActivity.leavesZeroBalance(15));
        assertEquals(false, DisplayMessageActivity.leavesZeroBalance(20));
    }

    @Test
    public void amountsToAddForNoRemainderFromZero() {
        ArrayList<Double> amts = new ArrayList<Double>();
        amts.add(0.0);
        amts.add(2.75);
        amts.add(22.30);
        amts.add(27.25);
        amts.add(49.55);
        amts.add(54.50);
        amts.add(71.85);
        assertEquals(amts, DisplayMessageActivity.amountsToAddForNoRemainder(0, 73.00));
    }

    @Test
    public void amountsToAddForNoRemainderFromNonZero() {
        ArrayList<Double> amts = new ArrayList<Double>();
        amts.add(2.45);
        amts.add(5.2);
        amts.add(19.55);
        amts.add(24.50);
        amts.add(41.85);
        amts.add(46.80);
        amts.add(69.10);
        assertEquals(amts, DisplayMessageActivity.amountsToAddForNoRemainder(0.3, 80.00));
    }
}