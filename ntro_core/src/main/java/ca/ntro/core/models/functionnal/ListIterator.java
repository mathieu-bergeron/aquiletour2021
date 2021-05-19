package ca.ntro.core.models.functionnal;

public interface ListIterator<I extends Object> {
	void on(I item) throws Break;
}
