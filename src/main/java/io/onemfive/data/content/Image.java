package io.onemfive.data.content;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class Image extends Binary {

    public Image() {
    }

    public Image(byte[] body, String contentType) {
        super(body, contentType);
    }

    public Image(byte[] body, String contentType, String name, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        super(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
    }
}
