package com.wise.common.security.rsa;

public class RSATest {
    public static void main(String[] args) throws Exception
    {
        //生成密钥对
        KeyPair keyPair = KeyGenerator.generateKeyPair(KeyFormat.ASN);

        //获取公私钥，以asn格式的为例
        String publicKey = keyPair.getPublicKey();
        String privateKey = keyPair.getPrivateKey();
        System.out.println(publicKey);
        System.out.println();
        System.out.println(privateKey);
        //ASN
        KeyWorker publicWorker =  new KeyWorker("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAr3qZEw4bfQ8UUhDuxPsw90/uv7uNlce5qssaFD5H7xw2jBYjQIDc/0eVXkK34tI72IABf1mHKDOFuQypeIE/y4CMtAC3nednS+DOaaUuVk9V2Yij3PimOFyIsuCSbz9f/DskRdoeHBz+D8QRDIfhZ956KzTh3tsU8xAiE2d/6QIDAQAB", KeyFormat.ASN);
        KeyWorker privateWorker = new KeyWorker("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAICvepkTDht9DxRSEO7E+zD3T+6/u42Vx7mqyxoUPkfvHDaMFiNAgNz/R5VeQrfi0jvYgAF/WYcoM4W5DKl4gT/LgIy0ALed52dL4M5ppS5WT1XZiKPc+KY4XIiy4JJvP1/8OyRF2h4cHP4PxBEMh+Fn3norNOHe2xTzECITZ3/pAgMBAAECgYBEf6PtuXPn8/DD608yGc5v6SU2O/8Uxaaz0RMdhasFVWUaMAVUrHArswMiojM3eNhZnMu+gFBinmaWhsMTrp51WmjdvXO55aQ6hYTdr0pJVaQMDl0paaWqrh381DxO/A0iq7ktfSSRTejsvLKYx/cUaSERTW3iMRKBq22ZHEYxkQJBAL4diMXC9QDaBKZu9O52TwQIccMmJiVJhJBvHAHmtNScNgqDEK2cjXq7SfIeXB9hsHrwozDhiPkH3DDOqonNnHUCQQCtSBFSJIzdRurMIyLqVogkSq4HoChDZjb5wMVUhqERJO5G2Hd3yih1Vby73jda2/fCArbeYw3grP2VG6Iwb/clAkEAiQDHm5xO6vox965mG6Judr2PHc7UFnLQcTVgvY4AKmcYGqMw1avH2PY256AxSvwfLblUINmm169uYk3MX9ooMQJAF2kDHgveFA7+rbGZh/tEzVJhjJowllp61ucaQb8mh4BryJp6GW7wZFm+88qjw9yv7kAboJPVTiNQ5xIiqVSXGQJAVSG6QLcIkyYyK6qud9KlVlwjl/lmxLJV0VMvDNO66wEb3HnlqE9LqBzy/EV/dG5qmM5hw5UHvJBBD/7wgbnZjA==", KeyFormat.ASN);

        String ssssss = "你好，世界！";
        String s = publicWorker.encrypt(ssssss);
        System.out.println(s);

        String decryptStr = privateWorker.decrypt(s);

        System.out.println(decryptStr);
        System.out.println(decryptStr.length());
        //System.out.print(publicWorker.decrypt(privateWorker.encrypt("你好！中国")));

        /*//ASN
        KeyWorker privateWorker = new KeyWorker(privateKey, KeyFormat.ASN);
        KeyWorker publicWorker = new KeyWorker(publicKey, KeyFormat.ASN);

        System.out.print(privateWorker.decrypt(publicWorker.encrypt("你好！世界")));
        System.out.print(publicWorker.decrypt(privateWorker.encrypt("你好！中国")));

        //XML
        privateWorker = new KeyWorker(xmlKeyPair.getPrivateKey(), KeyFormat.XML);
        publicWorker = new KeyWorker(xmlKeyPair.getPublicKey(), KeyFormat.XML);

        System.out.print(privateWorker.decrypt(publicWorker.encrypt("你好！世界")));
        System.out.print(publicWorker.decrypt(privateWorker.encrypt("你好！中国")));

        //PEM
        privateWorker = new KeyWorker(pemKeyPair.getPrivateKey(), KeyFormat.PEM);
        publicWorker = new KeyWorker(pemKeyPair.getPublicKey(), KeyFormat.PEM);

        System.out.print(privateWorker.decrypt(publicWorker.encrypt("你好！世界")));
        System.out.print(publicWorker.decrypt(privateWorker.encrypt("你好！中国")));*/

    }
}
