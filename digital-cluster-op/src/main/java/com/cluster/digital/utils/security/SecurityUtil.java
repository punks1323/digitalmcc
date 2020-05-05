package com.cluster.digital.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
public class SecurityUtil {
	private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

	public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
        	return null;
        }
        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }

	public static Authentication getAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}

	public static ArrayList<String> getUserRoles() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth
				.getAuthorities();
		ArrayList<String> currentUserRoles = new ArrayList<String>();
		for (GrantedAuthority authority : authorities) {
			currentUserRoles.add(authority.getAuthority());
		}
		if (logger.isDebugEnabled()) {
			logger.info("currentUserRoles:" + currentUserRoles);
		}
		return currentUserRoles;
	}
    
    public static boolean doesUserHasRole(String role) {
    	ArrayList<String> currentUserRoles = getUserRoles();
    	for(String s : currentUserRoles) {
    		if(s.equals(role)) {
    			return true;
    		}
    	}
    	return false;
    }    

	public static String generatePIN(String returnDigits, String key) {
		long T0 = 0;// unix epoch time
		// current unix time in seconds since 1970
		long curTime = getCurrentTime().getTime() / 1000L;
		long timeStep = 30;// new PIN for every 30 seconds
		long T = (curTime - T0) / timeStep;
		if (returnDigits == null || returnDigits.isEmpty()) {
			returnDigits = "4";
		}

		String steps = Long.toHexString(T).toUpperCase();
		String newPIN = TOTP.generateTOTP256(toHex(key), steps, returnDigits);
		return newPIN;
	}

	/**
	 * this returns pwd valid forever
	 * 
	 */
	public static String generatePwd(String returnDigits, String key) {
		long T0 = 0;// unix epoch time
		// current unix time in seconds since 1970
		long curTime = getCurrentTime().getTime() / 1000L;
		long timeStep = Long.MAX_VALUE;// this is like pwd valid forever
		long T = (curTime - T0) / timeStep;
		if (returnDigits == null || returnDigits.isEmpty()) {
			returnDigits = "4";
		}

		String steps = Long.toHexString(T).toUpperCase();
		String newPIN = TOTP.generateTOTP256(toHex(key), steps, returnDigits);
		return newPIN;
	}
	
	public static String toHex(String arg) {
		try {
			return String.format("%040x",
					new BigInteger(1, arg.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static Date getCurrentTime() {
		return new Date();
	}

	public static String generateNewUsrPwd(String returnDigits, String key) {
		long T0 = 0;// unix epoch time
		// current unix time in seconds since 1970
		long curTime = getCurrentTime().getTime() / 1000L;
		long timeStep = 30;// this is same for a window 30 secs
		long T = (curTime - T0) / timeStep;
		if (returnDigits == null || returnDigits.isEmpty()) {
			returnDigits = "8";
		}

		String steps = Long.toHexString(T).toUpperCase();
		String newPIN = TOTP.generateTOTP256(toHex(key), steps, returnDigits);
		// to get alphanumeric feel, convert decimal to hex
		newPIN = String.format("%08X", Long.parseLong(newPIN));
		return newPIN;
	}
}
