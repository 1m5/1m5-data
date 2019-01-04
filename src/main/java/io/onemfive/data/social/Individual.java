package io.onemfive.data.social;

import io.onemfive.data.DID;

/**
 * An individual using the network.
 *
 * @author objectorange
 */
public class Individual extends Party {

    private DID did;

    public DID getDid() {
        return did;
    }

    public void setDid(DID did) {
        this.did = did;
    }
}
