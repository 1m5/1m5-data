package io.onemfive.data.content;

public class HTML extends Text {

    public HTML() {
        contentType = "text/html";
    }

    public HTML(byte[] body) {
        super.body = body;
        contentType = "text/html";
    }

    public HTML(byte[] body, String contentType) {
        super(body, contentType);
    }

    public HTML(byte[] body, String contentType, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, name, generateHash, generateFingerprint);
    }
}
