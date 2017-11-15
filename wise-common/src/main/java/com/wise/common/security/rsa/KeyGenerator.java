package com.wise.common.security.rsa;

import java.security.NoSuchAlgorithmException;

public class KeyGenerator {

    static public KeyPair generateKeyPair(KeyFormat format)
            throws NoSuchAlgorithmException {
        KeyPair keyPair = new KeyPair(format);

        return keyPair;
    }

    static public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(KeyFormat.ASN);
    }
}
