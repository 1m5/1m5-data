package io.onemfive.data.content;

public class Video extends Binary {

    public Video() {
    }

    public Video(byte[] body, String contentType) {
        super(body, contentType);
    }

    public Video(byte[] body, String contentType, String name, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        super(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
    }
}
