package fr.arolla.kata.co2emissions.domain;

import com.google.common.collect.ImmutableMap;

public class Product implements Formula {

	private final String measurement1;
	private final String measurement2;

	public Product(String measurement1, String measurement2) {
		this.measurement1 = measurement1;
		this.measurement2 = measurement2;
	}

	public EmissionResult evaluate(ImmutableMap<String, QualifiedValue> parameters) {
		final QualifiedValue value1 = parameters.get(measurement1);
		final QualifiedValue value2 = parameters.get(measurement2);

		if (value1 == null && value2 == null) {
			return new EmissionResult("No parameter given for computation");
		}

		if (value1 != null && value2 != null) {
			return new EmissionResult(value1.multiply(value2), measurement1 + " (" + value1.value() + ") * "
					+ measurement2 + " (" + value2.value() + ")");
		}
		if (value1 == null) {
			return new EmissionResult("Missing parameter: " + measurement1);
		}
		return new EmissionResult("Missing parameter: " + measurement2);
	}

	@Override
	public int hashCode() {
		return measurement1.hashCode() ^ measurement2.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return measurement1.equals(other.measurement1) && measurement2.equals(other.measurement2);
	}

	@Override
	public String toString() {
		return "Product(" + measurement1 + " * " + measurement2 + ")";
	}

}
