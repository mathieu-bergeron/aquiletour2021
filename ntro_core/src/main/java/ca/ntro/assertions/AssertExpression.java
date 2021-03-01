package ca.ntro.assertions;

import java.util.ArrayList;
import java.util.List;

public class AssertExpression {

	private List<AssertExpression> subExpressions = new ArrayList<>();

	void addSubExpression(AssertExpression subExpression) {
		subExpressions.add(subExpression);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if(subExpressions.size() > 0) {
			builder.append(subExpressions.get(0));
		}
		
		for(int i = 1; i < subExpressions.size(); i++) {
			builder.append(".");
			builder.append(subExpressions.get(i));
		}
		
		return builder.toString();
	}

	void verify() {
		for(AssertExpression subExpression : subExpressions) {
			subExpression.verify();
		}
	}
}
