package ca.ntro.core.models.functionnal;

public interface ListMatcher<I extends Object> {
	boolean match(int index, I item) throws Break;
}
