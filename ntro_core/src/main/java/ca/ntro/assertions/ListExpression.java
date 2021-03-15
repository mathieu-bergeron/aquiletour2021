package ca.ntro.assertions;

import java.util.List;

public class ListExpression extends ValueExpression<List<?>> {
	
	private List<?> list;

	public ListExpression(List<?> list) {
		this.list = list;
	}

	public ContainsExpression contains(ContainsLambda lambda) {
		ContainsExpression result = new ContainsExpression(this,lambda);

		setChild(result);
		
		return result;
	}

	@Override
	public List<?> evaluate() {
		return list;
	}
}
