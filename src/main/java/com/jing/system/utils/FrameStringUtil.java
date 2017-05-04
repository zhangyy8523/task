package com.jing.system.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类<br>
 * 示例：
 * 		String str = (char)1+"dasda"+(char)2+"sdasd"+(char)1+"asdasdas"+(char)1+(char)1+(char)1;
		System.out.println(str);
		System.out.println(trim(str, (char)3));
		System.out.println("isEmpty: " + isEmpty(null));
		System.out.println("isNotEmpty: " + isNotEmpty("s"));
		System.out.println("isEqualArr: " + isEqualArr(2, 20521, 20513, 10024));
		System.out.println("isNotEqualArr: " + isNotEqualArr(null, 1, 2, 3));
		System.out.println("getStrsRandomStr: " + getStrsRandomStr("哈哈;嗯嗯;", ";"));
		
		String urlStr = "这是一个url链接http://www-test.company.com/view/1_2.html?a=%B8&f=%E4+%D3#td http://www.suyunyou.com/aid15.html需要转化成可点击";
		System.out.println(parseUrl(urlStr));
		System.out.println("包含http链接：" + existUrl("爱，http://sdfdf"));
		
 * @author yuejing
 * @date 2016年4月30日 下午7:15:59
 * @version V1.0.0
 */
public class FrameStringUtil {

	//链接正则表达式
	private static final String REGEX_URL = "(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";
	
	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null)  = true</li>
	 * <li>SysUtils.isEmpty("")    = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArr(Object value, Object ...equalArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值[不区分大小写]
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualsIcArr(Object value, Object ...equalArr) {
		if (value == null || "".equalsIgnoreCase(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArrSin(Object value, Object[] equalArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否不等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isNotEqualArr(Object value, Object ...notEqualArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return true;
		}
		for (Object object : notEqualArr) {
			if(value.toString().equals(object.toString())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断一个字符串是否不等于一个数组中的一个值[不区分大小写]
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isNotEqualIcArr(Object value, Object ...notEqualArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return true;
		}
		for (Object object : notEqualArr) {
			if(value.toString().equalsIgnoreCase(object.toString())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串是否不为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null)  = false</li>
	 * <li>SysUtils.isEmpty("")    = false</li>
	 * <li>SysUtils.isEmpty("   ") = false</li>
	 * <li>SysUtils.isEmpty("abc") = true</li>
	 * </ul>
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isNotEmpty(String value) {
		if (value != null && !"".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 检查对象是否为数字型字符串。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		String str = obj.toString();
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		return join(array, separator, 0, array.length);
	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = endIndex - startIndex;
		if (bufSize <= 0) {
			return "";
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
		StringBuilder buf = new StringBuilder(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * 字符串以指定字符分隔,然后随机获取一个字符串
	 * @param string
	 * @param split
	 * @return
	 */
	public static String getStrsRandomStr(String string, String split) {
		if(isEmpty(string)) {
			return "";
		}
		int len = string.length();
		String curString = null;
		if(split.equals(string.substring(len -1, len))) {
			curString = string.substring(0, len - 1);
		} else {
			curString = string;
		}
		String[] arr = curString.split(split);
		if(arr.length == 0) {
			return "";
		}
		int index = (int) (Math.random() * arr.length);
		if(index >= arr.length) {
			index = arr.length - 1;
		}
		return arr[index];
	}
	
	/**
	 * 将已指定分隔符的字符串转为List
	 * @param string	字符串
	 * @param regex		分隔符
	 * @return
	 */
	public static List<String> toArray(String string, String regex) {
		List<String> list = new ArrayList<String>();
		if(isEmpty(string)) {
			return list;
		}
		String[] arr = string.split(regex);
		for (String str : arr) {
			if(isEmpty(str)) {
				continue;
			}
			list.add(str);
		}
		return list;
	}
	
	/**
	 * 正则表达式校验字符串是否包含指定规则的字符串
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean isRegexContains(String str, String regex) {
		Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(str);
        return matcher.find();
    }
	
	/**
	 * 将字符串中的带有url链接转为可以点击的链接
	 * @param str
	 * @return
	 */
	public static String parseUrl(String str) {
        Pattern pattern = Pattern.compile(REGEX_URL);
        Matcher matcher = pattern.matcher(str);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
        	StringBuilder replace = new StringBuilder();
            replace.append("<a href=\"").append(matcher.group());
            replace.append("\" target=\"_blank\">").append(matcher.group()).append("</a>");
            matcher.appendReplacement(result, replace.toString());
        }
        matcher.appendTail(result);
		return result.toString();
	}
	
	/**
	 * 判断字符串中是否包含http链接
	 * @param str
	 * @return
	 */
	public static boolean existUrl(String str) {
		return isRegexContains(str, REGEX_URL);
	}
	
	/**
	 * 获取字符串从0开始到指定位置结束的字符串
	 * @param str
	 * @param len
	 * @return
	 */
	public static String getLenStr(String str, Integer len) {
		if(str == null) {
			return null;
		}
		if(str.length() > len) {
			return str.substring(0, len) + "...";
		}
		return str;
	}

}