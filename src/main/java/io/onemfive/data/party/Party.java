package io.onemfive.data.party;

import io.onemfive.data.Addressable;
import io.onemfive.data.DID;

import java.io.Serializable;

/**
 * An identity representing an individual or a group.
 *
 * @author objectorange
 */
public abstract class Party implements Addressable, Serializable {

    private DID identity;

    public Party(){}

    public Party(DID identity) {
        this.identity = identity;
    }

    @Override
    public String getAddress() {
        if(identity != null)
            return new String(identity.getMasterKeys().getPublic().getEncoded());
        else
            return null;
    }
}
