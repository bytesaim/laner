package io.github.thecarisma.laner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class MultipartStream {

    public final String boundary ;
    private BufferedReader bufferedReader;
    private String paddingLeft = "--";

    public MultipartStream(String rawBody, String boundary) {
        bufferedReader = new BufferedReader(new StringReader(rawBody));
        this.boundary = paddingLeft + boundary;
    }

    public MultipartStream(BufferedReader bufferedReader, String boundary) {
        this.bufferedReader = bufferedReader;
        this.boundary = paddingLeft + boundary;
    }

    public MultipartStream(BufferedReader bufferedReader, String boundary, String paddingLeft) {
        this.bufferedReader = bufferedReader;
        this.boundary = paddingLeft + boundary;
    }

    public boolean hasnext() throws IOException {
        StringBuilder tmpBoundary = new StringBuilder();
        while (bufferedReader.ready()) {
            char c = (char) bufferedReader.read();
            if (c == '\r') {
                int x = bufferedReader.read(); //read \n\r
                break;
            }
            tmpBoundary.append(c);
        }
        return tmpBoundary.toString().equals(boundary);
    }

    public MultipartData next() throws IOException {
        MultipartData multipartData = new MultipartData();
        StringBuilder readBytes = new StringBuilder();
        int lineCount = 0;
        while (bufferedReader.ready()) {
            char c = (char) bufferedReader.read();
            if (c == '\r') {
                System.out.println(readBytes);
                if (lineCount == 2) {
                    String[] s1 = readBytes.toString().split(";");
                    for (String s : s1) {
                        String[] s2;
                        if (s.contains("=\"")) {
                            s2 = s.split("=");
                            multipartData.getHeaders().put(s2[0].trim(), s2[1].substring(s2[1].indexOf("\"") + 1, s2[1].lastIndexOf("\"")));
                        } else {
                            s2 = s.split(":");
                            multipartData.getHeaders().put(s2[0].trim(), s2[1].trim());
                        }
                    }
                    multipartData.setName(multipartData.getHeaders().get("name"));
                    readBytes = new StringBuilder();
                }
                lineCount++;
                if (lineCount <= 2) {
                    readBytes.append(';');
                    int x = bufferedReader.read();
                    continue;
                }
            }
            readBytes.append(c);
        }
        return multipartData;
    }

}
