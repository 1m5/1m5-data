package io.onemfive.data.social;

import io.onemfive.data.DID;

/**
 * An identity representing an individual or a group.
 *
 * @author objectorange
 */
public abstract class Party extends DID {

    private Profile profile;

    public Party(){}

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
