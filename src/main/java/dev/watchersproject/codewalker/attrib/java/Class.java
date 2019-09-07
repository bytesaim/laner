package dev.watchersproject.codewalker.attrib.java;

import java.util.ArrayList;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Azeez Adewale <azeezadewale98@gmail.com>
 * :date: 03 September 2019
 * :time: 12:26 AM
 * :filename: Class.java
 */
public class Class {

    /**
     *
     */
    private String identifier = "";

    /**
     *
     */
    private ArrayList<Method> methods = new ArrayList<Method>();

    /**
     *
     */
    private ArrayList<Field> fields = new ArrayList<Field>();

    /**
     *
     */
    private ArrayList<String> implementedClasses = new ArrayList<String>();

    /**
     *
     */
    private boolean hasImplement = false ;

    /**
     *
     */
    private boolean extendsAClass = false ;

    /**
     *
     */
    private String classExtended = "" ;

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
    public ArrayList<Method> getMethods() {
        return methods;
    }

    /**
     *
     * @param method
     */
    public void addMethod(Method method) {
        methods.add(method);
        structureChanges = true;
    }

    /**
     *
     * @param method
     */
    public void removeMethod(Method method) {
        methods.remove(method);
        structureChanges = true;
    }

    /**
     *
     * @param index
     */
    public void removeMethod(int index) {
        methods.remove(index);
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    /**
     *
     * @param field
     */
    public void addField(Field field) {
        fields.add(field);
        structureChanges = true;
    }

    /**
     *
     * @param field
     */
    public void removeField(Field field) {
        fields.remove(field);
        structureChanges = true;
    }

    /**
     *
     * @param index
     */
    public void removeField(int index) {
        fields.remove(index);
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getImplementedClasses() {
        return implementedClasses;
    }

    /**
     *
     * @param aclass
     */
    public void addImplementedClass(String aclass) {
        implementedClasses.add(aclass);
        structureChanges = true;
    }

    /**
     *
     * @param aclass
     */
    public void removeImplementedClass(String aclass) {
        implementedClasses.remove(aclass);
        structureChanges = true;
    }

    /**
     *
     * @param index
     */
    public void removeImplementedClass(int index) {
        implementedClasses.remove(index);
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public String getRawString() {
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
     * @return
     */
    public boolean hasImplement() {
        return hasImplement;
    }

    /**
     *
     * @return
     */
    public boolean extendsAClass() {
        return extendsAClass;
    }

    /**
     *
     * @return
     */
    public String getClassExtended() {
        return classExtended;
    }

    /**
     *
     * @param classExtended
     */
    public void setClassExtended(String classExtended) {
        this.classExtended = classExtended;
        extendsAClass= true;
    }

    /**
     *
     */
    @Override
    public String toString() {
        if (structureChanges) {
            //TODO: fetch the to string values here
        }
        return toString;
    }
}
