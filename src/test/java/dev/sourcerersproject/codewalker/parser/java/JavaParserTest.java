package dev.sourcerersproject.codewalker.parser.java;

import dev.sourcerersproject.codewalker.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dev.sourcerersproject.codewalker.attrib.java.Package;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class JavaParserTest {

    @Before
    public void BeforeTest() {

    }

    @Test
    public void FromStringSourceTest() throws IOException {
        JavaParser javaParser = JavaParser.fromStringSource(FileUtil.readFile("./src/test/resources/Test1.java", Charset.defaultCharset()));
        for (Package pakkage : javaParser.getPackages()) {
            System.out.println(pakkage);
        }
        //System.out.println(javaParser.getRawString());

    }

    @After
    public void AfterTest() {

    }
}
