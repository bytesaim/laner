package dev.sourcerersproject.codewalker.attrib.java;

import dev.sourcerersproject.codewalker.parser.Token;
import dev.sourcerersproject.codewalker.parser.Tokenizer;
import dev.sourcerersproject.codewalker.util.TokenType;

import java.util.ArrayList;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Adewale Azeez <azeezadewale98@gmail.com>
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
    private void format() {
        if (formated) return;
        ArrayList<Token> tokens = Tokenizer.fromString(value);
        StringBuilder stringBuilder = new StringBuilder();
        for (int a = 0; a < tokens.size(); a++) {
            Token token = tokens.get(a);
            stringBuilder.append(token.getValue());
            if (a == tokens.size() - 1) {
                break;
            }
            if (tokens.get(a).getTokenType() != TokenType.OPEN_BRACE &&
                    tokens.get(a).getTokenType() != TokenType.OPEN_BRACKET &&
                    tokens.get(a).getTokenType() != TokenType.OPEN_SQUARE_BRACKET &&
                    tokens.get(a+1).getTokenType() != TokenType.DOT) {
                stringBuilder.append(" ");
            }
        }
        value = stringBuilder.toString();
    }

}
