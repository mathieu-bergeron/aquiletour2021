package ca.ntro.assertions;

import java.util.List;

public class ContainsExpression extends SimpleAssertExpression {
	
	private ListExpression listExpression;
	private ContainsLambda lambda;

	public ContainsExpression(ListExpression listExpression, ContainsLambda lambda) {
		this.listExpression = listExpression;
		this.lambda = lambda;
	}

	@Override
	public String failMessage() {
		List<?> list = listExpression.evaluate();
		
		boolean ifContains = false;
		
		for(Object o : list) {
			if(lambda.ifMatches(o)) {
				ifContains = true;
				break;
			}
		}
		
		if(!ifContains) {
			return "contains(" + lambda + ")";
		}

		return null;
	}

}
