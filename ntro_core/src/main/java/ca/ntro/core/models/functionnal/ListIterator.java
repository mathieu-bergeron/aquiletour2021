package ca.ntro.core.models.functionnal;

public interface ListIterator<I extends Object> {
	void on(int index, I item) throws Break;
}
