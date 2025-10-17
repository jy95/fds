package io.github.jy95.fds.common.l10n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for QuantityComparator resource bundles across all supported locales.
 * Tests verify FHIR quantity comparator codes are properly maintained and bundle integrity.
 */
public class QuantityComparatorTest {

    // All supported locales for QuantityComparator
    private static final Locale LOCALE_EN = Locale.ENGLISH;
    private static final Locale LOCALE_DE = Locale.GERMAN;
    private static final Locale LOCALE_FR = Locale.FRENCH;
    private static final Locale LOCALE_NL = new Locale("nl");

    // FHIR quantity comparator codes that must be present
    private static final String[] REQUIRED_COMPARATOR_CODES = {
        "<", "<=", ">=", ">", "ad"
    };

    /**
     * Provides all supported locales and their corresponding bundle classes for parameterized testing.
     */
    private static Stream<Arguments> provideBundleLocales() {
        return Stream.of(
                Arguments.of(LOCALE_EN, QuantityComparator.class),
                Arguments.of(LOCALE_DE, QuantityComparator_de.class),
                Arguments.of(LOCALE_FR, QuantityComparator_fr.class),
                Arguments.of(LOCALE_NL, QuantityComparator_nl.class)
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
     * Test that getContents() returns the expected number of entries.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testGetContentsHasExpectedSize(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Object[][] contents = bundle.getContents();
        
        assertEquals(5, contents.length, 
            "QuantityComparator should have exactly 5 entries for locale: " + locale);
    }

    /**
     * Test that all bundles have the same number of keys (ensuring consistency across locales).
     */
    @Test
    void testAllBundlesHaveSameNumberOfKeys() throws Exception {
        ListResourceBundle enBundle = new QuantityComparator();
        ListResourceBundle deBundle = new QuantityComparator_de();
        ListResourceBundle frBundle = new QuantityComparator_fr();
        ListResourceBundle nlBundle = new QuantityComparator_nl();

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
        Set<String> enKeys = getKeysFromBundle(new QuantityComparator());
        Set<String> deKeys = getKeysFromBundle(new QuantityComparator_de());
        Set<String> frKeys = getKeysFromBundle(new QuantityComparator_fr());
        Set<String> nlKeys = getKeysFromBundle(new QuantityComparator_nl());

        assertEquals(enKeys, deKeys, "German bundle should have same keys as English");
        assertEquals(enKeys, frKeys, "French bundle should have same keys as English");
        assertEquals(enKeys, nlKeys, "Dutch bundle should have same keys as English");
    }

    /**
     * Test that all required comparator codes exist in each bundle.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testAllRequiredComparatorCodesExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        for (String code : REQUIRED_COMPARATOR_CODES) {
            assertTrue(keys.contains(code), 
                "Bundle should contain comparator code '" + code + "' for locale: " + locale);
        }
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
     * Test that inequality operators are consistently represented across all locales.
     */
    @Test
    void testInequalityOperatorsAreConsistent() throws Exception {
        // Operators should typically remain the same across locales (mathematical symbols)
        verifyComparatorValue("<", "<");
        verifyComparatorValue("<=", "<=");
        verifyComparatorValue(">=", ">=");
        verifyComparatorValue(">", ">");
    }

    /**
     * Test that "ad" abbreviation is present in all locales.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testAdAbbreviationExists(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        String adValue = getValueFromBundle(bundle, "ad");
        
        assertNotNull(adValue, "'ad' abbreviation should exist for locale: " + locale);
        assertEquals("ad", adValue, "'ad' should remain 'ad' across all locales");
    }

    /**
     * Test less-than operator.
     */
    @ParameterizedTest
    @ValueSource(strings = {"<"})
    void testLessThanOperator(String operator) throws Exception {
        verifyComparatorValue(operator, operator);
    }

    /**
     * Test less-than-or-equal operator.
     */
    @ParameterizedTest
    @ValueSource(strings = {"<="})
    void testLessThanOrEqualOperator(String operator) throws Exception {
        verifyComparatorValue(operator, operator);
    }

    /**
     * Test greater-than-or-equal operator.
     */
    @ParameterizedTest
    @ValueSource(strings = {">="})
    void testGreaterThanOrEqualOperator(String operator) throws Exception {
        verifyComparatorValue(operator, operator);
    }

    /**
     * Test greater-than operator.
     */
    @ParameterizedTest
    @ValueSource(strings = {">"})
    void testGreaterThanOperator(String operator) throws Exception {
        verifyComparatorValue(operator, operator);
    }

    /**
     * Test that ResourceBundle.getBundle correctly loads localized versions.
     */
    @Test
    void testResourceBundleLoadingWithLocale() {
        ResourceBundle enBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.QuantityComparator", LOCALE_EN);
        ResourceBundle deBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.QuantityComparator", LOCALE_DE);
        ResourceBundle frBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.QuantityComparator", LOCALE_FR);
        ResourceBundle nlBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.QuantityComparator", LOCALE_NL);

        assertNotNull(enBundle);
        assertNotNull(deBundle);
        assertNotNull(frBundle);
        assertNotNull(nlBundle);

        // All should return the same values for symbols
        assertEquals("<", enBundle.getString("<"));
        assertEquals("<", deBundle.getString("<"));
        assertEquals("<", frBundle.getString("<"));
        assertEquals("<", nlBundle.getString("<"));
    }

    /**
     * Test fallback behavior for unsupported locale.
     */
    @Test
    void testFallbackToEnglishForUnsupportedLocale() {
        ResourceBundle bundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.QuantityComparator", Locale.JAPANESE);
        assertNotNull(bundle);
        assertEquals("<", bundle.getString("<"), "Should fallback to English for unsupported locale");
        assertEquals("ad", bundle.getString("ad"), "Should fallback to English for unsupported locale");
    }

    /**
     * Test that keys are simple and well-formed.
     */
    @Test
    void testKeyFormat() throws Exception {
        Set<String> keys = getKeysFromBundle(new QuantityComparator());

        for (String key : keys) {
            assertNotNull(key, "Key should not be null");
            assertFalse(key.isEmpty(), "Key should not be empty");
            // Keys can be symbols or short strings like "ad"
            assertTrue(key.length() <= 2, "Key should be 1-2 characters for comparators");
        }
    }

    /**
     * Test English bundle specific values.
     */
    @Test
    void testEnglishBundleValues() throws Exception {
        QuantityComparator enBundle = new QuantityComparator();
        
        assertEquals("<", getValueFromBundle(enBundle, "<"));
        assertEquals("<=", getValueFromBundle(enBundle, "<="));
        assertEquals(">=", getValueFromBundle(enBundle, ">="));
        assertEquals(">", getValueFromBundle(enBundle, ">"));
        assertEquals("ad", getValueFromBundle(enBundle, "ad"));
    }

    /**
     * Test German bundle specific values.
     */
    @Test
    void testGermanBundleValues() throws Exception {
        QuantityComparator_de deBundle = new QuantityComparator_de();
        
        assertEquals("<", getValueFromBundle(deBundle, "<"));
        assertEquals("<=", getValueFromBundle(deBundle, "<="));
        assertEquals(">=", getValueFromBundle(deBundle, ">="));
        assertEquals(">", getValueFromBundle(deBundle, ">"));
        assertEquals("ad", getValueFromBundle(deBundle, "ad"));
    }

    /**
     * Test French bundle specific values.
     */
    @Test
    void testFrenchBundleValues() throws Exception {
        QuantityComparator_fr frBundle = new QuantityComparator_fr();
        
        assertEquals("<", getValueFromBundle(frBundle, "<"));
        assertEquals("<=", getValueFromBundle(frBundle, "<="));
        assertEquals(">=", getValueFromBundle(frBundle, ">="));
        assertEquals(">", getValueFromBundle(frBundle, ">"));
        assertEquals("ad", getValueFromBundle(frBundle, "ad"));
    }

    /**
     * Test Dutch bundle specific values.
     */
    @Test
    void testDutchBundleValues() throws Exception {
        QuantityComparator_nl nlBundle = new QuantityComparator_nl();
        
        assertEquals("<", getValueFromBundle(nlBundle, "<"));
        assertEquals("<=", getValueFromBundle(nlBundle, "<="));
        assertEquals(">=", getValueFromBundle(nlBundle, ">="));
        assertEquals(">", getValueFromBundle(nlBundle, ">"));
        assertEquals("ad", getValueFromBundle(nlBundle, "ad"));
    }

    /**
     * Test that all locales return identical values (since symbols are universal).
     */
    @Test
    void testAllLocalesReturnIdenticalValues() throws Exception {
        ListResourceBundle enBundle = new QuantityComparator();
        ListResourceBundle deBundle = new QuantityComparator_de();
        ListResourceBundle frBundle = new QuantityComparator_fr();
        ListResourceBundle nlBundle = new QuantityComparator_nl();

        for (String code : REQUIRED_COMPARATOR_CODES) {
            String enValue = getValueFromBundle(enBundle, code);
            String deValue = getValueFromBundle(deBundle, code);
            String frValue = getValueFromBundle(frBundle, code);
            String nlValue = getValueFromBundle(nlBundle, code);

            assertEquals(enValue, deValue, "German value should match English for code: " + code);
            assertEquals(enValue, frValue, "French value should match English for code: " + code);
            assertEquals(enValue, nlValue, "Dutch value should match English for code: " + code);
        }
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

    private void verifyComparatorValue(String key, String expectedValue) throws Exception {
        assertEquals(expectedValue, getValueFromBundle(new QuantityComparator(), key));
        assertEquals(expectedValue, getValueFromBundle(new QuantityComparator_de(), key));
        assertEquals(expectedValue, getValueFromBundle(new QuantityComparator_fr(), key));
        assertEquals(expectedValue, getValueFromBundle(new QuantityComparator_nl(), key));
    }
}