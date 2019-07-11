package com.recse4cloud.pay.wx.account.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MTextUntils {

	public static String Bytes2HexString(byte[] b) {
		if (b == null) {
			return "";
		}
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static String Bytes2HexString(byte[] b, int len) {
		byte[] temp = new byte[len];
		System.arraycopy(b, 0, temp, 0, len);
		return Bytes2HexString(temp);
	}

	public static byte[] hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return bytes;
	}

	/**
	 * 16进制数转换为字节数组
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexString2Byte(String hexStr) {
		int len = hexStr.length() / 2;
		byte[] bs = new byte[len];
		int b;
		for (int i = 0; i < len; i++) {
			b = Integer.decode("#" + hexStr.substring(i * 2, 2 * (i + 1)));
			bs[i] = (byte) b;
		}
		return bs;
	}

	public static byte[] MD5(String s) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return md5.digest(s.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String Md5(String s) {
		return new String(MD5(s));
	}

	/**
	 * 代表16进制的字符串转换为真正的16进制
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] toHexByte(String s) {
		int len = s.length() / 2;
		byte[] bs = new byte[len];
		String child = null;
		for (int i = 0; i < len; i++) {
			child = s.substring(i * 2, i * 2 + 2);
			bs[i] = Integer.decode("0x" + child).byteValue();
		}
		return bs;
	}

	public static byte[] toHexByte2(String s) {
		int len = s.length() / 2;
		byte[] bs = new byte[len];
		String child = null;
		for (int i = 0; i < len; i++) {
			child = s.substring(i * 2, i * 2 + 2);
			bs[i] = (byte) Integer.parseInt(child);
		}
		return bs;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
