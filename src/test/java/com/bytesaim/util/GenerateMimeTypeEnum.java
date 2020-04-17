package com.bytesaim.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 */
public class GenerateMimeTypeEnum {

    private final PrintStream stream;

    private final String filePath;

    private Map<String, ArrayList<String>> mimeMap = new HashMap<>();

    public GenerateMimeTypeEnum(PrintStream stream, String filePath) {
        this.stream = stream;
        this.filePath = filePath;
    }

    public void generate() throws IOException {
        walkFile();
        write("public enum MimeTypes {");
        write("");
        writeEnums();
        writeConstructor();
        writeGet();
        write("");
        write("}");
    }

    public void write(String text) {
        stream.println(text);
    }

    private void writeConstructor() {
        stream.println("    ;\n" +
                "\n" +
                "    private final String value;\n" +
                "\n" +
                "    MimeTypes(String value) {\n" +
                "        this.value = value;\n" +
                "    }\n" +
                "\n" +
                "    public String getValue() {\n" +
                "        return value;\n" +
                "    }");
    }

    private void writeEnums() {
        for (String key : mimeMap.keySet()) {
            stream.println(String.format("\t%s(\"%s\"),", getIdentifier(key), key));
        }
    }

    public void writeGet() {
        Map<String, Integer> caseName = new HashMap<>();
        stream.println("\n    public static MimeTypes get(String fileName) {\n" +
                "        String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();\n" +
                "        switch (ext) {");

        for (String key : mimeMap.keySet()) {
            ArrayList<String> arr = mimeMap.get(key);
            for (String mime : arr) {
                boolean hasMime = caseName.containsKey(mime);
                stream.println(String.format("            case \"%s\":\n" +
                        "                return %s;", (hasMime ? mime + "_" + caseName.get(mime) : mime),
                                                        getIdentifier(key)));

                if (hasMime) {
                    caseName.put(mime, caseName.get(mime) + 1);
                } else {
                    caseName.put(mime, 1);
                }
            }
        }

        stream.println("            default:\n" +
                "                return TEXT_HTML;\n" +
                "        }\n" +
                "    }");
    }

    private String getIdentifier(String key) {
        return key.replaceAll("-", "_")
                .replaceAll("/", "_")
                .replaceAll("\\.", "_")
                .replaceAll("\\(", "_")
                .replaceAll("\\)", "_")
                .replaceAll(" +", "_")
                .toUpperCase();
    }

    private void walkFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("//")) { //comment
                    continue;
                }
                String[] parts = line.trim().split("\t");
                if (parts.length < 2) {
                    continue;
                }
                put(parts[1], parts[0].substring(1));
            }
        }
    }

    private void put(String key, String value) {
        if (!mimeMap.containsKey(key)) {
            ArrayList<String> arr = new ArrayList<>();
            mimeMap.put(key, arr);
        }
        mimeMap.get(key).add(value);
    }

    public static void main(String[] args) throws IOException {
        GenerateMimeTypeEnum generateMimeTypeEnum = new GenerateMimeTypeEnum(System.out, "src/test/resources/mimetypes.txt");
        generateMimeTypeEnum.generate();
    }

}
