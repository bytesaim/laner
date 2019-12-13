package dev.sourcerersproject.codewalker.parser;

import dev.sourcerersproject.codewalker.util.TokenType;
import dev.sourcerersproject.codewalker.util.Type;
import javafx.scene.input.TouchEvent;

import java.util.ArrayList;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Adewale Azeez <azeezadewale98@gmail.com>
 * :date: 08 September 2019
 * :time: 01:26 PM
 * :filename: Tokenizer.java
 */
public class Tokenizer {

    /**
     *
     */
    private ArrayList<Token> tokens = new ArrayList<Token>();

    /**
     *
     */
    public Tokenizer() {

    }

    /**
     *
     * @param source
     */
    public Tokenizer(String source) {
        parse(String.format("%s ", source));
    }

    /**
     *
     * @param source
     * @return
     */
    public static ArrayList<Token> fromString(String source) {
        Tokenizer tokenizer = new Tokenizer(source);
        return tokenizer.tokens;
    }

    /**
     *
     * @return
     */
    public ArrayList<Token> getTokens() {
        return tokens;
    }

    /**
     *
     * @param token
     */
    public void appendToken(Token token) {
        tokens.add(token);
    }

    /**
     *
     */
    private void parse(String source) {
        boolean parseidentifier = true;
        boolean expectingSpace= false;
        int lineNumber = 1;
        int column = 1;
        char chars[] = source.toCharArray();
        for (int a = 0; a <  chars.length; a++) {

            Token token = new Token(String.format("%c", chars[a]), TokenType.UNKNOWN, lineNumber, column);
            if (chars[a] == '\n') {
                lineNumber++;
                column = 1;
            }
            if (Type.isPunctuation(chars[a])) {
                if (chars[a] == '{') {
                    token.setTokenType(TokenType.OPEN_BRACE);
                } else if (chars[a] == '}') {
                    token.setTokenType(TokenType.CLOSE_BRACE);
                } else if (chars[a] == '(') {
                    token.setTokenType(TokenType.OPEN_BRACKET);
                } else if (chars[a] == ')') {
                    token.setTokenType(TokenType.CLOSE_BRACKET);
                } else if (chars[a] == '[') {
                    token.setTokenType(TokenType.OPEN_SQUARE_BRACKET);
                } else if (chars[a] == ']') {
                    token.setTokenType(TokenType.CLOSE_SQUARE_BRACKET);
                } else if (chars[a] == '.') {
                    token.setTokenType(TokenType.DOT);
                } else if (chars[a] == ',') {
                    token.setTokenType(TokenType.COMMA);
                } else if (chars[a] == '=') {
                    token.setTokenType(TokenType.EQUAL);
                } else if (chars[a] == '-') {
                    token.setTokenType(TokenType.MINUS);
                } else if (chars[a] == '+') {
                    token.setTokenType(TokenType.PLUS);
                } else if (chars[a] == '*') {
                    token.setTokenType(TokenType.ASTERISK);
                } else if (chars[a] == '/') {
                    token.setTokenType(TokenType.BACKWARD_SLASH);
                } else if (chars[a] == '\\') {
                    token.setTokenType(TokenType.FORWARD_SLASH);
                } else if (chars[a] == '%') {
                    token.setTokenType(TokenType.PERCENTAGE);
                } else if (chars[a] == '<') {
                    token.setTokenType(TokenType.LESSER_THAN);
                } else if (chars[a] == '>') {
                    token.setTokenType(TokenType.GREATER_THAN);
                } else if (chars[a] == ':') {
                    token.setTokenType(TokenType.COLON);
                } else if (chars[a] == ';') {
                    token.setTokenType(TokenType.SEMI_COLON);
                } else if (chars[a] == '\'') {
                    token.setTokenType(TokenType.QUOTE);
                } else if (chars[a] == '"') {
                    token.setTokenType(TokenType.DOUBLE_QUOTE);
                } else if (chars[a] == '#') {
                    token.setTokenType(TokenType.NUMBER_SIGN);
                } else if (chars[a] == '!') {
                    token.setTokenType(TokenType.EXCLAMATION);
                } else if (chars[a] == '?') {
                    token.setTokenType(TokenType.QUESTION_MARK);
                } else if (chars[a] == '@') {
                    token.setTokenType(TokenType.AT_SIGN);
                } else if (chars[a] == '_') {
                    token.setTokenType(TokenType.UNDERSCORE);
                } else if (chars[a] == '~') {
                    token.setTokenType(TokenType.TILDE);
                } else if (chars[a] == '|') {
                    token.setTokenType(TokenType.BINARY_OPERATOR);
                } else if (chars[a] == '`') {
                    token.setTokenType(TokenType.GRAVE);
                } else if (chars[a] == '^') {
                    token.setTokenType(TokenType.CIRCUMFLEX);
                } else {
                    token.setTokenType(TokenType.PUNCTUATION);
                }

            } else if (Type.isDigit(chars[a])) {
                StringBuilder stringBuilder = new StringBuilder();
                do {
                    stringBuilder.append(chars[a]);
                    a++;
                } while (!Type.isPunctuation(chars[a]) && !Type.isWhitespace(chars[a]));
                token = new Token(stringBuilder.toString(), TokenType.NUMBER, lineNumber, column);
                a--;

            } else if (Type.isLetter(chars[a])) {
                StringBuilder stringBuilder = new StringBuilder();
                do {
                    stringBuilder.append(chars[a]);
                    a++;
                } while (!Type.isPunctuation(chars[a]) && !Type.isWhitespace(chars[a]));
                token = new Token(stringBuilder.toString(), TokenType.WORD, lineNumber, column);
                a--;

            } else if (Type.isWhitespace(chars[a])) {
                continue;
            }
            tokens.add(token);
            column++;
        }
    }

}
