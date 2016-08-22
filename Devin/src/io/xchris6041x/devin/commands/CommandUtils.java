package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.List;

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
	
}
