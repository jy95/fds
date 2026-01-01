/**
 * Translators for Timing elements.
 *
 * <p>This package provides translation utilities for FHIR Timing constructs
 * (frequency, period, bounds, and other timing-related fields). The classes
 * here convert raw FHIR timing structures into normalized representations
 * consumed by the rest of the library, enabling consistent human-readable
 * output across FHIR versions.</p>
 *
 * <p>Implementations should encapsulate semantics such as period units,
 * frequency ranges, and event timing. They should aim to produce values that
 * are easy for presentation logic to format.</p>
 */
package io.github.jy95.fds.common.translators.timing;