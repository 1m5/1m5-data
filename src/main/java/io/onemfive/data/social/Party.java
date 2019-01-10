package io.onemfive.data.social;

/**
 * An identity representing an individual or a group.
 *
 * @author objectorange
 */
public abstract class Party {

    private Profile profile;

    public Party(){}

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
