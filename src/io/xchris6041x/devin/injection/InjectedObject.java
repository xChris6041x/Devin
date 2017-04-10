package io.xchris6041x.devin.injection;

/**
 * Basic object for keeping injection data.
 * 
 * @author Christopher Bishop
 */
public class InjectedObject {

	private String name;
	private Object obj;
	
	public InjectedObject(String name, Object obj) {
		this.name = name;
		this.obj = obj;
	}
	public InjectedObject(Object obj) {
		this("", obj);
	}

	public String getName() {
		return name;
	}
	public Object getObject() {
		return obj;
	}
	
	public boolean isSimilarTo(String name, Object obj) {
		return (this.name.equalsIgnoreCase(name) && this.obj.getClass().equals(obj));
	}
	
}
