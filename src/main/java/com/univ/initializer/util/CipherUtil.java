package com.univ.initializer.util;

import com.univ.initializer.bo.RSAKeyPO;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * base64编码、解码；
 * RSA加密、解密；
 *
 * @author univ 2023/2/24 15:06
 */
public class CipherUtil {

	/**
	 * 将string形式进行base64编码。底层其实还是对此string对应的byte[]进行操作
	 *
	 * 十分重要！！！：base64编码、解码的入参都是byte[]；
	 *
	 * 如果输入是普通字符串，则先转成byte[]再编码、解码，
	 * 但要注意！如果输入是byte[]，此时不要把byte[]先转成string然后再编码，直接编码、解码即可。
	 * 	(编码后长度会变，但有些算法如RSA对输入的长度限制，融入base64时使用不当易出错)
	 *
	 * 所以需要提供对byte[]进行编码的重载方法，用来直接对byte[]进行编码；
	 *
	 * @param text
	 * @return
	 */
	public static String base64Encode(String text) {
		return base64Encode(text.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 将byte[]形式进行base64编码
	 * @param bytes
	 * @return
	 */
	public static String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * 将base64Text运用base64解码成String形式
	 * @param base64Text
	 * @return
	 */
	public static String base64Decode(String base64Text) {
		return new String(base64DecodeToBytes(base64Text), StandardCharsets.UTF_8);
	}

	/**
	 * 将base64Text运用base64解码成byte[]形式
	 * @param base64Text
	 * @return
	 */
	public static byte[] base64DecodeToBytes(String base64Text) {
		return Base64.getDecoder().decode(base64Text);
	}

	public static void main(String[] args) {
		byte[] arr = {10, 11, 12, 13};
		System.out.println(Arrays.toString(arr));
		String str = new String(arr);
		System.out.println(str);
		String encode1 = base64Encode(str);
		System.out.println(encode1);

		String s = base64Decode(encode1);
		System.out.println(s);
		System.out.println(Arrays.toString(s.getBytes()));

		// base64编码
		/*String text = "[B@2cfb4a64";
		String encode = base64Encode(text);
		System.out.println(encode);
		System.out.println(base64Decode(encode));*/

		// RSA加解密
		/*RSAKeyPO rsaKeyPO = generateRSAKey();
		String encrypted = rsaEncrypt(rsaKeyPO.getPublicKey(), "fjeia都是没用工二月份");
		System.out.println(encrypted);
		String decrypted = rsaDecrypt(rsaKeyPO.getPrivateKey(), encrypted);
		System.out.println(decrypted);*/
	}

	/**
	 * RSA完整流程的示例
	 *
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static void rsaDetail()
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();

		String text = "fjieafe";
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 重点1：加密的对象的是byte[]，解密的结果也是byte[]
		byte[] encryptedBytes = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

		// 加密后的结果(byte)以base64编码，这样更易读
		String secretMsgWithBase64 = base64Encode(encryptedBytes);
		System.out.println("RSA加密且base64编码后：" + secretMsgWithBase64);

		// 解密
		Cipher encryptCipher1 = Cipher.getInstance("RSA");
		encryptCipher1.init(Cipher.DECRYPT_MODE, privateKey);
		// 重点2：解密的对象的是byte[]，加密的结果也是byte[]
		byte[] bytes = encryptCipher1.doFinal(encryptedBytes);
		String decoded = new String(bytes, StandardCharsets.UTF_8);
		System.out.println("RSA解码后的原文为: " + decoded);
	}

	/**
	 * 创建RSA公钥与私钥
	 *
	 * @throws NoSuchAlgorithmException
	 */
	public static RSAKeyPO generateRSAKey() {
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("生成RSA密钥失败");
			e.printStackTrace();
		}
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
		return new RSAKeyPO(publicKeyToBase64Str(publicKey), privateKeyToBase64Str(privateKey));
	}

	public static PublicKey base64StrToPublicKey(String base64Str)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(base64DecodeToBytes(base64Str));
		return keyFactory.generatePublic(publicKeySpec);
	}

	public static String publicKeyToBase64Str(PublicKey publicKey) {
		return base64Encode(publicKey.getEncoded());
	}

	public static PrivateKey base64StrToPrivateKey(String base64Str)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		// 这里不能使用X509EncodedKeySpec
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(base64DecodeToBytes(base64Str));
		return keyFactory.generatePrivate(privateKeySpec);
	}

	public static String privateKeyToBase64Str(PrivateKey privateKey) {
		return base64Encode(privateKey.getEncoded());
	}

	/**
	 * RSA加密
	 *
	 * 注意：RSA加密以及解密的对象都是byte[]，在对结果应用base64时注意使用byte[]形式而不是字符串形式
	 * @return
	 */
	public static String rsaEncrypt(String publicKey, String text)  {
		Cipher encryptCipher = null;
		try {
			encryptCipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		try {
			encryptCipher.init(Cipher.ENCRYPT_MODE, base64StrToPublicKey(publicKey));
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		byte[] encryptedBytes = new byte[0];
		try {
			encryptedBytes = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		// 加密后的结果(byte)以base64编码，这样更易读
		return base64Encode(encryptedBytes);
	}

	/**
	 * RSA解密
	 * 注意：RSA加密以及解密的对象都是byte[]，在对结果应用base64时注意使用byte[]形式而不是字符串形式
	 */
	public static String rsaDecrypt(String privateKey, String secretMsg) {
		Cipher decryptCipher = null;
		try {
			decryptCipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		try {
			decryptCipher.init(Cipher.DECRYPT_MODE, base64StrToPrivateKey(privateKey));
		} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 特别注意，这里base64解码必须使用byte[]的返回值形式
		byte[] bytes = new byte[0];
		try {
			bytes = decryptCipher.doFinal(base64DecodeToBytes(secretMsg));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return new String(bytes, StandardCharsets.UTF_8);
	}

}
