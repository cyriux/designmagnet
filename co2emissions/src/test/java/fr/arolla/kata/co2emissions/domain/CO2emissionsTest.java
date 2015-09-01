package fr.arolla.kata.co2emissions.domain;

import static org.junit.Assert.assertEquals;

import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class CO2emissionsTest {

	@Test
	public void annual_emissions_fail_with_no_input() {
		assertEquals(new EmissionResult("No parameter given for computation"), annualEmissionOld(param().build()));
	}

	@Test
	public void annual_emissions_fail_with_one_input_for_average() {
		assertEquals(new EmissionResult("Missing parameter: householdNumber"),
				annualEmissionOld(param().put("averageEmissionPerHousehold", 10.0).build()));
	}

	@Test
	public void annual_emissions_fail_with_one_input_for_total_consumption() {
		assertEquals(new EmissionResult("Missing parameter: householdNumber"),
				annualEmissionOld(param().put("averageEmissionPerHousehold", 10.0).build()));
	}

	@Test
	public void annual_emissions_succeeds_using_average() {
		assertEquals(new EmissionResult(10.0 * 2500000.0, "averageEmissionPerHousehold (" + 10.0
				+ ") * householdNumber (" + 2500000.0 + ")"),
				annualEmissionOld(param().put("averageEmissionPerHousehold", 10.0).put("householdNumber", 2500000.0)
						.build()));
	}

	@Test
	public void annual_emissions_succeeds_using_total_consumption() {
		assertEquals(
				new EmissionResult(30000.0 * 4000.0, "totalFuelConsumption (" + 30000.0 + ") * fuelStandardEmission ("
						+ 4000.0 + ")"),
				annualEmissionOld(param().put("totalFuelConsumption", 30000.0).put("fuelStandardEmission", 4000.0).build()));
	}

	@Test
	public void annual_emissions_succeeds_using_the_most_accurate() {
		final ImmutableMap<String, Double> params = param().put("averageEmissionPerHousehold", 10.0)
				.put("householdNumber", 2500000.0).put("totalFuelConsumption", 30000.0)
				.put("fuelStandardEmission", 4000.0).build();

		assertEquals(new EmissionResult(30000.0 * 4000.0, "totalFuelConsumption (" + 30000.0
				+ ") * fuelStandardEmission (" + 4000.0 + ")"), annualEmissionOld(params));
	}

	@Test
	public void annual_emissions_succeeds_using_the_most_accurate_really() {
		final ImmutableMap<String, QualifiedValue> params = new ImmutableMap.Builder<String, QualifiedValue>()
				.put("averageEmissionPerHousehold", new QualifiedValue(10.0, 0.5))
				.put("householdNumber", new QualifiedValue(2500000.0, 100))
				.put("totalFuelConsumption", new QualifiedValue(30000.0, 15))
				.put("fuelStandardEmission", new QualifiedValue(4000.0, 35)).build();

		assertEquals(new EmissionResult(new QualifiedValue(30000.0 * 4000.0, 35), "totalFuelConsumption (" + 30000.0
				+ ") * fuelStandardEmission (" + 4000.0 + ")"), annualEmission(params));
	}

	private EmissionResult annualEmission(ImmutableMap<String, QualifiedValue> parameters) {
		final Product totalConsumptionFormula = new Product("totalFuelConsumption", "fuelStandardEmission");
		final Product averageFormula = new Product("averageEmissionPerHousehold", "householdNumber");
		final Formula formula = new CompositeFormula(totalConsumptionFormula, averageFormula);
		return formula.evaluate(parameters);
	}

	private EmissionResult annualEmissionOld(ImmutableMap<String, Double> parameters) {
		return annualEmission(toParams(parameters));
	}

	private final static Builder<String, Double> param() {
		return new ImmutableMap.Builder<String, Double>();
	}

	private final static ImmutableMap<String, QualifiedValue> toParams(ImmutableMap<String, Double> parameters) {
		final Builder<String, QualifiedValue> builder = new ImmutableMap.Builder<String, QualifiedValue>();
		for (Entry<String, Double> e : parameters.entrySet()) {
			builder.put(e.getKey(), new QualifiedValue(e.getValue(), 0));
		}
		return builder.build();
	}
}
