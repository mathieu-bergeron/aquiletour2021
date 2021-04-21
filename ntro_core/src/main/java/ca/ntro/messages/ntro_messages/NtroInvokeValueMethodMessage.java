package ca.ntro.messages.ntro_messages;

import java.util.List;

import ca.ntro.messages.NtroMessage;
import ca.ntro.stores.ValuePath;

public class NtroInvokeValueMethodMessage extends NtroMessage {

	private ValuePath valuePath;
	private String methodName;
	private List<Object> args;
	
	public ValuePath getValuePath() {
		return valuePath;
	}
	public void setValuePath(ValuePath valuePath) {
		this.valuePath = valuePath;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<Object> getArgs() {
		return args;
	}
	public void setArgs(List<Object> args) {
		this.args = args;
	}
	
	

}
