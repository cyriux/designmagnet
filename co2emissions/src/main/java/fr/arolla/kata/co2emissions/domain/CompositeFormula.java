package fr.arolla.kata.co2emissions.domain;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableMap;

public class CompositeFormula implements Formula {

	private final List<Formula> formulas;

	public CompositeFormula(Formula... terms) {
		this(Arrays.asList(terms));
	}

	public CompositeFormula(List<Formula> formulas) {
		this.formulas = formulas;
	}

	public EmissionResult evaluate(ImmutableMap<String, QualifiedValue> parameters) {
		for (Formula formula : formulas) {
			final EmissionResult result = formula.evaluate(parameters);
			if (result.isSuccess()) {
				return result;
			}
			if (result.missingParameterCount() == 1) {
				return result;
			}
		}
		return new EmissionResult("No parameter given for computation");
	}

}
