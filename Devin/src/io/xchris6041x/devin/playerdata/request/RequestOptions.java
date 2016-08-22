package io.xchris6041x.devin.playerdata.request;

/**
 * @author Christopher Bishop
 */
public @interface RequestOptions {

	public String[] responses() default {"accept", "decline"};
	
}
