package com.bytesaim.http;

import com.bytesaim.exceptions.ResponseHeaderException;
import com.bytesaim.util.Attributes;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 *
 * https://expressjs.com/en/5x/api.html#res
 */
public class Response {

    protected OutputStream out;
    private Map<String, String[]> headers = new HashMap<>();
    private String rawResponseHead = "" ;
    private String reasonPhrase = "" ;
    private StatusCode statusCode = StatusCode.OK;
    private boolean headersNeedsParsing = true;
    public boolean headersSent = false;
    public boolean addDefaultHeaders = true;
    public final Server server ;
    private int bufferSize = 8192;

    public Response(Server server, OutputStream out) {
        this.server = server;
        this.out = out;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        if (reasonPhrase.isEmpty()) {
            reasonPhrase = statusCode.getValue();
        }
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public void appendHeader(String key, String... values) throws ResponseHeaderException {
        if (headersSent) {
            throw new ResponseHeaderException();
        }
        headers.put(key, values);
        headersNeedsParsing = true;
    }

    public String[] removeHeader(String key) {
        return headers.remove(key);
    }

    public String getHttpVersion() {
        return "HTTP/1.1";
    }

    public void write(String data) throws IOException {
        write(data.getBytes());
    }

    public void write(byte[] data) throws IOException {
        if (!headersSent) {
            sendResponseHead();
        }
        out.write(data);
    }

    public void sendFile(File file) throws IOException, ResponseHeaderException {
        sendFile(file, getFileType(file));
    }

    public void sendFileInRange(File file, long from, long to) throws IOException, ResponseHeaderException {
        sendFileInRange(file, getFileType(file), from, to);
    }

    public void sendFile(File file, String contentType) throws IOException, ResponseHeaderException {
        if (!headersSent) {
            FileInputStream input = new FileInputStream(file);
            long fileSize = file.length();
            appendHeader("Content-Length", "" + fileSize);
            appendHeader("Content-Type", contentType);
            sendResponseHead();
            final byte[] buffer = new byte[bufferSize];
            for (int read = input.read(buffer); read >= 0; read = input.read(buffer))
                out.write(buffer, 0, read);
            out.flush();
            input.close();
        }
    }

    //TODO: needs keep alive connection
    public void sendFileInRange(File file, String contentType, long from, long to) throws IOException, ResponseHeaderException {
        if (!headersSent) {
            FileInputStream input = new FileInputStream(file);
            long fileSize = (to - from);
            appendHeader("Content-Type", contentType);
            appendHeader("Content-Range", String.format("bytes %d-*/*", from, to));
            appendHeader("Content-Length", ""+file.length());
            final byte[] buffer = new byte[bufferSize];
            long skipped = input.skip(from);
            int i = 0;
            while (i < fileSize) {
                out.write(input.read());
                ++i;
            }
            out.flush();
            input.close();
        }
    }

    public void downloadFile(File file, String fileName) throws IOException, ResponseHeaderException {
        if (!headersSent) {
            FileInputStream input = new FileInputStream(file);
            long fileSize = file.length();
            appendHeader("Content-Length", "" + fileSize);
            appendHeader("Content-Type", getFileType(file));
            appendHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            sendResponseHead();
            final byte[] buffer = new byte[bufferSize];
            for (int read = input.read(buffer); read >= 0; read = input.read(buffer))
                out.write(buffer, 0, read);
            out.flush();
            input.close();
        }
    }

    public void close(String data) throws IOException {
        close(data.getBytes());
    }

    /*public void flush() throws IOException {
        out.flush();
    }*/

    public void close(byte[] data) throws IOException {
        write(data);
        close();
    }

    public void close() throws IOException {
        if (!headersSent) {
            sendResponseHead();
        }
        out.close();
    }

    private void sendResponseHead() throws IOException {
        parseHeaders();
        headersSent = true;
        write(rawResponseHead.getBytes());
    }

    private void parseHeaders() {
        if (headersNeedsParsing) {
            rawResponseHead = String.format("%s %d %s\r\n", getHttpVersion(), statusCode.getCode(), getReasonPhrase());
            if (addDefaultHeaders) {
                try {
                    appendHeader("Host", server.getHost());
                    appendHeader("Date", new Date().toString());
                    appendHeader("User-Agent", String.format("%s/%s (%s; x%s)",
                            Attributes.NAME, Attributes.VERSION, System.getProperties().getProperty("os.name"), System.getProperties().getProperty("sun.arch.data.model")));
                } catch (ResponseHeaderException e) {
                    //impossible
                    e.printStackTrace();
                }
            }
            for (String key : headers.keySet()) {
                String[] values = headers.get(key);
                StringBuilder header = new StringBuilder(key + ": ");
                for (int i = 0; i < values.length; ++i) {
                    header.append(values[i]);
                    if (i != values.length - 1) {
                        header.append(", ");
                    }
                }
                rawResponseHead += header + "\r\n";
            }
            rawResponseHead += "\r\n";
        }
    }

    public boolean isClosed() {
        return headersSent;
    }

    private String getFileType(File file) throws IOException {
        String type = Files.probeContentType(file.toPath());
        return (type != null ?  type : "application/octet-stream") ;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = (bufferSize > 0 ? bufferSize : this.bufferSize);
    }
}
