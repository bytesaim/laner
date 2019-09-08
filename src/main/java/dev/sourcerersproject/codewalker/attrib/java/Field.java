package dev.sourcerersproject.codewalker.attrib.java;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Azeez Adewale <azeezadewale98@gmail.com>
 * :date: 03 September 2019
 * :time: 12:11 AM
 * :filename: Field.java
 */
public class Field {

    /**
     *
     */
    private String type = "" ;

    /**
     *
     */
    private String identifier = "";

    /**
     *
     */
    private Class value = new Class() ;

    /**
     *
     */
    private String rawValue = "" ;

    /**
     *
     */
    private boolean isVariadic = false ;

    /**
     * The complete raw code for the object this include the body
     * of the method and the declaration
     */
    private String rawString = "";

    /**
     * Used to check if the structure changes. true be default
     * to enable analyzing toString value for first time
     */
    private boolean structureChanges = true;

    /**
     * This is the string representation of this object.
     * It is cached to prevent overhead and multiple calculation
     * of hashcodes and fields analysing when structure does not
     * change
     */
    private String toString = "" ;

    /**
     *
     */
    public Field() {

    }

    /**
     *
     * @param type
     * @param identifier
     */
    public Field(String type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    /**
     *
     * @return
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     *
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public boolean isVariadic() {
        return isVariadic;
    }

    /**
     *
     * @param variadic
     */
    public void setVariadic(boolean variadic) {
        isVariadic = variadic;
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public String getRawString() {
        if (structureChanges) {
            if (rawValue.equals("")) {
                rawString = type + " " + identifier + " ;";
            } else {
                rawString = type + " " + identifier + " = " + rawValue + " ;";
            }
        }
        return rawString;
    }

    /**
     *
     * @param rawString
     */
    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    /**
     *
     */
    @Override
    public String toString() {
        if (structureChanges) {
            toString = getClass().getName() + "@" + Integer.toHexString(hashCode());
            toString += ":Type=" + type + ",Identifier=" + identifier + ",IsVariadic=" + isVariadic ;
        }
        return toString;
    }

    /**
     *
     * @return
     */
    public Class getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(Class value) {
        this.value = value;
    }

    /**
     *
     * @param value
     */
    public void setRawValue(String value) {
        this.rawValue = value;
    }

    /**
     *
     * @return
     */
    public String getRawValue() {
        return rawValue;
    }
}
