package com.pavan.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Utility {

	public static boolean isEmpty(Object obj) {
		try {

			if (obj instanceof Collection) {
				return obj == null || ((Collection<?>) obj).size() == 0;
			} else if (obj instanceof String) {
				return obj == null || ((String) obj).isEmpty();
			} else if (obj instanceof Integer) {
				return obj == null || Integer.valueOf(String.valueOf(obj)) == 0;
			} else if (obj instanceof Long) {
				return obj == null || Long.valueOf(String.valueOf(obj)) == 0;
			} else if (obj instanceof Float) {
				return obj == null || Float.valueOf(String.valueOf(obj)) == 0;
			} else if (obj instanceof Double) {
				return obj == null || Double.valueOf(String.valueOf(obj)) == 0;
			} else {
				return obj == null;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public void deleteFiles(List<File> files) throws IOException {
		for (File file : files) {
			file.delete();
		}
	}

	public static String encrypt(String input) {
		String encrypted = "";
		if (input != null && input.length() > 0) {
			encrypted = Base64.encode(input.getBytes());
		}
		return encrypted;
	}

	public static String decrypt(String input) {
		String decrypted = null;
		if (input != null && input.length() > 0) {
			try {
				decrypted = new String(Base64.decode(input));
			} catch (Exception e) {
				return null;
			}
		}
		return decrypted;
	}

}
