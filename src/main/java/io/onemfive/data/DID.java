package io.onemfive.data;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.Principal;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
@Indices({
        @Index(value = "alias", type = IndexType.Fulltext)
})
public class DID implements Persistable, Serializable {

    public enum Status {UNREGISTERED, ACTIVE, SUSPENDED}
    public enum Provider {I2P}

    @Id
    private Long id;
    /**
     * The current in-use alias
     */
    private String alias;
    private String passphrase;
    private Status status = Status.UNREGISTERED;
    private Provider provider = Provider.I2P; // Default
    private boolean verified = false;
    private boolean authenticated = false;
    private KeyPair masterKeys;
    private List<String> aliases = new ArrayList<>();
    // Identities = Signing Keys: Long-Lived
    private Map<Provider,Map<String,KeyPair>> providerIdentities = new HashMap<>();
    // Encryption Keys: Short-Lived
    private Map<Provider,Map<String,KeyPair>> providerEncryptionKeys = new HashMap<>();
    // Encoded Key Strings (Base64 PublicKeyPair for I2P)
    private Map<Provider,Map<String,String>> providerEncodedKeys = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public KeyPair getMasterKeys() {
        return masterKeys;
    }

    public void setMasterKeys(KeyPair masterKeys) {
        this.masterKeys = masterKeys;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addAlias(String alias) {
        aliases.add(alias);
    }

    public void setActiveAliasAndProvider(String activeAlias, Provider activeProvider) {
        this.alias = activeAlias;
        this.provider = activeProvider;
    }

    /**
     * Removes active alias from the aliases list without removing keys.
     * This prevents spreading usage of the key while supporting
     * applications who are still using the key, e.g. to secure long-lived data.
     * @return true if successful otherwise false
     */
    public Boolean removeAlias() {
        return removeAlias(alias, false);
    }

    /**
     * Removes specified alias from the aliases list without removing keys.
     * This prevents spreading usage of the key while supporting
     * applications who are still using the key, e.g. to secure long-lived data.
     * @param alias A username of the end user
     * @return true if successful otherwise false
     */
    public Boolean removeAlias(String alias) {
        return removeAlias(alias, false);
    }

    public Boolean removeAlias(Boolean withKeys) {
        return removeAlias(alias, withKeys);
    }
    /**
     * Same as removeAlias except it supports explicit removal of keys with alias.
     * WARNING: removal of keys will prevent further retrieval of them by other applications.
     * @param alias A username of the end user
     * @param withKeys If keys associated with alias should also be removed
     * @return true if successful otherwise false
     */
    public Boolean removeAlias(String alias, Boolean withKeys) {
        return removeAlias(alias, withKeys, withKeys, withKeys);
    }

    public Boolean removeAlias(Boolean withEncryptionKeys, Boolean withIdentityKeys, Boolean withEncodedKey) {
        return removeAlias(alias, withEncryptionKeys, withIdentityKeys, withEncodedKey);
    }

    public Boolean removeAlias(String alias, Boolean withEncryptionKeys, Boolean withIdentityKeys, Boolean withEncodedKey) {
        if(withEncodedKey) {
            removeEncodedKey(Provider.I2P, alias);
        }
        if(withEncryptionKeys) {
            removeEncryptionKeys(Provider.I2P, alias);
        }
        if(withIdentityKeys) {
            removeIdentity(Provider.I2P, alias);
        }
        aliases.remove(alias);
        return true;
    }

    public Boolean aliasSupportsProvider(Provider provider, String alias) {
        return getIdentity(provider, alias) != null
                && getEncryptionKeys(provider, alias) != null
                && getEncodedKey(provider, alias) != null;
    }

    public Boolean addIdentity(KeyPair keyPair) {
        return addIdentity(provider, alias, keyPair);
    }

    public Boolean addIdentity(Provider provider, KeyPair keyPair) {
        return addIdentity(provider, alias, keyPair);
    }

    public Boolean addIdentity(Provider provider, String alias, KeyPair keyPair) {
        Map<String,KeyPair> identities = providerIdentities.get(provider);
        if(identities == null) {
            identities = new HashMap<>();
            providerIdentities.put(provider, identities);
        }
        identities.put(alias,keyPair);
        return true;
    }

    public KeyPair getIdentity() {
        return getIdentity(provider, alias);
    }

    public KeyPair getIdentity(Provider provider) {
        return getIdentity(provider, alias);
    }

    public KeyPair getIdentity(Provider provider, String alias) {
        Map<String,KeyPair> identities = providerIdentities.get(provider);
        return identities == null ? null : identities.get(alias);
    }

    public Boolean removeIdentity() {
        return removeIdentity(provider, alias);
    }

    public Boolean removeIdentity(Provider provider) {
        return removeIdentity(provider, alias);
    }

    public Boolean removeIdentity(Provider provider, String alias) {
        Map<String,KeyPair> identities = providerIdentities.get(provider);
        if(identities != null) {
            identities.remove(alias);
        }
        return true;
    }

    public Boolean addEncryptionKeys(KeyPair keyPair) {
        return addEncryptionKeys(provider, alias, keyPair);
    }

    public Boolean addEncryptionKeys(Provider provider, KeyPair keyPair) {
        return addEncryptionKeys(provider, alias, keyPair);
    }

    public Boolean addEncryptionKeys(Provider provider, String alias, KeyPair keyPair) {
        Map<String,KeyPair> encryptionKeys = providerEncryptionKeys.get(provider);
        if(encryptionKeys == null) {
            encryptionKeys = new HashMap<>();
            providerEncryptionKeys.put(provider, encryptionKeys);
        }
        encryptionKeys.put(alias, keyPair);
        return true;
    }

    public KeyPair getEncryptionKeys() {
        return getEncryptionKeys(provider, alias);
    }

    public KeyPair getEncryptionKeys(Provider provider) {
        return getEncryptionKeys(provider, alias);
    }

    public KeyPair getEncryptionKeys(Provider provider, String alias) {
        Map<String,KeyPair> encryptionKeys = providerEncryptionKeys.get(provider);
        return encryptionKeys == null ? null : encryptionKeys.get(alias);
    }

    public Boolean removeEncrptionKeys() {
        return removeEncryptionKeys(provider, alias);
    }

    public Boolean removeEncryptionKeys(Provider provider) {
        return removeEncryptionKeys(provider, alias);
    }

    public Boolean removeEncryptionKeys(Provider provider, String alias) {
        Map<String,KeyPair> encryptionKeys = providerEncryptionKeys.get(provider);
        if(encryptionKeys != null) {
            encryptionKeys.remove(alias);
        }
        return true;
    }

    public Boolean addEncodedKey(String encodedKey) {
        return addEncodedKey(provider, alias, encodedKey);
    }

    public Boolean addEncodedKey(Provider provider, String encodedKey) {
        return addEncodedKey(provider, alias, encodedKey);
    }

    public Boolean addEncodedKey(Provider provider, String alias, String encodedKey) {
        Map<String,String> encodedKeys = providerEncodedKeys.get(provider);
        if(encodedKeys == null) {
            encodedKeys = new HashMap<>();
            providerEncodedKeys.put(provider, encodedKeys);
        }
        encodedKeys.put(alias, encodedKey);
        return true;
    }

    public String getEncodedKey() {
        return getEncodedKey(provider, alias);
    }

    public String getEncodedKey(Provider provider) {
        return getEncodedKey(provider, alias);
    }

    public String getEncodedKey(Provider provider, String alias) {
        Map<String,String> encodedKeys = providerEncodedKeys.get(provider);
        return encodedKeys == null ? null : encodedKeys.get(alias);
    }

    public Boolean removeEncodedKey() {
        return removeEncodedKey(provider, alias);
    }

    public Boolean removeEncodedKey(Provider provider) {
        return removeEncodedKey(provider, alias);
    }

    public Boolean removeEncodedKey(Provider provider, String alias) {
        Map<String,String> encodedKeys = providerEncodedKeys.get(provider);
        if(encodedKeys != null) {
            encodedKeys.remove(alias);
        }
        return true;
    }

    public Boolean addFullNewPublicPrivateKeySet(KeyPair publicKeyPair, KeyPair privateKeyPair, String encodedKey) {
        return addFullNewPublicPrivateKeySet(provider, alias, publicKeyPair, privateKeyPair, encodedKey);
    }

    public Boolean addFullNewPublicPrivateKeySet(Provider provider, KeyPair publicKeyPair, KeyPair privateKeyPair, String encodedKey) {
        return addFullNewPublicPrivateKeySet(provider, alias, publicKeyPair, privateKeyPair, encodedKey);
    }

    public Boolean addFullNewPublicPrivateKeySet(Provider provider, String alias, KeyPair publicKeyPair, KeyPair privateKeyPair, String encodedKey) {
        if(!aliasPresent(alias))
            aliases.add(alias);
        addEncryptionKeys(provider, alias, new KeyPair(publicKeyPair.getPublic(), publicKeyPair.getPrivate()));
        addIdentity(provider, alias, new KeyPair(privateKeyPair.getPublic(), privateKeyPair.getPrivate()));
        addEncodedKey(provider, alias, encodedKey);
        return true;
    }

    public Boolean addFullNewEncryptionIdentityKeySet(KeyPair encryptionKeyPair, KeyPair identityKeyPair, String encodedKey) {
        return addFullNewEncryptionIdentityKeySet(provider, alias, encryptionKeyPair, identityKeyPair, encodedKey);
    }

    public Boolean addFullNewEncryptionIdentityKeySet(Provider provider, KeyPair encryptionKeyPair, KeyPair identityKeyPair, String encodedKey) {
        return addFullNewEncryptionIdentityKeySet(provider, alias, encryptionKeyPair, identityKeyPair, encodedKey);
    }

    public Boolean addFullNewEncryptionIdentityKeySet(Provider provider, String alias, KeyPair encryptionKeyPair, KeyPair identityKeyPair, String encodedKey) {
        if(!aliasPresent(alias))
            aliases.add(alias);
        addEncryptionKeys(provider, alias, encryptionKeyPair);
        addIdentity(provider, alias, identityKeyPair);
        addEncodedKey(provider, alias, encodedKey);
        return true;
    }

    private Boolean aliasPresent(String alias) {
        for(String a : aliases) {
            if(a.equals(alias))
                return true;
        }
        return false;
    }
}
