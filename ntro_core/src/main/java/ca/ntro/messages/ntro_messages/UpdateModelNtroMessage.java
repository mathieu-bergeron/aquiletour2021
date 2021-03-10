package ca.ntro.messages.ntro_messages;

import java.util.List;

import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.messages.NtroMessage;

public class UpdateModelNtroMessage extends NtroMessage {

	private ValuePath valuePath;
	private String methodName;
	private List<Object> arguments;
	
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
	public List<Object> getArguments() {
		return arguments;
	}
	public void setArguments(List<Object> arguments) {
		this.arguments = arguments;
	}
	
	

}
