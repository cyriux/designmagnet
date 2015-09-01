package fr.arolla.kata.co2emissions.domain;

public class QualifiedValue {

	private static final double ACCURACY = 10E-9;

	private final double value;
	private final double uncertainty;

	public QualifiedValue(double value, double uncertainty) {
		this.value = value;
		this.uncertainty = uncertainty;
	}

	public QualifiedValue multiply(QualifiedValue other) {
		return new QualifiedValue(value * other.value, Math.max(uncertainty, other.uncertainty));
	}

	public double value() {
		return value;
	}

	public double uncertainty() {
		return uncertainty;
	}

	@Override
	public int hashCode() {
		return (int) (Double.doubleToLongBits(uncertainty) ^ Double.doubleToLongBits(value));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof QualifiedValue)) {
			return false;
		}
		QualifiedValue other = (QualifiedValue) obj;
		return isSame(uncertainty, other.uncertainty) && isSame(value, other.value);
	}

	private final static boolean isSame(double a, double b) {
		return Math.abs(a - b) < ACCURACY;
	}

	@Override
	public String toString() {
		return value + "(+/-" + uncertainty + ")";
	}

}
