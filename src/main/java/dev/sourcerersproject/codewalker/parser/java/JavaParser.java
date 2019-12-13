package dev.sourcerersproject.codewalker.parser.java;

import dev.sourcerersproject.codewalker.attrib.java.Package;
import dev.sourcerersproject.codewalker.parser.Token;
import dev.sourcerersproject.codewalker.parser.Tokenizer;

import java.util.ArrayList;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Adewale Azeez <azeezadewale98@gmail.com>
 * :date: 08 September 2019
 * :time: 01:26 AM
 * :filename: JavaParser.java
 */
public class JavaParser {

    /**
     *
     */
    private ArrayList<Package> packages = new ArrayList<Package>();

    /**
     *
     */
    private boolean structureChanges = true;

    /**
     *
     */
    private String rawString = "";

    /**
     *
     */
    public JavaParser() {

    }

    /**
     *
     * @param source
     * @return
     */
    public static JavaParser fromStringSource(String source) {
        JavaParser javaParser = new JavaParser();
        javaParser.setRawString(source);
        javaParser.parse(source);
        return javaParser;
    }

    /**
     *
     */

    /**
     *
     * @return
     */
    public ArrayList<Package> getPackages() {
        return packages;
    }

    /**
     *
     * @param packkage
     */
    public void addPackage(Package packkage) {
        packages.add(packkage);
        structureChanges = true;
    }

    /**
     *
     * @param packkage
     */
    public void removePackage(Package packkage) {
        packages.remove(packkage);
        structureChanges = true;
    }

    /**
     *
     * @param index
     */
    public void removePackage(int index) {
        packages.remove(index);
        structureChanges = true;
    }

    /**
     *
     * @param rawString
     * @return
     */
    private void setRawString(String rawString) {
        this.rawString = rawString ;
    }

    /**
     *
     */
    public String getRawString() {
        return rawString;
    }

    /**
     *
     * @param parse
     */
    private void parse(String parse) {
        Tokenizer tokenizer = new Tokenizer(parse);
        for (int a = 0; a < tokenizer.getTokens().size(); a++) {
            String token = tokenizer.getTokens().get(a).getValue();
            if (token.equals("package")) {
                System.out.println("found package");
            }
        }
    }

}
