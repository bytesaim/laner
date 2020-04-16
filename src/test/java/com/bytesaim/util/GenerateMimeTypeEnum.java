package com.bytesaim.util;

import java.io.PrintStream;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class GenerateMimeTypeEnum {

    private final PrintStream stream;

    public GenerateMimeTypeEnum(PrintStream stream, String filePath) {
        this.stream = stream;
    }

    public void generate() {
        writeConstructor();
    }

    private void writeConstructor() {
        stream.println("");
    }

    public static void main(String[] args) {
        GenerateMimeTypeEnum generateMimeTypeEnum = new GenerateMimeTypeEnum(System.out, "");
        generateMimeTypeEnum.generate();
    }

}
