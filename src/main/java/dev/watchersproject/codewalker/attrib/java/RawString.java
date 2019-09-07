package dev.watchersproject.codewalker.attrib.java;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Azeez Adewale <azeezadewale98@gmail.com>
 * :date: 07 September 2019
 * :time: 04:10 AM
 * :filename: RawString.java
 */
public class RawString {

    /**
     *
     */
    private String value = "" ;

    /**
     *
     */
    private boolean formated = true;

    /**
     *
     */
    private boolean valueChanged = false;

    /**
     *
     */
    public RawString() {

    }

    /**
     *
     * @param str
     */
    public RawString(String str) {
        this.value = str;
        valueChanged = true;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        if (valueChanged) {
            format();
        }
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
        valueChanged = true;
        formated = false;
    }

    /**
     *
     * @return the raw string value
     */
    @Override
    public String toString() {
        return getValue();
    }

    /**
     *
     * @return
     */
    public boolean isFormated() {
        return formated;
    }

    /**
     *
     */
    public void format() {
        if (formated) return;

    }

}
