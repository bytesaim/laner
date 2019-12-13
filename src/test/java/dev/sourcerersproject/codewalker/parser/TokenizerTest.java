package dev.sourcerersproject.codewalker.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TokenizerTest {

    Tokenizer tokenizer, tokenizer2;

    @Before
    public void Before() {
        tokenizer = new Tokenizer("=====");
        tokenizer2 = new Tokenizer("else if (chars[a] == '_') {\ntoken.setTokenType(TokenType.UNDERSCORE);");
    }

    @Test
    public void Test1() {
        for (Token token : tokenizer.getTokens()) {
            System.out.println(token);
        }
        System.out.println();
        for (Token token : tokenizer2.getTokens()) {
            System.out.println(token);
        }
    }

    @After
    public void After() {

    }

}
