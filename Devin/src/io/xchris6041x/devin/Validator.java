package io.xchris6041x.devin;

public final class Validator {

	public static boolean isInteger(String str, MessageSender msgSender) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			if(msgSender != null) msgSender.error("Invalid number \"" + str + "\".");
			return false;
		}
	}
	public static boolean isInteger(String str) {
		return isInteger(str, null);
	}
	
}
