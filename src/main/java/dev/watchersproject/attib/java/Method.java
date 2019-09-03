package dev.watchersproject.attib.java;

import dev.watchersproject.enums.AccessModifier;
import dev.watchersproject.enums.AccessSpecifier;

import java.util.ArrayList;

public class Method {

    /**
     *
     */
    public AccessModifier accessModifier = AccessModifier.PRIVATE;

    /**
     *
     */
    public AccessSpecifier accessSpecifier = AccessSpecifier.NONE;

    /**
     *
     */
    private boolean hasReturnType = false;

    /**
     *
     */
    private String returnType = "void" ;

    /**
     * 
     */
    private String Identifier = "";

    /**
     *
     */
    private boolean isConstructor = false;

    /**
     *
     */
    private ArrayList<Parameter> parameters = new ArrayList<Parameter>();

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
    }

    /**
     * 
     * @return
     */
    public String getIdentifier() {
        return Identifier;
    }

    /**
     * 
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    /**
     *
     * @return
     */
    public boolean isHasReturnType() {
        return hasReturnType;
    }

    /**
     *
     * @param hasReturnType
     */
    public void setHasReturnType(boolean hasReturnType) {
        this.hasReturnType = hasReturnType;
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
    }

    /**
     *
     * @return
     */
    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    /**
     *
     * @param parameter
     */
    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    /**
     *
     * @param parameter
     */
    public void removeParameter(Parameter parameter) {
        parameters.remove(parameter);
    }

    /**
     *
     * @param index
     */
    public void removeParameter(int index) {
        parameters.remove(index);
    }

}
