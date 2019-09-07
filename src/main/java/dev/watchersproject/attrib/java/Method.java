package dev.watchersproject.attrib.java;

import dev.watchersproject.enums.AccessModifier;
import dev.watchersproject.enums.AccessSpecifier;
import dev.watchersproject.enums.StatementType;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Azeez Adewale <azeezadewale98@gmail.com>
 * :date: 03 September 2019
 * :time: 12:10 AM
 * :filename: Method.java
 */
public class Method {

    /**
     *
     */
    private AccessModifier accessModifier = AccessModifier.PRIVATE;

    /**
     *
     */
    private AccessSpecifier accessSpecifier = AccessSpecifier.NONE;

    /**
     *
     */
    private boolean hasReturnType = false;

    /**
     *
     */
    private boolean isNative = false ;

    /**
     *
     */
    private boolean isAbstract = false ;

    /**
     *
     */
    private String returnType = "void" ;

    /**
     * 
     */
    private String identifier = "";

    /**
     *
     */
    private boolean isConstructor = false;

    /**
     *
     */
    private ArrayList<Field> parameters = new ArrayList<Field>();

    /**
     *
     */
    private ArrayList<Field> localFields = new ArrayList<Field>();

    /**
     *
     */
    private ArrayList<Statement> statements = new ArrayList<Statement>();

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
    public boolean isConstructor() {
        return isConstructor;
    }

    /**
     * 
     * @param constructor
     */
    public void setConstructor(boolean constructor) {
        isConstructor = constructor;
        structureChanges = true;
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
    public boolean hasReturnType() {
        return hasReturnType;
    }

    /**
     *
     * @return
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     *
     * @param returnType
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
        if (returnType.equals("void")) {
            hasReturnType = false;
        }
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public ArrayList<Field> getParameters() {
        return parameters;
    }

    /**
     *
     * @param field
     */
    public void addParameter(Field field) {
        parameters.add(field);
        structureChanges = true;
    }

    /**
     *
     * @param field
     */
    public void removeParameter(Field field) {
        parameters.remove(field);
        structureChanges = true;
    }

    /**
     *
     * @param index
     */
    public void removeParameter(int index) {
        parameters.remove(index);
        structureChanges = true;
    }

    /**
     *
     * @return
     */
    public ArrayList<Field> getLocalFields() {
        if (structureChanges) {
            for (Statement statement : statements) {
                if (statement.getType() == StatementType.DECLARATION) {
                    localFields.add((Field) statement.getObject());
                }
            }
        }
        return localFields;
    }

    /**
     *
     * @return
     */
    public ArrayList<Statement> getStatements() {
        return statements;
    }

    /**
     *
     * @param statement
     */
    public void addStatement(Statement statement) {
        statements.add(statement);
        structureChanges = true;
    }

    /**
     *
     * @param statement
     */
    public void removeStatement(Statement statement) {
        statements.remove(statement);
        structureChanges = true;
    }

    /**
     *
     * @param index
     */
    public void removeStatement(int index) {
        statements.remove(index);
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
     */
    @Override
    public String toString() {
        if (structureChanges) {
            //TODO: fetch the to string values here
        }
        return toString;
    }

    /**
     *
     * @return
     */
    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    /**
     *
     * @param accessModifier
     */
    public void setAccessModifier(AccessModifier accessModifier) {
        this.accessModifier = accessModifier;
    }

    /**
     *
     * @return
     */
    public AccessSpecifier getAccessSpecifier() {
        return accessSpecifier;
    }

    /**
     *
     * @param accessSpecifier
     */
    public void setAccessSpecifier(AccessSpecifier accessSpecifier) {
        this.accessSpecifier = accessSpecifier;
    }

    /**
     *
     * @return
     */
    public boolean isNative() {
        return isNative;
    }

    /**
     *
     * @param aNative
     */
    public void setNative(boolean aNative) {
        isNative = aNative;
    }

    /**
     *
     * @return
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     *
     * @param isAbstract
     */
    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
}
