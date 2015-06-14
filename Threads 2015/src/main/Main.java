/*
 * Created on May 28, 2015
 *
 */
package main;

import test.Test;

public class Main {
	
    public Main() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        Test t = new Test();
//        t.simpleWait();
//        t.loopWait();
//        t.timerWait();
//        t.testPickLine();
//        t.testSing();
//        t.testSingWithDelay();
//        t.testSingWithTimer();
        t.testSingWithThreads();
    }

}
