package io.xchris6041x.devin;

import org.bukkit.command.CommandSender;

/**
 * A static class that validates data.
 * @author Christopher Bishop
 */
@SuppressWarnings("ALL")
public final class Validator {

	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is an integer.
	 */
	public static boolean isInteger(String str, CommandSender sender, MessageSender msgSender) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(sender != null && msgSender != null) msgSender.error(sender, "Invalid integer \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is an integer.
	 */
	public static boolean isInteger(String str) {
		return isInteger(str, null, null);
	}
	
	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is an integer.
	 */
	public static boolean isFloat(String str, CommandSender sender, MessageSender msgSender) {
		try {
			Float.parseFloat(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(sender != null && msgSender != null) msgSender.error(sender, "Invalid integer \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is an integer.
	 */
	public static boolean isFloat(String str) {
		return isFloat(str, null, null);
	}
	
	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is a double.
	 */
	public static boolean isDouble(String str, CommandSender sender, MessageSender msgSender) {
		try {
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(sender != null && msgSender != null) msgSender.error(sender, "Invalid number \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is a double.
	 */
	public static boolean isDouble(String str) {
		return isDouble(str, null, null);
	}
	
	/**
	 * @param str - The string to check.
	 * @param msgSender - The message sender used to send a message on failed.
	 * @return whether the string is a boolean.
	 */
	public static boolean isBoolean(String str, CommandSender sender, MessageSender msgSender) {
		try {
			Boolean.parseBoolean(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(sender != null && msgSender != null) msgSender.error(sender, "Invalid number \"" + str + "\".");
			return false;
		}
	}
	/**
	 * @param str - The string to check.
	 * @return whether the string is a boolean.
	 */
	public static boolean isBoolean(String str) {
		return isBoolean(str, null, null);
	}
	
}
