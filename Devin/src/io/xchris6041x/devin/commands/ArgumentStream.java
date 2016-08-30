package io.xchris6041x.devin.commands;

public class ArgumentStream {

	private String[] args;
	private int pos = 0;
	
	public ArgumentStream(String[] args) {
		this.args = args;
	}
	
	/**
	 * @param index
	 * @return The argument at a specific index.
	 */
	public String get(int index) {
		return args[index];
	}
	
	/**
	 * @return the next argument in the stream.
	 */
	public String next() {
		String arg = args[pos];
		pos++;
		
		return arg;
	}
	/**
	 * @return whether there is a next argument in the stream.
	 */
	public boolean hasNext() {
		return pos < args.length;
	}
	
	/**
	 * @return the rest of the arguments in string form with spaces between arguments.
	 */
	public String implode() {
		String str = "";
		while(hasNext()) {
			str += next() + " ";
		}
		
		return str.trim();
	}
	
	
	public int getPosition() {
		return pos;
	}
	public void setPosition(int pos) {
		this.pos = pos;
	}
	
}
