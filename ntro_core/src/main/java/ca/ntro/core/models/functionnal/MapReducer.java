package ca.ntro.core.models.functionnal;

public interface MapReducer<ACC extends Object, V extends Object> {

	ACC reduce(String key, V value, ACC accumulator) throws Break;

}
