package ca.ntro.core.models.foreach;

public interface MapReducer<ACC extends Object, V extends Object> {

	ACC reduce(String key, V value, ACC accumulator) throws Break;

}
