package ca.ntro.assertions;

import java.util.List;

import ca.ntro.core.introspection.NtroClass;

public class SimpleAssertExpression extends AssertExpression {

	private AssertExpression child;
	
	protected void setChild(AssertExpression child) {
		this.child = child;
	}

	protected AssertExpression getChild() {
		return child;
	}

	public SimpleValueExpression that(Object value) {
		
		SimpleValueExpression result = new SimpleValueExpression(value);
		
		setChild(result);
		
		return result;
	}

	public NtroClassExpression thatClass(NtroClass classSignature) {

		NtroClassExpression result = new NtroClassExpression(classSignature);

		setChild(result);
		
		return result;
	}

	public ObjectExpression thatObject(Object object) {

		ObjectExpression result = new ObjectExpression(object);

		setChild(result);
		
		return result;
	}
	
	public AndExpression and() {

		AndExpression result = new AndExpression(this); // XXX: result.child is the right-hand side of the AND

		setChild(result);
		
		return result;
	}

	public ListExpression thatList(List<?> list) {

		ListExpression result = new ListExpression(list);

		setChild(result);
		
		return result;
	}

	@Override
	public String failMessage() {
		if(child != null) {

			String childMessage = child.failMessage();

			if(childMessage != null) {
				return child.toString() + childMessage;
			}
			
			return null;
		}

		return "Implementation error. child should not be null";
	}
}
