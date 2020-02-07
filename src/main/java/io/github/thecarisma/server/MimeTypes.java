package io.github.thecarisma.server;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 * https://www.sitepoint.com/mime-types-complete-list/
 */
public class MimeTypes {

    public static String THREE_DM = "x-world/x-3dmf";

    public static String THREE_DMF = "x-world/x-3dmf";

    public static String A = "application/octet-stream";

    public static String AAB = "application/x-authorware-bin";

    public static String AAM = "application/x-authorware-map";

    public static String AAS = "application/x-authorware-seg";

    public static String ABC = "text/vnd.abc";

    public static String ACGI = "text/html";

    public static String AFL = "video/animaflex";

    public static String AI = "application/postscript";

    public static String AIF = "audio/aiff";

    public static String AIF_X = "audio/x-aiff";

    public static String AIFC = "audio/aiff";

    public static String AIFC_X = "audio/x-aiff";

    public static String AIFF = "audio/aiff";

    public static String AIFF_X = "audio/x-aiff";

    public static String AIM = "application/x-aim";

    public static String AIP = "text/x-audiosoft-intra";

    public static String ANI = "application/x-navi-animation";

    public static String AOS = "application/x-nokia-9000-communicator-add-on-software";

    public static String APS = "application/mime";

    public static String ARC = "application/octet-stream";

    public static String ARJ = "application/arj";

    public static String ARJ_OCTET = "application/octet-stream";

    public static String ART = "image/x-jg";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

    public static String THREE_DM = "";

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
