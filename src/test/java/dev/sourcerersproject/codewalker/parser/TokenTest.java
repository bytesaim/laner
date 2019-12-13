package dev.sourcerersproject.codewalker.parser;

import dev.sourcerersproject.codewalker.util.RandomGenerator;
import dev.sourcerersproject.codewalker.util.TokenType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TokenTest {

    ArrayList<Token> tokens = new ArrayList<Token>();

    @Before
    public void Before() {
        for (int a = 0; a < RandomGenerator.generateRandomNumber(30, 10); a++) {
            tokens.add(new Token("Token"+a, TokenType.UNKNOWN, a, a+(a+3)));
        }
    }

    @Test
    public void TokenTest() {
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    @After
    public void After() {

    }

}
