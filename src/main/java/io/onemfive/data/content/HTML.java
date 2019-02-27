package io.onemfive.data.content;

public class HTML extends Text {

    public HTML() {
    }

    public HTML(byte[] body, String contentType) {
        super(body, contentType);
    }

    public HTML(byte[] body, String contentType, String name, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        super(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
    }
}
