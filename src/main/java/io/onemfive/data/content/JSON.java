package io.onemfive.data.content;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class JSON extends Text {

    public JSON() {
    }

    public JSON(byte[] body) {
        super.body = body;
        contentType = "application/json";
    }

    public JSON(byte[] body, String contentType) {
        super(body, contentType);
    }

    public JSON(byte[] body, String contentType, String name, boolean generateHash, boolean generateFingerprint) {
        super(body, contentType, name, generateHash, generateFingerprint);
    }
}
