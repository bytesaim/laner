package dev.watchersproject.codewalker.attrib.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RawStringTest {

    RawString rawString = new RawString();

    @Before
    public void Before() {
        SetValue();
    }

    @Test
    public void SetValue() {
        rawString.setValue("=new String()");
    }

    @Test
    public void GetValue() {
        System.out.println(rawString.getValue());
    }

    @After
    public void After() {

    }

}
