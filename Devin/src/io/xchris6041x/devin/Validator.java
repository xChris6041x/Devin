package io.xchris6041x.devin;

/**
 * A static class that validates data.
 * @author Christopher Bishop
 */
public final class Validator {

	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is an integer.
	 */
	public static boolean isInteger(String str, MessageSender msgSender) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(msgSender != null) msgSender.error("Invalid integer \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is an integer.
	 */
	public static boolean isInteger(String str) {
		return isInteger(str, null);
	}
	
	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is an integer.
	 */
	public static boolean isFloat(String str, MessageSender msgSender) {
		try {
			Float.parseFloat(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(msgSender != null) msgSender.error("Invalid integer \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is an integer.
	 */
	public static boolean isFloat(String str) {
		return isFloat(str, null);
	}
	
	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is a double.
	 */
	public static boolean isDouble(String str, MessageSender msgSender) {
		try {
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(msgSender != null) msgSender.error("Invalid number \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is a double.
	 */
	public static boolean isDouble(String str) {
		return isDouble(str, null);
	}
	
	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is a boolean.
	 */
	public static boolean isBoolean(String str, MessageSender msgSender) {
		try {
			Boolean.parseBoolean(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(msgSender != null) msgSender.error("Invalid number \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is a boolean.
	 */
	public static boolean isBoolean(String str) {
		return isBoolean(str, null);
	}
	
}
