package com.bytesaim.http;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 *
 * https://www.sitepoint.com/mime-types-complete-list/
 *
 * Generated with com.bytesaim.util.GenerateMimeTypeEnum
 * with file resources/mimetypes
 */
public enum MimeTypes {

    THREE_DM("x-world/x-3dmf"),
    TEXT_HTML("x-world/x-3dmf"),

    ;

    private final String value;

    MimeTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MimeTypes get(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (ext) {
            case "3dm":
                return THREE_DM;
            default:
                return TEXT_HTML;
        }
    }

}
