package ca.ntro.core.models.foreach;

public interface ValueLambda<V extends Object> {
	
	void execute(V value);

}
