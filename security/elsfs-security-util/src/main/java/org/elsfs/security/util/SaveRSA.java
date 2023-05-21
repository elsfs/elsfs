package org.elsfs.security.util;

import java.io.*;
import java.security.*;
import java.util.Base64;

/**
 * @author zeng
 * @since 0.0.1
 */
public class SaveRSA {

    /**
     * ���� KeyPair
     * @param keyPair
     * @param directory
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static void save(KeyPair keyPair, File directory) throws NoSuchAlgorithmException, IOException {
        if (!directory.isDirectory()) {
            throw new IOException(" File is no directory");
        }
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicKeyStr = getKeyAsString(publicKey);
        String privateKeyStr = getKeyAsString(privateKey);
        // ����Կ���浽�ļ���
        OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream(directory.getPath() + File.separator + "publicKey.key"));
        osw.write(publicKeyStr, 0, publicKeyStr.length());
        osw.flush();
        osw.close();

        OutputStreamWriter osw2 = new OutputStreamWriter(
                new FileOutputStream(directory.getPath() + File.separator + "privateKey.key"));
        osw2.write(privateKeyStr, 0, privateKeyStr.length());
        osw2.flush();
        osw2.close();
    }

    /**
     * @param key
     * @param file �ļ���׺ .key
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static void save(Key key, File file) throws NoSuchAlgorithmException, IOException {
        String stringKey = getKeyAsString(key);
        // ����Կ���浽�ļ���
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
        osw.write(stringKey, 0, stringKey.length());
        osw.flush();
        osw.close();
    }

    // ��Key����ת��ΪString����
    public static String getKeyAsString(Key key) {
        byte[] keyBytes = key.getEncoded();
        return encodeBASE64(keyBytes);
    }

    public static String encodeBASE64(byte[] keyBytes) {
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        File file = new File("F:\\OpenSource\\elsfs\\webapp\\admin\\manage-admin-webapp\\src\\main\\resources");
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        save(keyPair, file);
    }

}