package com.wise.common.security.rsa;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyPair {
    private java.security.KeyPair _rsa;
    private KeyFormat _format;
    private String _private;
    private String _public;

    /**
     * 指定key的大小
     */
    private static int KEY_SIZE = 1024;

    public KeyFormat getFormat() {
        return this._format;
    }

    KeyPair(KeyFormat format) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(KEY_SIZE);
        this._rsa = kpg.genKeyPair();
        this._format = format;
    }

    private KeyPair(java.security.KeyPair rsa, KeyFormat format) {
        this._rsa = rsa;
        this._format = format;
    }

    public String getPrivateKey() {
        if (this._private == null) {
            switch (this._format) {
                case ASN:
                    this._private = this._toASNPrivateKey();
                    break;
                case PEM:
                    this._private = this._toPEMPrivateKey();
                    break;
            }
        }
        return this._private;
    }

    public String getPublicKey() {
        if (this._public == null) {
            switch (this._format) {
                case ASN:
                    this._public = this._toASNPublicKey();
                    break;
                case PEM:
                    this._public = this._toPEMPublicKey();
                    break;
            }
        }
        return this._public;
    }

    public KeyPair toASNKeyPair() {
        return new KeyPair(this._rsa, KeyFormat.ASN);
    }

    public KeyPair toPEMKeyPair() {
        return new KeyPair(this._rsa, KeyFormat.PEM);
    }


    private String _toASNPublicKey() {
        return Base64.encodeToString(this._rsa.getPublic().getEncoded());
    }

    private String _toASNPrivateKey() {
        return Base64.encodeToString(this._rsa.getPrivate().getEncoded());
    }

    private String _toPEMPublicKey() {
        String publicKey = this._toASNPublicKey();
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN PUBLIC KEY-----\r\n");
        int i = 0;
        while (i + 64 < publicKey.length()) {
            sb.append(publicKey.substring(i, i + 64) + "\r\n");
            i += 64;
        }
        sb.append(publicKey.substring(i, publicKey.length()) + "\r\n");
        sb.append("-----END PUBLIC KEY-----\r\n");

        return sb.toString();
    }

    private String _toPEMPrivateKey() {
        String privateKey = this._toASNPrivateKey();
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN PRIVATE KEY-----\r\n");
        int i = 0;
        while (i + 64 < privateKey.length()) {
            sb.append(privateKey.substring(i, i + 64) + "\r\n");
            i += 64;
        }
        sb.append(privateKey.substring(i, privateKey.length()) + "\r\n");
        sb.append("-----END PRIVATE KEY-----\r\n");

        return sb.toString();
    }
}
