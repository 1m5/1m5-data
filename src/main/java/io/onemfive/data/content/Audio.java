package io.onemfive.data.content;

public class Audio extends Binary {

    public Audio() {}

    public Audio(byte[] body, String contentType) {
        super(body, contentType);
    }

    public Audio(byte[] body, String contentType, String name, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        super(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
    }
}
