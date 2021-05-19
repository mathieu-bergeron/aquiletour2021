package ca.ntro.core.models.foreach;

public interface EntryLambda<V extends Object> {

	void execute(String key, V value) throws Break;

}
