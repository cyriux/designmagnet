package fr.arolla.kata.co2emissions.domain;


public class EmissionResult {

	
	private final String message;
	private final QualifiedValue value;

	public EmissionResult(String message) {
		this(-1, message);
	}

	public EmissionResult(double value, String message) {
		this(new QualifiedValue(value, 0), message);
	}

	public EmissionResult(QualifiedValue value, String message) {
		this.value = value;
		this.message = message;
	}

	public double doubleValue() {
		return value.value();
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		return (int) (Double.doubleToLongBits(doubleValue()) ^ message.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EmissionResult)) {
			return false;
		}
		final EmissionResult other = (EmissionResult) obj;
		return value.equals(other.value) && message.equals(other.message);
	}

	public boolean isSuccess() {
		return doubleValue() != -1;
	}

	public int missingParameterCount() {
		if (isSuccess()) {
			return 0;
		}
		return message.startsWith("No parameter given") ? 2 : 1;
	}

	@Override
	public String toString() {
		return (isSuccess() ? value : "") + message;
	}
}
