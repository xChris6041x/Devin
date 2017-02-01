package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * A class with all static methods to simplify complex tasks that many commands have such as pagination.
 * 
 * @author Christopher Bishop
 */
public final class CommandUtils {
	
	
	/**
	 * Divide the book into pages and output one page.
	 * @param book - All the possible lines that could be on a page.
	 * @param pageLength - The length of one page (The max length of the return array).
	 * @param pageNumber - The page number (starting at 0).
	 * @return one page.
	 */
	public static String[] pagination(String[] book, int pageLength, int pageNumber) {
		int maxPages = (int) Math.ceil(book.length / (double) pageLength);
		if(pageNumber < 0) {
			pageNumber = 0;
		}
		else if(pageNumber > maxPages) {
			pageNumber = maxPages;
		}
		
		List<String> page = new ArrayList<String>();
		for(int i = 0; i < pageLength; i++) {
			int index = pageLength * pageNumber + i;
			if(index >= book.length) break;
			
			page.add(book[index]);
		}
		
		return page.toArray(new String[0]);
	}
	
	/**
	 * Divide the book into pages and output one page.
	 * @param book - All the possible lines that could be on a page.
	 * @param pageLength - The length of one page (The max length of the return array).
	 * @param pageNumber - The page number (starting at 0).
	 * @return one page.
	 */
	public static String[] pagination(List<String> book, int pageLength, int pageNumber) {
		return pagination(book.toArray(new String[0]), pageLength, pageNumber);
	}
	
	/**
	 * Combine all the args that are contained in "" or '' into a single argument.
	 * @param args - the arguments from the command.
	 * @return the new arguments with combined string arguments.
	 */
	public static String[] stringify(String[] args) {
		List<String> list = new ArrayList<String>();
		
		String str = "";
		char delimiter = '"';
		boolean insideString = false;
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			
			if(insideString) {
				if(arg.charAt(arg.length() - 1) == delimiter) {
					arg = arg.substring(0, arg.length() - 1);
					
					str += " " + arg;
					list.add(str);
					
					str = "";
					insideString = false;
				}
				else{
					str += " " + arg;
				}
			}
			else{
				if(arg.charAt(0) == '"' || arg.charAt(0) == '\'') {
					delimiter = arg.charAt(0);
					arg = arg.substring(1);
					
					if(arg.charAt(arg.length() - 1) == delimiter) {
						arg = arg.substring(0, arg.length() - 1);
						list.add(arg);
					}
					else {
						str = arg;
						insideString = true;
					}
				}
				else{
					list.add(arg);
				}
			}
		}
		
		if(str.length() > 0) {
			list.add(str);
		}
		return list.toArray(new String[0]);
	}
	
}
