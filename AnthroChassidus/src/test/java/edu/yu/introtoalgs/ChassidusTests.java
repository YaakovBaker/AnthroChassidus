package edu.yu.introtoalgs;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class ChassidusTests {
    
    @Test
    public void classWorksTest1(){
        int n = 10;
        int a[] = {0,1,2,3,6,8};
        int b[] = {5,6,7,9,4,2};
        // 0 and 5
        // 1 and 6 and 4
        // 2 and 7 and 8
        // 3 and 9
        AnthroChassidus aChassidus = new AnthroChassidus(n, a, b);
        assertEquals(2, aChassidus.nShareSameChassidus(0));
        assertEquals(2, aChassidus.nShareSameChassidus(5));
        assertEquals(2, aChassidus.nShareSameChassidus(3));
        assertEquals(2, aChassidus.nShareSameChassidus(9));
        assertEquals(3, aChassidus.nShareSameChassidus(1));
        assertEquals(3, aChassidus.nShareSameChassidus(6));
        assertEquals(3, aChassidus.nShareSameChassidus(4));
        assertEquals(3, aChassidus.nShareSameChassidus(2));
        assertEquals(3, aChassidus.nShareSameChassidus(7));
        assertEquals(3, aChassidus.nShareSameChassidus(8));
        assertEquals(4, aChassidus.getLowerBoundOnChassidusTypes());
    }

    @Test
    public void classWorksTest2(){
        int n = 10;
        int a[] = {0,1,2,3,6,5};
        int b[] = {5,6,7,4,4,2};
        // 0 and 5 and 2 and 7
        // 1 and 6 and 4 and 3 
        AnthroChassidus aChassidus = new AnthroChassidus(n, a, b);
        assertEquals(4, aChassidus.nShareSameChassidus(0));
        assertEquals(4, aChassidus.nShareSameChassidus(5));
        assertEquals(4, aChassidus.nShareSameChassidus(3));
        assertEquals(4, aChassidus.nShareSameChassidus(1));
        assertEquals(4, aChassidus.nShareSameChassidus(6));
        assertEquals(4, aChassidus.nShareSameChassidus(4));
        assertEquals(4, aChassidus.nShareSameChassidus(2));
        assertEquals(4, aChassidus.nShareSameChassidus(7));
        assertEquals(4, aChassidus.getLowerBoundOnChassidusTypes());
    }

    @Test
    public void classWorksTest3(){
        int n = 10;
        int a[] = {0,1,3,4};
        int b[] = {9,7,4,6};
        // 0 and 9
        // 1 and 7
        // 3 and 4 and 6
        AnthroChassidus aChassidus = new AnthroChassidus(n, a, b);
        assertEquals(2, aChassidus.nShareSameChassidus(0));
        assertEquals(2, aChassidus.nShareSameChassidus(9));
        assertEquals(2, aChassidus.nShareSameChassidus(7));
        assertEquals(2, aChassidus.nShareSameChassidus(1));
        assertEquals(3, aChassidus.nShareSameChassidus(6));
        assertEquals(3, aChassidus.nShareSameChassidus(4));
        assertEquals(3, aChassidus.nShareSameChassidus(3));
    }

    @Test
    public void constructorTimeTest(){
        int n = 15000000;
        int array_size = n/4;
        Random alThor = new Random();
        int a[] = new int[array_size];
        int b[] = new int[array_size];
        
        for(int i = 0; i < array_size; i++){
            int r = alThor.nextInt(n);
            a[i] = r;
            int r2 = alThor.nextInt(n);
            b[i] = r2;
        }
        long time = System.currentTimeMillis();
        AnthroChassidus aChassidus = new AnthroChassidus(n, a, b);
        long newTime = System.currentTimeMillis();
        double executionTimeInSeconds = (newTime - time) / 1000.0;
        System.out.println("___________");
        System.out.println("execution time: " + executionTimeInSeconds);
        System.out.println("___________");
        System.out.println("ChassidusLowerBound: " + aChassidus.getLowerBoundOnChassidusTypes());
        System.out.println("NShare: " + aChassidus.nShareSameChassidus(5));
        //TOok 0.001 second with n = 600
        //took 0.0 seconds with n = 6000
        //took 0 seconds with n = 600 on second test
        //seems to be run dependant but the runtime of the constructor is giving me 0 seconds for these relatively low ns
        //Will do big ns now
        //took 1.821 seconds for n = 14748364 on run one
        //took 1.801 seconds for n = 14748364 on run two
        //stays relatively the same time for the same n between different runs
        //cant do ns that are too big for java heap size problems and array size
        //took 0.398 with n = 15000000 and arraysizes 1/4 of that
    }
}
