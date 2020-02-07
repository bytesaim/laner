package io.github.thecarisma.server;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 * https://www.sitepoint.com/mime-types-complete-list/
 */
public class MimeTypes {

    public static String THREE_DM = "x-world/x-3dmf";

    public static String CSS = "text/css";

    public static String CSS_APP = "application/x-pointplus";

    public static String TEXT_HTML = "text/html";

    public static String getMimeType(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
        switch (ext) {
            case "3dm":
                return THREE_DM;
            case "css":
                return CSS;
            case "html":
            default:
                return TEXT_HTML;
        }
    }

}
