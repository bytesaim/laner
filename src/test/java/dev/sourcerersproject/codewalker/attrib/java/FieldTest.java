package dev.sourcerersproject.codewalker.attrib.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FieldTest {

    Field field1 = new Field();
    Field field2 = new Field();
    Field field3 = new Field("Object", "id");

    @Before
    public void Before() {
        field1.setType("String");
        field1.setIdentifier("arg");
        field1.setVariadic(true);
        field1.setValue(new Class());
        field1.setRawValue("true");
    }

    @Test
    public void Type() {
        assert field1.getType().equals("String");
        assert !field1.getType().equals("type");
    }

    @Test
    public void Identifier() {
        assert field1.getIdentifier().equals("arg");
        assert !field1.getIdentifier().equals("identifier");
    }

    @Test
    public void Value() {
        assert !field1.getValue().equals(new Class());
        assert field1.getRawValue().equals("true");
        assert !field1.getValue().equals(field2.getValue());
    }

    @Test
    public void StringValue() {
        System.out.println(field1.toString());
    }

    @Test
    public void Variadic() {
        assert field1.isVariadic() && !field2.isVariadic();
        assert field1.isVariadic();
    }

    @Test
    public void RawString() {
        System.out.println(field1.getRawString());
        System.out.println(field3.getRawString());
    }

    @After
    public void After() {

    }

}
