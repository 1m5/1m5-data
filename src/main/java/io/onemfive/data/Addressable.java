package io.onemfive.data;

/**
 * Indicates object is addressable on network by its public key.
 *
 * @author objectorange
 */
public interface Addressable {
    String getShortAddress();
    String getFullAddress();
}
