/**
 * Translators and mappers for FHIR dosage-related elements.
 *
 * <p>Contains classes that translate FHIR dosage model elements (text,
 * dose-and-rate, route, method, etc.) into intermediate representations 
 * or human-readable strings. Implementations focus on dosage-specific 
 * semantics and maintain version-agnostic logic to ensure the common 
 * rendering components consume a unified data shape.
 *
 * <p>Typical responsibilities include:
 * <ul>
 * <li>Extracting dosage-specific fields from FHIR structures</li>
 * <li>Normalizing dose and rate representations</li>
 * <li>Providing best-effort text fallbacks for missing codings</li>
 * </ul>
 */
package io.github.jy95.fds.common.translators.dosage;