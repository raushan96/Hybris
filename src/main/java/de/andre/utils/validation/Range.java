package de.andre.utils.validation;

import java.io.Serializable;
import java.util.Comparator;

public class Range {
	private final long lowBound;
	private final long highBound;

	public Range(final long lowBound, final long highBound) {
		if (lowBound > highBound) {
			throw new IllegalArgumentException("Range\'s low bound must be less than or equal to its high bound: " + lowBound + " >= " + highBound);
		} else if (lowBound < 0L || highBound < 0L) {
			throw new IllegalArgumentException("Bounds should greater than zero" + lowBound + " " + highBound);
		}

		this.lowBound = lowBound;
		this.highBound = highBound;
	}

	public long getHighBound() {
		return highBound;
	}

	public long getLowBound() {
		return lowBound;
	}

	public boolean containsValue(final long pValue) {
		return pValue >= lowBound && pValue <= highBound;
	}

	@Override
	public String toString() {
		return "Range{" +
				"lowBound=" + lowBound +
				", highBound=" + highBound +
				'}';
	}

	public static final Comparator<Range> RANGE_COMPARATOR = new RangeComparator();

	private static final class RangeComparator implements Comparator<Range>, Serializable {

		private static final long serialVersionUID = 3433573350032926385L;

		@Override
		public int compare(final Range r1, final Range r2) {
			if (r1 == r2) {
				return 0;
			} else if (r1 == null) {
				return -1;
			} else if (r2 == null) {
				return 1;
			}

			final Long lowBound1 = r1.getLowBound();
			final Long lowBound2 = r2.getLowBound();
			return lowBound1.compareTo(lowBound2);
		}
	}
}
