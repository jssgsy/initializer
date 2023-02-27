package com.univ.initializer.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author univ 2023/2/27 09:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RSAKeyPO {

	/**
	 * 公钥
	 */
	private String publicKey;

	/**
	 * 私钥
	 */
	private String privateKey;

}
