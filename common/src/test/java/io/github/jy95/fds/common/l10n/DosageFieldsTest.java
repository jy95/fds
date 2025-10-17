package io.github.jy95.fds.common.l10n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for DosageFields resource bundles across all supported locales.
 * Tests verify bundle integrity, key consistency, localization correctness, and proper fallback behavior.
 */
public class DosageFieldsTest {

    // All supported locales for DosageFields
    private static final Locale LOCALE_EN = Locale.ENGLISH;
    private static final Locale LOCALE_DE = Locale.GERMAN;
    private static final Locale LOCALE_FR = Locale.FRENCH;
    private static final Locale LOCALE_NL = new Locale("nl");

    /**
     * Provides all supported locales and their corresponding bundle classes for parameterized testing.
     */
    private static Stream<Arguments> provideBundleLocales() {
        return Stream.of(
                Arguments.of(LOCALE_EN, DosageFields.class),
                Arguments.of(LOCALE_DE, DosageFields_de.class),
                Arguments.of(LOCALE_FR, DosageFields_fr.class),
                Arguments.of(LOCALE_NL, DosageFields_nl.class)
        );
    }

    /**
     * Test that each bundle can be instantiated successfully.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testBundleInstantiation(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        assertNotNull(bundle, "Bundle should be instantiable for locale: " + locale);
        assertNotNull(bundle.getContents(), "Contents should not be null for locale: " + locale);
    }

    /**
     * Test that getContents() returns a non-empty array for each bundle.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testGetContentsNotEmpty(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Object[][] contents = bundle.getContents();
        assertTrue(contents.length > 0, "Contents should not be empty for locale: " + locale);
    }

    /**
     * Test that all bundles have the same number of keys (ensuring consistency across locales).
     */
    @Test
    void testAllBundlesHaveSameNumberOfKeys() throws Exception {
        ListResourceBundle enBundle = new DosageFields();
        ListResourceBundle deBundle = new DosageFields_de();
        ListResourceBundle frBundle = new DosageFields_fr();
        ListResourceBundle nlBundle = new DosageFields_nl();

        int enSize = enBundle.getContents().length;
        int deSize = deBundle.getContents().length;
        int frSize = frBundle.getContents().length;
        int nlSize = nlBundle.getContents().length;

        assertEquals(enSize, deSize, "German bundle should have same number of keys as English");
        assertEquals(enSize, frSize, "French bundle should have same number of keys as English");
        assertEquals(enSize, nlSize, "Dutch bundle should have same number of keys as English");
    }

    /**
     * Test that all bundles contain the same set of keys.
     */
    @Test
    void testAllBundlesHaveSameKeys() throws Exception {
        Set<String> enKeys = getKeysFromBundle(new DosageFields());
        Set<String> deKeys = getKeysFromBundle(new DosageFields_de());
        Set<String> frKeys = getKeysFromBundle(new DosageFields_fr());
        Set<String> nlKeys = getKeysFromBundle(new DosageFields_nl());

        assertEquals(enKeys, deKeys, "German bundle should have same keys as English");
        assertEquals(enKeys, frKeys, "French bundle should have same keys as English");
        assertEquals(enKeys, nlKeys, "Dutch bundle should have same keys as English");
    }

    /**
     * Test that critical linkword keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testLinkwordKeysExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("linkwords.and"), "Bundle should contain 'linkwords.and' key for locale: " + locale);
        assertTrue(keys.contains("linkwords.then"), "Bundle should contain 'linkwords.then' key for locale: " + locale);
    }

    /**
     * Test that amount-related keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testAmountKeysExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("amount.range.withUnit"), "Bundle should contain 'amount.range.withUnit' for locale: " + locale);
        assertTrue(keys.contains("amount.range.withoutUnit"), "Bundle should contain 'amount.range.withoutUnit' for locale: " + locale);
        assertTrue(keys.contains("amount.ratio.denominatorLinkword"), "Bundle should contain 'amount.ratio.denominatorLinkword' for locale: " + locale);
    }

    /**
     * Test that field keys for rate and duration exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testRateDurationFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.rateQuantity"), "Bundle should contain 'fields.rateQuantity' for locale: " + locale);
        assertTrue(keys.contains("fields.rateRange"), "Bundle should contain 'fields.rateRange' for locale: " + locale);
        assertTrue(keys.contains("fields.rateRatio"), "Bundle should contain 'fields.rateRatio' for locale: " + locale);
        assertTrue(keys.contains("fields.duration"), "Bundle should contain 'fields.duration' for locale: " + locale);
        assertTrue(keys.contains("fields.durationMax"), "Bundle should contain 'fields.durationMax' for locale: " + locale);
    }

    /**
     * Test that frequency-related field keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testFrequencyFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.frequency"), "Bundle should contain 'fields.frequency' for locale: " + locale);
        assertTrue(keys.contains("fields.frequencyAndFrequencyMax"), "Bundle should contain 'fields.frequencyAndFrequencyMax' for locale: " + locale);
        assertTrue(keys.contains("fields.frequencyMax"), "Bundle should contain 'fields.frequencyMax' for locale: " + locale);
    }

    /**
     * Test that period-related field keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testPeriodFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.period"), "Bundle should contain 'fields.period' for locale: " + locale);
        assertTrue(keys.contains("fields.periodMax"), "Bundle should contain 'fields.periodMax' for locale: " + locale);
    }

    /**
     * Test that time-related field keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testTimeFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.dayOfWeek"), "Bundle should contain 'fields.dayOfWeek' for locale: " + locale);
        assertTrue(keys.contains("fields.timeOfDay"), "Bundle should contain 'fields.timeOfDay' for locale: " + locale);
        assertTrue(keys.contains("fields.asNeeded"), "Bundle should contain 'fields.asNeeded' for locale: " + locale);
        assertTrue(keys.contains("fields.asNeededFor"), "Bundle should contain 'fields.asNeededFor' for locale: " + locale);
    }

    /**
     * Test that bounds-related field keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testBoundsFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.boundsDuration"), "Bundle should contain 'fields.boundsDuration' for locale: " + locale);
        assertTrue(keys.contains("fields.boundsPeriod"), "Bundle should contain 'fields.boundsPeriod' for locale: " + locale);
        assertTrue(keys.contains("fields.boundsRange"), "Bundle should contain 'fields.boundsRange' for locale: " + locale);
    }

    /**
     * Test that count-related field keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testCountFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.count"), "Bundle should contain 'fields.count' for locale: " + locale);
        assertTrue(keys.contains("fields.countMax"), "Bundle should contain 'fields.countMax' for locale: " + locale);
    }

    /**
     * Test that event-related field keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testEventFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.event"), "Bundle should contain 'fields.event' for locale: " + locale);
    }

    /**
     * Test that max dose field keys exist in all bundles.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testMaxDoseFieldsExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        assertTrue(keys.contains("fields.maxDosePerLifetime"), "Bundle should contain 'fields.maxDosePerLifetime' for locale: " + locale);
        assertTrue(keys.contains("fields.maxDosePerAdministration"), "Bundle should contain 'fields.maxDosePerAdministration' for locale: " + locale);
        assertTrue(keys.contains("fields.maxDosePerPeriod"), "Bundle should contain 'fields.maxDosePerPeriod' for locale: " + locale);
    }

    /**
     * Test that all values are non-null and non-empty strings.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testAllValuesAreNonNullAndNonEmpty(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Object[][] contents = bundle.getContents();

        for (Object[] entry : contents) {
            String key = (String) entry[0];
            Object value = entry[1];

            assertNotNull(value, "Value should not be null for key '" + key + "' in locale: " + locale);
            assertTrue(value instanceof String, "Value should be a String for key '" + key + "' in locale: " + locale);
            assertFalse(((String) value).trim().isEmpty(), "Value should not be empty for key '" + key + "' in locale: " + locale);
        }
    }

    /**
     * Test that localized values are different from English (except for symbols/formatting).
     */
    @Test
    void testLocalizedValuesDifferFromEnglish() throws Exception {
        DosageFields enBundle = new DosageFields();
        DosageFields_de deBundle = new DosageFields_de();
        DosageFields_fr frBundle = new DosageFields_fr();

        // Test linkword "and" which should be localized
        String enAnd = getValueFromBundle(enBundle, "linkwords.and");
        String deAnd = getValueFromBundle(deBundle, "linkwords.and");
        String frAnd = getValueFromBundle(frBundle, "linkwords.and");

        assertEquals("and", enAnd);
        assertEquals("und", deAnd);
        assertEquals("et", frAnd);
        assertNotEquals(enAnd, deAnd, "German 'and' should differ from English");
        assertNotEquals(enAnd, frAnd, "French 'and' should differ from English");
    }

    /**
     * Test that ResourceBundle.getBundle correctly loads localized versions.
     */
    @Test
    void testResourceBundleLoadingWithLocale() {
        ResourceBundle enBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", LOCALE_EN);
        ResourceBundle deBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", LOCALE_DE);
        ResourceBundle frBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", LOCALE_FR);
        ResourceBundle nlBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", LOCALE_NL);

        assertNotNull(enBundle);
        assertNotNull(deBundle);
        assertNotNull(frBundle);
        assertNotNull(nlBundle);

        // Verify different locales return different bundles
        assertEquals("and", enBundle.getString("linkwords.and"));
        assertEquals("und", deBundle.getString("linkwords.and"));
        assertEquals("et", frBundle.getString("linkwords.and"));
        assertEquals("en", nlBundle.getString("linkwords.and"));
    }

    /**
     * Test fallback behavior for unsupported locale.
     */
    @Test
    void testFallbackToEnglishForUnsupportedLocale() {
        // Test with Japanese locale which is not supported - should fallback to English
        ResourceBundle bundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", Locale.JAPANESE);
        assertNotNull(bundle);
        assertEquals("and", bundle.getString("linkwords.and"), "Should fallback to English for unsupported locale");
    }

    /**
     * Test that keys are properly formatted (no typos, consistent naming).
     */
    @Test
    void testKeyNamingConsistency() throws Exception {
        Set<String> keys = getKeysFromBundle(new DosageFields());

        // Check that all field keys start with expected prefixes
        long fieldKeys = keys.stream().filter(key -> key.startsWith("fields.")).count();
        long linkwordKeys = keys.stream().filter(key -> key.startsWith("linkwords.")).count();
        long amountKeys = keys.stream().filter(key -> key.startsWith("amount.")).count();

        assertTrue(fieldKeys > 0, "Should have keys starting with 'fields.'");
        assertTrue(linkwordKeys > 0, "Should have keys starting with 'linkwords.'");
        assertTrue(amountKeys > 0, "Should have keys starting with 'amount.'");

        // Ensure all keys follow expected pattern (no spaces, proper case)
        for (String key : keys) {
            assertFalse(key.contains(" "), "Key should not contain spaces: " + key);
            assertTrue(key.matches("^[a-zA-Z0-9.]+$"), "Key should only contain alphanumeric and dots: " + key);
        }
    }

    /**
     * Test that message format patterns are valid (contain proper placeholders).
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testMessageFormatPatternsAreValid(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Object[][] contents = bundle.getContents();

        for (Object[] entry : contents) {
            String key = (String) entry[0];
            String value = (String) entry[1];

            // Check for balanced braces in message format patterns
            long openBraces = value.chars().filter(ch -> ch == '{').count();
            long closeBraces = value.chars().filter(ch -> ch == '}').count();

            assertEquals(openBraces, closeBraces, 
                "Braces should be balanced in message format for key '" + key + "' in locale: " + locale + ", value: " + value);
        }
    }

    /**
     * Test specific localization for German bundle.
     */
    @Test
    void testGermanSpecificLocalizations() throws Exception {
        DosageFields_de deBundle = new DosageFields_de();
        
        assertEquals("und", getValueFromBundle(deBundle, "linkwords.and"));
        assertEquals("dann", getValueFromBundle(deBundle, "linkwords.then"));
        
        String asNeeded = getValueFromBundle(deBundle, "fields.asNeeded");
        assertTrue(asNeeded.contains("Bedarf") || asNeeded.contains("bedarf"), 
            "German 'as needed' should contain 'Bedarf'");
    }

    /**
     * Test specific localization for French bundle.
     */
    @Test
    void testFrenchSpecificLocalizations() throws Exception {
        DosageFields_fr frBundle = new DosageFields_fr();
        
        assertEquals("et", getValueFromBundle(frBundle, "linkwords.and"));
        assertEquals("puis", getValueFromBundle(frBundle, "linkwords.then"));
        
        String asNeeded = getValueFromBundle(frBundle, "fields.asNeeded");
        assertTrue(asNeeded.contains("nécessaire") || asNeeded.contains("necessaire"), 
            "French 'as needed' should contain 'nécessaire'");
    }

    /**
     * Test specific localization for Dutch bundle.
     */
    @Test
    void testDutchSpecificLocalizations() throws Exception {
        DosageFields_nl nlBundle = new DosageFields_nl();
        
        assertEquals("en", getValueFromBundle(nlBundle, "linkwords.and"));
        assertEquals("dan", getValueFromBundle(nlBundle, "linkwords.then"));
        
        String asNeeded = getValueFromBundle(nlBundle, "fields.asNeeded");
        assertTrue(asNeeded.contains("nodig") || asNeeded.contains("indien"), 
            "Dutch 'as needed' should contain 'nodig' or 'indien'");
    }

    // Helper methods

    private Set<String> getKeysFromBundle(ListResourceBundle bundle) {
        Set<String> keys = new HashSet<>();
        Object[][] contents = bundle.getContents();
        for (Object[] entry : contents) {
            keys.add((String) entry[0]);
        }
        return keys;
    }

    private String getValueFromBundle(ListResourceBundle bundle, String key) {
        Object[][] contents = bundle.getContents();
        for (Object[] entry : contents) {
            if (entry[0].equals(key)) {
                return (String) entry[1];
            }
        }
        return null;
    }
}