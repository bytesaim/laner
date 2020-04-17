package com.bytesaim.exceptions;

import org.junit.Test;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class TestExceptor {

    private String testThrowserField = "This is exclusive to TestExceptor";

    @Test
    public void Test1() {
        Exceptor exceptor = new Exceptor() {
            @Override
            public void threw(Object thrower, Exception ex) {
                System.out.println(((TestExceptor)thrower).testThrowserField );
            }
        };
        exceptor.threw(this, new ArgumentOutOfBoundException());
    }

    @Test
    public void Test2() {
        Exceptor exceptor = new Exceptor() {
            @Override
            public void threw(Object thrower, Exception ex) {
                ex.printStackTrace();
            }
        };
        exceptor.threw(null, new Exception("Some randome exception"));
    }

    @Test
    public void Test3() {
        Exceptor exceptor = new Exceptor() {
            @Override
            public void threw(Object thrower, Exception ex) {
                if (ex instanceof ArgumentOutOfBoundException) {

                } else if (ex instanceof ResponseHeaderException) {
                    System.err.println(ex.getMessage());
                }
            }
        };
        exceptor.threw(this, new ResponseHeaderException());
    }

}
