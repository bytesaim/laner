package io.github.thecarisma.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class MultipartStream {

    public final String boundary ;
    private BufferedReader bufferedReader;
    private String paddingLeft = "--";
    private boolean hasNext = false;
    private boolean streamDone = false;

    public MultipartStream(String rawBody, String boundary) {
        if (boundary.isEmpty()) {
            hasNext = false;
            streamDone = true;
        }
        bufferedReader = new BufferedReader(new StringReader(rawBody));
        this.boundary = boundary.trim().replaceAll("boundary=", paddingLeft);
    }

    public MultipartStream(BufferedReader bufferedReader, String boundary) {
        if (boundary.isEmpty()) {
            hasNext = false;
            streamDone = true;
        }
        this.bufferedReader = bufferedReader;
        this.boundary = boundary.trim().replaceAll("boundary=", paddingLeft);
    }

    public MultipartStream(BufferedReader bufferedReader, String boundary, String paddingLeft) {
        if (boundary.isEmpty()) {
            hasNext = false;
            streamDone = true;
        }
        this.bufferedReader = bufferedReader;
        this.boundary = boundary.trim().replaceAll("boundary=", paddingLeft);
    }

    public boolean hasnext() throws IOException {
        if (!hasNext && !streamDone) {
            StringBuilder tmpBoundary = new StringBuilder();
            while (bufferedReader.ready()) {
                char c = (char) bufferedReader.read();
                if (c == '\r') {
                    int x = bufferedReader.read(); //read \r\n
                    break;
                }
                tmpBoundary.append(c);
            }
            hasNext = tmpBoundary.toString().equals(boundary);
        }
        return hasNext;
    }

    public MultipartData next() throws IOException {
        MultipartData multipartData = new MultipartData();
        StringBuilder readBytes = new StringBuilder();
        int lineCount = 1;
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            if (inputLine.startsWith(boundary)) {
                if (inputLine.endsWith("--")) {
                    hasNext = false;
                    streamDone = true;
                } else {
                    hasNext = true;
                }
                break;
            }
            readBytes.append(inputLine);
            if (lineCount == 2) {
                String[] s1 = readBytes.toString().replaceAll("\r\n", ";").split(";");
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
            if (lineCount < 2) {
                readBytes.append(";");
            } else {
                readBytes.append("\r\n");
            }
            lineCount++;
        }
        multipartData.setBody(readBytes.toString().trim());
        return multipartData;
    }

}
