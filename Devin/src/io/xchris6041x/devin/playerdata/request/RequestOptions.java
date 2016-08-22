package io.xchris6041x.devin.playerdata.request;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Christopher Bishop
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestOptions {

	public String name() default "Request";
	
	public String[] responses() default {"accept", "decline"};
	
}
