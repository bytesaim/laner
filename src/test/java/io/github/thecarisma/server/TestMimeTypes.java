package io.github.thecarisma.server;

import org.junit.Test;

public class TestMimeTypes {

    @Test
    public void Test1() {
        String[] names = { "text.html", "css", ".", "avery.try.abc", "", "..." } ;
        for (String name : names) {
            String ext = name.substring(name.lastIndexOf('.') + 1, name.length());
            System.out.println(ext);
        }
    }

    @Test
    public void Test2() {
        String[] names = { "file.3dm", "text.html", "css", ".js", "avery.try.mp4", "mp3", "..." } ;
        for (String name : names) {
            System.out.println(MimeTypes.getMimeType(name));
        }
    }

}
