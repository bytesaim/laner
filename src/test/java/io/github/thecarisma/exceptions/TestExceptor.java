package io.github.thecarisma.exceptions;

import org.junit.Test;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class TestExceptor {

    @Test
    public void Test1() {
        Exceptor exceptor = new Exceptor() {
            @Override
            public void thrown(Exception ex) {

            }
        };
        exceptor.thrown(new ArgumentOutOfBoundException());
    }

    @Test
    public void Test2() {
        Exceptor exceptor = new Exceptor() {
            @Override
            public void thrown(Exception ex) {
                ex.printStackTrace();
            }
        };
        exceptor.thrown(new Exception("Some randome exception"));
    }

    @Test
    public void Test3() {
        Exceptor exceptor = new Exceptor() {
            @Override
            public void thrown(Exception ex) {
                if (ex instanceof ArgumentOutOfBoundException) {

                } else if (ex instanceof ResponseHeaderException) {
                    System.err.println(ex.getMessage());
                }
            }
        };
        exceptor.thrown(new ResponseHeaderException());
    }

}
