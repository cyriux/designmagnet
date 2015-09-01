package fr.arolla.kata.co2emissions.domain;

import com.google.common.collect.ImmutableMap;

public interface Formula {

	EmissionResult evaluate(ImmutableMap<String, QualifiedValue> parameters);
}