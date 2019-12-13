package dev.sourcerersproject.codewalker.parser;

import dev.sourcerersproject.codewalker.util.TokenType;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Adewale Azeez <azeezadewale98@gmail.com>
 * :date: 08 September 2019
 * :time: 01:48 AM
 * :filename: Token.java
 */
public class Token {

    /**
     *
     */
    private String value = "" ;

    /**
     *
     */
    private int lineNumber = 1 ;

    /**
     *
     */
    private int columnNumber = 1;

    /**
     *
     */
    private String compoundValue = "" ;

    /**
     *
     */
    private TokenType tokenType = TokenType.UNKNOWN;

    /**
     *
     * @param value
     * @param tokenType
     * @param lineNumber
     * @param columnNumber
     */
    public Token(String value, TokenType tokenType, int lineNumber, int columnNumber) {
        this.value = value ;
        this.tokenType = tokenType;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     *
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     *
     * @return
     */
    public TokenType getTokenType() {
        return tokenType;
    }

    /**
     *
     * @param tokenType
     */
    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    /**
     *
     * @return
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     *
     * @param columnNumber
     */
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     *
     * @return
     */
    public String getCompoundValue() {
        return compoundValue;
    }

    /**
     *
     * @param compoundValue
     */
    public void setCompoundValue(String compoundValue) {
        this.compoundValue = compoundValue;
    }

    /**
     *
     * @return the raw string value
     */
    @Override
    public String toString() {
        return String.format("%s@%d:value=%s,tokenType=%s,lineNumber=%d,columnNumber=%d", getClass().getName(), hashCode(), value, tokenType, lineNumber, columnNumber);
    }

}
