/**
 * Translators for Timing.repeat components.
 *
 * <p>Specialized translators handling the `repeat` substructure of FHIR
 * Timing elements (for example: bounds, count, frequency, period and their
 * maxima/minima). This package isolates logic that interprets repeat rules
 * and maps them to the library's repeat/time abstractions.</p>
 *
 * <p>Note: repeat semantics can be nuanced across FHIR versions; keep this
 * package focused on extracting and normalizing repeat-specific data only.</p>
 */
package io.github.jy95.fds.common.translators.timing.repeat;