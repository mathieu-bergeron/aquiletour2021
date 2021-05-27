package ca.aquiletour.core.models.courses.base.lambdas;

import ca.ntro.core.system.trace.T;

public class BreakableAccumulator<V extends Object> {
	
	private boolean shouldBreak = false;
	private V accumulator;
	
	public void setShouldBreak(boolean shouldBreak) {
		this.shouldBreak = shouldBreak;
	}

	public void setAccumulator(V accumulator) {
		this.accumulator = accumulator;
	}

	public BreakableAccumulator(V accumulator) {
		T.call(this);
		this.accumulator = accumulator;
	}

	public boolean shouldBreak() {
		return shouldBreak;
	}

	public V getAccumulator() {
		return accumulator;
	}
}
