package io.xchris6041x.devin.commands;

public final class Validator {

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
