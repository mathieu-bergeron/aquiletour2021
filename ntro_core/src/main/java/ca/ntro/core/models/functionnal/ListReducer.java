package ca.ntro.core.models.functionnal;

public interface ListReducer<ACC extends Object, V extends Object> {

	ACC reduce(int index, V value, ACC accumulator) throws Break;

}
