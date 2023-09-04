package com.zl.wise.common.utils;

import com.uaepay.merchant.console.frontend.common.enums.ResponseCode;
import com.uaepay.merchant.console.frontend.common.exception.BusinessLogicException;
import com.uaepay.merchant.console.frontend.common.exception.ValidateException;
import com.zl.wise.common.rpc.i18n.I18nMessage;

import java.util.regex.Pattern;

/**
 * <p>校验工具</p>
 *
 * @author zhou.liu
 * @version : ValidateUtil.java, v 0.1 2019/12/16 11:48 am liuzhou Exp $
 */
public class ValidateUtil {

	public static final String REGEX_MOBILE = "^\\+\\d{1,5}\\-\\d{1,15}$";

	private static final String REGEX_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

	public static void isTrue(boolean flag, String msg) throws ValidateException {
		if (!flag) {
			throw new ValidateException(msg);
		}
	}
	
	public static void isTrue(boolean flag, I18nMessage msg) throws BusinessLogicException {
		if (!flag) {
			throw new BusinessLogicException(ResponseCode.BUSINESS_EXCEPTION, msg);
		}
	}
	
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	public static boolean isValidLong(String str) {
		try {
			Long.valueOf(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
