package com.univ.initializer.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * ip地址、mac地址
 * @author univ 2023/2/28 15:47
 */
public class NetWorkUtil {

	public static void main(String[] args) throws SocketException {
		System.out.println(getLocalIpAddress());

		System.out.println(getAllMacAddress());
	}
	/**
	 * 获取本机ip地址
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有网卡mac地址, 大写
	 *
	 * 处理时注意忽略大小写
	 *
	 * @return
	 * @throws SocketException
	 */
	public static List<String> getAllMacAddress() throws SocketException {
		List<String> macAddressList = new ArrayList<>(8);
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface ni = networkInterfaces.nextElement();
			byte[] hardwareAddress = ni.getHardwareAddress();
			if (hardwareAddress != null) {
				String[] hexadecimalFormat = new String[hardwareAddress.length];
				for (int i = 0; i < hardwareAddress.length; i++) {
					hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
				}
				macAddressList.add(String.join("-", hexadecimalFormat));
			}
		}
		return macAddressList;
	}
}
