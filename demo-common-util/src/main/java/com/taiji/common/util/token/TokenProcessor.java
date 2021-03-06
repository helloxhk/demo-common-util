package com.taiji.common.util.token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 自动生成TOKEN公共util类
 * @author 
 *
 */
public class TokenProcessor {

	private static TokenProcessor instance = new TokenProcessor();
	private static long previous;

	protected TokenProcessor() {  
	} 
	
	public static TokenProcessor getInstance() {  
		return instance;  
	}  

	public static synchronized boolean isTokenValid(HttpServletRequest request) {
		return isTokenValid(request, false);
	}

	public static synchronized boolean isTokenValid(HttpServletRequest request,  boolean reset) {  
	       HttpSession session = request.getSession(false);  
	       if (session == null  )
	          return false;  
	       String saved = (String) session .getAttribute("cn.vicky.struts.action.TOKEN");  
	      if (saved == null)   
	          return false;  
	       if (reset)   
	           resetToken(request);  
	       String token = request  
	               .getParameter("cn.vicky.struts.taglib.html.TOKEN");  
	       if (token == null)  
	           return false;  
	       else  
	           return saved.equals(token);   
		}	
	
	public static synchronized void resetToken(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			session.removeAttribute("cn.vicky.struts.action.TOKEN");
			return;
		}
	}

	public static synchronized void saveToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = generateToken(request);
		if (token != null)
			session.setAttribute("cn.vicky.struts.action.TOKEN", token);
	}

	public static synchronized String generateToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return generateToken(session.getId());
	}

	public static synchronized String generateToken(String id) {
		try {
			long current = System.currentTimeMillis();
			if (current == previous)
				current++;
			previous = current;

			// byte now[] = (current+"").toString().getBytes();
			byte now[] = (new Long(current)).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(id.getBytes());
			md.update(now);
			System.out.println(md.digest().length);
			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}

		return sb.toString();
	}
	public static void main(String[] args) {
		String aa = generateToken("123");
		System.out.println(aa);
	}
}
