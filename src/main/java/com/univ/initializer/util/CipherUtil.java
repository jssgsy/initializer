package com.univ.initializer.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author univ 2023/2/24 15:06
 */
public class CipherUtil {

	/**
	 * 十分重要！！！：只能对普通字符串作base64，不要把字节先转成string然后再编码，此时解码时得到的不是原字节。
	 * 所以需要提供对byte进行编码的重载方法，用来直接对byte进行编码；
	 * @param text
	 * @return
	 */
	public static String base64Encode(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
	}

	public static String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static String base64Decode(String base64Text) {
		return new String(base64DecodeToBytes(base64Text), StandardCharsets.UTF_8);
	}

	public static byte[] base64DecodeToBytes(String base64Text) {
		return Base64.getDecoder().decode(base64Text);
	}

	public static void main(String[] args)
			throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		String text = "[B@2cfb4a64";
		String encode = base64Encode(text);
		System.out.println(encode);
		System.out.println(base64Decode(encode));
	}

	public void rsaDetail()
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();

		String text = "fjieafe";
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encryptedBytes = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

		String byteStr = new String(encryptedBytes, StandardCharsets.UTF_8);

		System.out.println("==============");
		// 加密后的结果(byte)以base64编码，这样更易读
		String encoded = base64Encode(byteStr);


		Cipher encryptCipher1 = Cipher.getInstance("RSA");
		encryptCipher1.init(Cipher.DECRYPT_MODE, privateKey);
		// 特别注意，这里必须使用encryptedBytes，是对string解码，而不是对byte解码
		byte[] bytes = encryptCipher1.doFinal(encryptedBytes);
		String decoded = new String(bytes, StandardCharsets.UTF_8);
		System.out.println("RSA 解码后的byte[]: ---》 str: " + decoded);

	}

	public void generateRSAKey() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
		System.out.println("publicKey: " + publicKeyToBase64Str(publicKey));
		System.out.println("privateKey: " + privateKeyToBase64Str(privateKey));


	}
	public PublicKey base64StrToPublicKey(String base64Str)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(base64DecodeToBytes(base64Str));
		return keyFactory.generatePublic(publicKeySpec);
	}

	public String publicKeyToBase64Str(PublicKey publicKey) {
		return base64Encode(publicKey.getEncoded());
	}

	public PrivateKey base64StrToPrivateKey(String base64Str)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		// 这里不能使用X509EncodedKeySpec
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(base64DecodeToBytes(base64Str));
		return keyFactory.generatePrivate(privateKeySpec);
	}

	public String privateKeyToBase64Str(PrivateKey privateKey) {
		return base64Encode(privateKey.getEncoded());
	}

	public void rsaEncrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAunH7sBeHIUZi3FUEMTNnDgpr+PIOTb5dsGYqWI9WDqQ3NEU5xlThb/kimknB9GVX6ZwKPIVK8L4GoCbYk7zhyy+Z869VOcbLLJ8CTCAUXKodxekMX2bC9rzyysbbOq0WN7FYg6R/u+SKlljgbjEhr6/RKNjIVTUtniWefSHhc75lqM4ImUzEZiVuWNBfiliUJRmuBJyzaN34RS9p1Nxgk7NTAO6AqeeoR2WIpN1CbQMLdBX7Fjk/HiJWB9aoBCYJTVw1SNInnUXMxDSzAy8Mt+LiMd+VQn+bXNWbZ1716afUWhXqfkmftLfBbLDsgne6V+hQH0D2FvXkxZhiGTpyMwIDAQAB";
		String text = "hello, world";
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, base64StrToPublicKey(publicKey));
		byte[] encryptedBytes = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

		// 加密后的结果(byte)以base64编码，这样更易读
		String encoded = base64Encode(encryptedBytes);
		System.out.println(encoded);
	}

	public void rasDecrypt()
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6cfuwF4chRmLcVQQxM2cOCmv48g5Nvl2wZipYj1YOpDc0RTnGVOFv+SKaScH0ZVfpnAo8hUrwvgagJtiTvOHLL5nzr1U5xsssnwJMIBRcqh3F6QxfZsL2vPLKxts6rRY3sViDpH+75IqWWOBuMSGvr9Eo2MhVNS2eJZ59IeFzvmWozgiZTMRmJW5Y0F+KWJQlGa4EnLNo3fhFL2nU3GCTs1MA7oCp56hHZYik3UJtAwt0FfsWOT8eIlYH1qgEJglNXDVI0iedRczENLMDLwy34uIx35VCf5tc1ZtnXvXpp9RaFep+SZ+0t8FssOyCd7pX6FAfQPYW9eTFmGIZOnIzAgMBAAECggEAU46fyKRSuDPuPGaRkWdDCUTcbN42UNs+675tvfDHFZq87t0TbnI98kvKGTFzDg+ZHaYD5GmsU0YmfAHM0DE3VDNKIK70WAdiqOW+RtWdqoXHABpYpCzOtXuqp/wE5kAKE/2T+LNxV4iuH4FHW7sDKNR3vYGiDqXdguHDLLtRaegndBlPuoku4E2+hYH0voA4e1+s11bQhL10ANuZbSZwBEGZ9IvXF6CH8t6eXo/TnmBMiek+7Q/m6l586ttk9syND7BMEldqX+c/ft6dCcDJKoiJFDYsKA4tZKGbb6fyK8GRZXz3aEEXZ8NN/3JoPKb6alVdtiMiUhVKJL1SQeoPQQKBgQDjpP0NiZVNaDofWbLL1YhqnHUe2+wdWpZLa8iRKjgNeGIRs7ab0qhK4n23l08AvwEgf1RC+1zH/UaH732hbshLR7QCm1yTXgdG9kRxKi2YVcd8y+JoW6iZj53xX9AW0LynFFjmuLGkGTKbNx3HymVL4UZ4P8BZljIdbV9Cwk5ykwKBgQDRq0EY3yj/M9lPt8C47b8zbheeSjk6uU6iAy5u84/SrYzn9eTKCyM7ppXArIJXETS3QllhYJLrVCqqaJFeNpd8zmlQQ1qszmILwsftwHH/UtAZ61n9XvZEiJq9QUFVsOCIfpKe4ZyNk7ROKnwBR2fz/bULXP9z1PTvduiNDjWl4QKBgDMM5/bmOMZALnCjY/ZUL6CA77744M2AaB4H0NXFGwbiTVPc8oGlTzpj6n+GfiATtYP9Kf9PcXIj+XI4ofrW5jIDpXnEL5GYz5b5WUXriIgWDIOPEXoHvBqb23aq3vaO3w+0/27WUngTcobeaF5KpEbNAUmsOk2c4iY5RdipzH/jAoGBALR1uK7GjEuy/axeVLMWdgW3EP79lWpUyjgpktKOMdC77VZMzuGTWnzh8WyHThvQRFYi9jkY3PtNUveeztePFy2lnwElJ6sqazsCKAAbLkL6rQBSD/9Ct6XfCYgw3SH6/hadEmdLeeplIcBRUq/rHK7lQ+kG3xt8RGDDMH8Ppt8hAoGAaOitKMrjiPK3qd0yMRBQ5Y1isu2/OKFjtLRMvJZRaMSeTPWQ+kpWXFdyaTady/kCbbSfOnucbz6mJoxt017a6MqPpaxwIK1FQtnkeEX0fjTs8gwgdkfgNOHIlaWldG0RDuBSaEomhnd10XojfugI6QP0dDaEpZmKjLogkTgBGJY=";
		String secretMsg = "UhCzN4pbSpb1h5FtQDuAUQ1kAO1yZghhrMag4wosLmbq0wW7w0m42cvNobXtrLHVmu3xbTQtWKvnGZofbzyuiuhbdUcJWeQshASHgKjCLTR8WLofsvXeo/8X18Itt6EXlM4usg793+kwRwpeQz3rivA3xk9+V5SQYRrxnMxZA77dY3D/DWVKybT2K3HxDiiSqKebTIK6oeuIcsLutKydtC3Do96/FdS6rWwa0zgCvG+4P486cja/oUyx2e/7g3MEajcJdOjP91Qisd9PGGG6QJZicxZMOQrIZMlvnkH9SiVDVmPrNStFiMU9oeoHoURo1jjX/r0NvjfnJkEkFcKlBA==";
		Cipher decryptCipher = Cipher.getInstance("RSA");
//		System.out.println(privateKeyToBase64Str(base64StrToPrivateKey(privateKey)));;
		decryptCipher.init(Cipher.DECRYPT_MODE, base64StrToPrivateKey(privateKey));
		// 特别注意，这里必须使用encryptedBytes，是对string解码，而不是对byte解码
		byte[] bytes = decryptCipher.doFinal(base64DecodeToBytes(secretMsg));
		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	}



}
