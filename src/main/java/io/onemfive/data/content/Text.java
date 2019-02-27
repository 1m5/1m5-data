package io.onemfive.data.content;

public class Text extends Content {

    public Text() {
    }

    public Text(byte[] body, String contentType) {
        super(body, contentType);
    }

    public Text(byte[] body, String contentType, String name, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        super(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
    }
}
