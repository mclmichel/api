package com.codirect.codiappapi.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;
import java.util.UUID;

public class CryptographyUtil {

	private static final String UNICODE_FORMAT = "UTF8";
	private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

	private KeySpec ks;
	private SecretKeyFactory skf;
	private Cipher cipher;
	private byte[] arrayBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	private SecretKey key;

	public CryptographyUtil(String salt) {
		try {
			myEncryptionKey = salt;
			myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
			arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
			ks = new DESedeKeySpec(arrayBytes);
			skf = SecretKeyFactory.getInstance(myEncryptionScheme);
			cipher = Cipher.getInstance(myEncryptionScheme);
			key = skf.generateSecret(ks);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String encrypt(String raw) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = raw.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return encryptedString;
	}

	public String decrypt(String encrypted) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encrypted);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return decryptedText;
	}

	public static String generateSalt() {
		return UUID.randomUUID().toString().substring(0, 28).toLowerCase().replace("-", "");
	}

	public static String generateValidationCode() {
		Integer part1 = new Double(Math.random() * 1000.0).intValue();
		Integer part2 = new Double(Math.random() * 1000.0).intValue();
		return String.format("%03d", part1) + "-" + String.format("%03d", part2);
	}
	
	public static Long convertValidationCode(String validationCode) {
		return new Long(validationCode.replace("-", ""));
	}
}
