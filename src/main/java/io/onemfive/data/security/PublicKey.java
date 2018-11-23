package io.onemfive.data.security;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class PublicKey {

    private String algorithm;
    private String format;
    private byte[] encoded;
    private String encodedInBase64;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public byte[] getEncoded() {
        return encoded;
    }

    public void setEncoded(byte[] encoded) {
        this.encoded = encoded;
    }

    public String getEncodedInBase64() {
        return encodedInBase64;
    }

    public void setEncodedInBase64(String encodedInBase64) {
        this.encodedInBase64 = encodedInBase64;
    }
}
