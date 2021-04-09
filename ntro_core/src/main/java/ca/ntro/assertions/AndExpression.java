package ca.ntro.assertions;

public class AndExpression extends SimpleAssertExpression {
	
	private AssertExpression left;
	// XXX: getChild() is the right-hand side of the AND
	
	public AndExpression(AssertExpression left) {
		this.left = left;
	}

	@Override
	public String failMessage() {
		AssertExpression right = getChild();
		
		String leftFailMessage = left.failMessage();
		
		if(leftFailMessage != null) {
			
			return leftFailMessage;
			
		}else {
			
			return right.failMessage();
		}
	}

}
