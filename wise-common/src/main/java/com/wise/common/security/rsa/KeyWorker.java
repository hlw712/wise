package com.wise.common.security.rsa;

import org.xml.sax.SAXException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyWorker {

    private String _key;
    private KeyFormat _format;
    private Cipher _decryptProvider;
    private Cipher _encryptProvider;

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public KeyWorker(String key) {
        this(key, KeyFormat.ASN);
    }

    public KeyWorker(String key, KeyFormat format) {
        this._key = key;
        this._format = format;
    }


    /**
     * 加密
     *
     * @param data
     * @return
     */
    public String encrypt(String data) throws IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeySpecException, IOException, SAXException, ParserConfigurationException {
        this._makesureEncryptProvider();

        byte[] bytes = data.getBytes("UTF-8");

        int inputLen = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = this._encryptProvider.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = this._encryptProvider.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeToString(encryptedData);
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public String decrypt(String data) throws IOException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeySpecException, SAXException, ParserConfigurationException {
        this._makesureDecryptProvider();

        byte[] bytes = Base64.decode(data);

        int inputLen = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = this._decryptProvider.doFinal(bytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = this._decryptProvider.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "UTF-8");
    }

    private void _makesureDecryptProvider() throws NoSuchAlgorithmException,
            NoSuchPaddingException, IOException, InvalidKeySpecException,
            InvalidKeyException, SAXException, ParserConfigurationException {
        if (this._decryptProvider != null)
            return;

        Cipher deCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        switch (this._format) {
            case PEM: {
                this._key = this._key.replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\r\n", "");
            }
            case ASN:
            default: {
                Boolean isPrivate = this._key.length() > 500;
                if (isPrivate) {
                    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
                            Base64.decode(this._key));

                    KeyFactory factory = KeyFactory.getInstance("RSA");
                    RSAPrivateKey privateKey = (RSAPrivateKey) factory
                            .generatePrivate(spec);
                    deCipher.init(Cipher.DECRYPT_MODE, privateKey);
                } else {
                    X509EncodedKeySpec spec = new X509EncodedKeySpec(
                            Base64.decode(this._key));

                    KeyFactory factory = KeyFactory.getInstance("RSA");
                    RSAPublicKey publicKey = (RSAPublicKey) factory
                            .generatePublic(spec);
                    deCipher.init(Cipher.DECRYPT_MODE, publicKey);
                }
            }
            break;
        }

        this._decryptProvider = deCipher;
    }

    private void _makesureEncryptProvider() throws NoSuchAlgorithmException,
            NoSuchPaddingException, IOException, InvalidKeySpecException,
            InvalidKeyException, SAXException, ParserConfigurationException {
        if (this._encryptProvider != null)
            return;

        Cipher enCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        switch (this._format) {
            case PEM: {
                this._key = this._key.replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\r\n", "");
            }
            case ASN:
            default: {
                Boolean isPrivate = this._key.length() > 500;
                if (isPrivate) {
                    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
                            Base64.decode(this._key));

                    KeyFactory factory = KeyFactory.getInstance("RSA");
                    RSAPrivateKey privateKey = (RSAPrivateKey) factory
                            .generatePrivate(spec);
                    enCipher.init(Cipher.ENCRYPT_MODE, privateKey);

                } else {
                    X509EncodedKeySpec spec = new X509EncodedKeySpec(
                            Base64.decode(this._key));

                    KeyFactory factory = KeyFactory.getInstance("RSA");
                    RSAPublicKey publicKey = (RSAPublicKey) factory
                            .generatePublic(spec);
                    enCipher.init(Cipher.ENCRYPT_MODE, publicKey);
                }
            }
            break;
        }

        this._encryptProvider = enCipher;
    }

}
