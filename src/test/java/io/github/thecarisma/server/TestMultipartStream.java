package io.github.thecarisma.server;

import io.github.thecarisma.server.MultipartData;
import io.github.thecarisma.server.MultipartStream;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Adewale Azeez "azeezadewale98@gmail.com"
 */
public class TestMultipartStream {

    @Test
    public void Test1() throws IOException {
        String content = "----AaB03x\r\n"
                + "Content-Disposition: form-data; name=\"submit-name\"\r\n"
                + "\r\n"
                + "Larry\r\n"
                + "----AaB03x\r\n"
                + "Content-Disposition: form-data; name=\"files\"; filename=\"file1.txt\"\r\n"
                + "Content-Type: text/plain\r\n"
                + "\r\n"
                + "HELLO WORLD!\r\n"
                + "----AaB03x--\r\n";
        MultipartStream multipartStream = new MultipartStream(content, "boundary=--AaB03x");
        while (multipartStream.hasnext()) {
            MultipartData multipartData = multipartStream.next();
            System.out.println(multipartData.getName());
        }
    }

}
