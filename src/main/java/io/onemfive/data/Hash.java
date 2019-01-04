package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class Hash extends Data {

    public enum Algorithm {

        SHA1("SHA-1"),
        SHA256("SHA-256"),
        SHA512("SHA-512"),
        PBKDF2WithHmacSHA1("PBKDF2WithHmacSHA1");

        private String name;

        Algorithm(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private String hash;
    private Algorithm algorithm;

    public Hash(String hash, Algorithm algorithm) {
        this.hash = hash;
        this.algorithm = algorithm;
    }

    public String getHash() {
        return hash;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public int length() {
        return 0;
    }
}
