package com.recse4cloud.pay.wx.account.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsSDKSignature {

	final static Logger log = LoggerFactory.getLogger(JsSDKSignature.class);

	/**
	 * 签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）,
	 * url（当前网页的URL，不包含#及其后面部分） 。对所有待签名参数按照字段名的ASCII
	 * 码从小到大排序（字典序）后，使用URL键值对的格式（即key1
	 * =value1&key2=value2…）拼接成字符串string1。这里需要注意的是所有参数名均为小写字符
	 * 。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
	 * 
	 * 即signature=sha1(string1)。 示例：
	 * 
	 * noncestr=Wm3WZYTPz0wzccnW jsapi_ticket=
	 * sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3
	 * -Sl-HhTdfl2fzFy1AOcHKP7qg timestamp=1414587457
	 * url=http://mp.weixin.qq.com?params=value
	 * 
	 * jsapi_ticket=
	 * sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3
	 * -Sl-HhTdfl2fzFy1AOcHKP7qg
	 * &noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url
	 * =http://mp.weixin.qq.com?params=value
	 * 
	 * @param params
	 * @return
	 */
	public static String signature(Map<String, Object> params) {

		ArrayList<String> list = new ArrayList<String>();

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			list.add(entry.getKey() + "=" + entry.getValue());
		}

		String[] temp = new String[list.size()];
		list.toArray(temp);
		Arrays.sort(temp, String.CASE_INSENSITIVE_ORDER);
		StringBuffer sb = new StringBuffer();

		for (String s : temp) {
			sb.append(s + "&");
		}
		sb.delete(sb.lastIndexOf("&"), sb.length());
		String sign = sb.toString();
		log.info("sign - " + sign);
		try {
			String result = sigatures(sign);
			log.info("sign -result- " + result);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查看当前应用签名信息
	 * 
	 * @param sign
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String sigatures(String sign)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("sha1");
		return MTextUntils.Bytes2HexString(md.digest(sign.getBytes("UTF-8")))
				.toLowerCase();
	}

	/**
	 * js config 签名
	 * @param timestamp 时间
	 * @param noncestr 随机串
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static String signature(String timestamp, String noncestr, String jsapi_ticket, String url) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("timestamp", timestamp);
		params.put("noncestr", noncestr);
		params.put("jsapi_ticket", jsapi_ticket);
		params.put("url", url);
		return signature(params);
	}

}
