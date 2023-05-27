package org.elsfs.security.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public final class SecurityReadKeyUtils {

    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";

    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";


    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";

    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";

    public static final String PEM_FILE_SUFFIX = ".pem";

    public static final String CER_FILE_SUFFIX = ".cer";

    /*-----------------ȡpemʽ RSA PublicKey------------------------------------*/
    private static String filterStrKey(String str) {
        return str.replace(BEGIN_PUBLIC_KEY, "")
            .replace(BEGIN_PRIVATE_KEY, "")
            .replaceAll(System.lineSeparator(), "")
            .replace(END_PUBLIC_KEY, "")
            .replace(END_PRIVATE_KEY, "");
    }

    /**
     * pem
     * @param pemFile
     * @throws IOException
     */
    private static void checkFile(File pemFile, String suffix) throws IOException {
        String fileName = pemFile.getName();
        if (!pemFile.isFile())
            throw new IOException(fileName + "not a file!");
        if (!pemFile.canRead())
            throw new IOException(fileName + "file does not have read permission");
        int lastIndexOfPem = fileName.lastIndexOf(suffix);
        if (lastIndexOfPem == -1 ||
        // �м��� suffix �����
                fileName.length() != suffix.length() + lastIndexOfPem)
            throw new IOException(fileName + " file suffix not is " + suffix);
    }

    /**
     * ���� *.pem�ļ�·����ȡ��Կ
     * @param publicKeFilePath *��pem�ļ�·��
     * @return ��Կ
     */
    public static PublicKey readFileSuffixPemPublicKey(String publicKeFilePath)
            throws IOException, GeneralSecurityException {
        return readFileSuffixPemPublicKey(new File(publicKeFilePath));
    }

    /**
     * ���� *.pem�ļ�·����ȡ��Կ
     * @param pemFile *��pem�ļ��ļ�
     * @return ��Կ
     */
    public static PublicKey readFileSuffixPemPublicKey(File pemFile) throws IOException, GeneralSecurityException {
        checkFile(pemFile, PEM_FILE_SUFFIX);
        return readFileSuffixPemPublicKey(new FileInputStream(pemFile));
    }

    /**
     * �����ַ�����ȡ��Կ
     * @param publicKeyStr
     * @return
     */
    public static PublicKey readStrPublicKey(String publicKeyStr) throws GeneralSecurityException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(
                Base64.getMimeDecoder().decode(filterStrKey(publicKeyStr).getBytes(StandardCharsets.UTF_8)));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * �������������ع��� pem ��ʽ
     * @param input
     */
    public static PublicKey readFileSuffixPemPublicKey(InputStream input) throws IOException, GeneralSecurityException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int n;
        byte[] buffer = new byte[1024 * 4];
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return readStrPublicKey(output.toString());
    }
    /*-----------------��ȡ cer �ļ���ʽ RSA PublicKey------------------------------------*/

    /**
     * * ���� *.cer �ļ�·����ȡ��Կ
     * @param keyStorePath
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static PublicKey readFileSuffixCerPublicKey(String keyStorePath)
            throws IOException, GeneralSecurityException {
        return readFileSuffixCerPublicKey(new File(keyStorePath));
    }

    /**
     * ���� *.cer �ļ���ȡ��Կ
     * @param keyStoreFile *.cer �ļ�
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static PublicKey readFileSuffixCerPublicKey(File keyStoreFile) throws IOException, GeneralSecurityException {
        checkFile(keyStoreFile, CER_FILE_SUFFIX);
        return readFileSuffixCerPublicKey(new FileInputStream(keyStoreFile));
    }

    /**
     * ���� *.cer ��
     * @param keyStoreInputStream *.cer �ļ���
     */
    public static PublicKey readFileSuffixCerPublicKey(InputStream keyStoreInputStream)
            throws IOException, GeneralSecurityException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(keyStoreInputStream);
        cert.checkValidity();
        return cert.getPublicKey();
    }

    /*---------------------��ȡ RSA PrivateKey-----------------------------------*/

    /**
     * ���� ˽Կ�ļ�·����ȡ PrivateKey �ļ���ʽΪ .pem
     * @param privateKeyFilePath
     */
    public static PrivateKey readFileSuffixPemPrivateKey(String privateKeyFilePath)
            throws IOException, GeneralSecurityException {
        return readFileSuffixPemPrivateKey(new File(privateKeyFilePath));
    }

    /**
     * ���� ˽Կ�ļ���ȡ PrivateKey �ļ���ʽΪ .pem
     * @param pemFile
     * @return
     */
    public static PrivateKey readFileSuffixPemPrivateKey(File pemFile) throws IOException, GeneralSecurityException {
        checkFile(pemFile, PEM_FILE_SUFFIX);
        return readFileSuffixPemPrivateKey(new FileInputStream(pemFile));
    }

    /**
     * ��������ȡ˽Կ
     * @param input
     */
    public static PrivateKey readFileSuffixPemPrivateKey(InputStream input)
            throws IOException, GeneralSecurityException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int n;
        byte[] buffer = new byte[1024 * 4];
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        return readStrPrivateKey(output.toString());
    }

    /**
     * �����ַ�����ȡ��Կ
     * @param privateKeyStr
     * @return
     */
    public static PrivateKey readStrPrivateKey(String privateKeyStr) throws GeneralSecurityException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(filterStrKey(privateKeyStr).getBytes(StandardCharsets.UTF_8)));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /*--------------------p12 �ļ�֤��-----------------*/
    /**
     * ���� ˽Կ�ļ�·����ȡ PrivateKey �ļ���ʽΪ .p12
     * @param privateKeyFilePath p12 �ļ�·��
     * @param password ����
     * @param alias ����
     */
    public static PrivateKey readFileSuffixP12PrivateKey(String privateKeyFilePath, String password, String alias)
            throws IOException, GeneralSecurityException {
        File file = new File(privateKeyFilePath);
        InputStream stream = new FileInputStream(file);
        KeyStore store = KeyStore.getInstance("PKCS12");
        store.load(stream, password.toCharArray());
        return (PrivateKey) store.getKey(alias, password.toCharArray());
    }

    /**
     * @param key
     * @return
     */
    public static String getKeyString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

}
