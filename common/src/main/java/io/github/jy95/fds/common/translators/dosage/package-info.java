/**
 * Translators and mappers for dosage-related elements.
 *
 * <p>Contains classes that translate FHIR dosage model elements (text,
 * dose-and-rate, route, method, etc.) into the library's intermediate
 * representations or human-readable strings used by the rendering components.
 * Implementations in this package should focus on dosage-specific semantics
 * and keep conversion logic version-agnostic where possible so the common
 * rendering code can consume a unified shape.</p>
 *
 * <p>Typical responsibilities:
 * <ul>
 *   <li>Extract dosage-specific fields from FHIR structures</li>
 *   <li>Normalize dose and rate representations</li>
 *   <li>Provide best-effort text fallbacks for missing codings</li>
 * </ul>
 * </p>
 */
package io.github.jy95.fds.common.translators.dosage;