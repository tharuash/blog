package com.blog.utils;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;

public class CookieDecrypt {
	
	public Optional<String> readCookie(String key, Cookie[] cookies) {
	    return Arrays.stream(cookies)
	      .filter(c -> key.equals(c.getName()))
	      .map(Cookie::getValue)
	      .findAny();
	}

}
