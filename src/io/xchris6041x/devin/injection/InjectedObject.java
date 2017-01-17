package io.xchris6041x.devin.injection;

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
	
	public boolean isSimalarTo(String name, Object obj) {
		return (this.name.equalsIgnoreCase(name) && this.obj.getClass().equals(obj));
	}
	
}
