package io.github.thecarisma.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class MultipartStream {

    public final String boundary ;
    private BufferedReader bufferedReader;
    private String paddingLeft = "--";
    private boolean hasNext_ = false;
    private boolean streamDone = false;

    public MultipartStream(String rawBody, String boundary) {
        if (boundary.isEmpty()) {
            hasNext_ = false;
            streamDone = true;
        }
        bufferedReader = new BufferedReader(new StringReader(rawBody));
        this.boundary = boundary.trim().replaceAll("boundary=", paddingLeft);
    }

    public MultipartStream(BufferedReader bufferedReader, String boundary) {
        if (boundary.isEmpty()) {
            hasNext_ = false;
            streamDone = true;
        }
        this.bufferedReader = bufferedReader;
        this.boundary = boundary.trim().replaceAll("boundary=", paddingLeft);
    }

    public MultipartStream(BufferedReader bufferedReader, String boundary, String paddingLeft) {
        if (boundary.isEmpty()) {
            hasNext_ = false;
            streamDone = true;
        }
        this.bufferedReader = bufferedReader;
        this.boundary = boundary.trim().replaceAll("boundary=", paddingLeft);
    }

    public boolean hasNext() throws IOException {
        if (!hasNext_ && !streamDone) {
            StringBuilder tmpBoundary = new StringBuilder();
            while (bufferedReader.ready()) {
                char c = (char) bufferedReader.read();
                if (c == '\r') {
                    int x = bufferedReader.read(); //read \r\n
                    break;
                }
                tmpBoundary.append(c);
            }
            hasNext_ = tmpBoundary.toString().equals(boundary);
        }
        return hasNext_;
    }

    public MultipartData next() throws IOException {
        MultipartData multipartData = new MultipartData();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream currentLineOut = new ByteArrayOutputStream();
        boolean parsedHead = false;
        char c;
        while (bufferedReader.ready() && (c = (char) bufferedReader.read()) != (char) -1) {
            if (!parsedHead) {
                if (c == '\r') {
                    out.write(c);
                    c = (char) bufferedReader.read(); //read \r\n
                    if (c == '\n') {
                        out.write(c);
                        String out_ = new String(out.toByteArray());
                        if (out_.endsWith("\r\n\r\n")) {
                            String[] s1 = out_.replaceAll("\r\n", ";").split(";");
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
                            out = new ByteArrayOutputStream();
                            multipartData.setName(multipartData.getHeaders().get("name"));
                            parsedHead = true;
                        }
                    }
                } else {
                    out.write(c);
                }
                continue;
            }
            currentLineOut.write(c);
            String currentLineOut_ = new String(currentLineOut.toByteArray());
            if (currentLineOut_.endsWith("\r\n")) {
                if (currentLineOut_.startsWith(boundary)) {
                    if (currentLineOut_.endsWith("--\r\n")) {
                        hasNext_ = false;
                        streamDone = true;
                    } else {
                        hasNext_ = true;
                    }
                    break;
                }
                out.write(currentLineOut.toByteArray());
                currentLineOut = new ByteArrayOutputStream();
            }
        }
        System.out.println(new String(out.toByteArray()));
        /*while ((inputLine = bufferedReader.readLine()) != null) {
            if (inputLine.startsWith(boundary)) {
                if (inputLine.endsWith("--")) {
                    hasNext_ = false;
                    streamDone = true;
                } else {
                    hasNext_ = true;
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
        }*/
        multipartData.setBody(out.toByteArray());
        return multipartData;
    }

}
