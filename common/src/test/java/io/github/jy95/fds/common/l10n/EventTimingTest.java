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
 * Comprehensive unit tests for EventTiming resource bundles across all supported locales.
 * Tests verify FHIR event timing codes are properly localized and bundle integrity is maintained.
 */
public class EventTimingTest {

    // All supported locales for EventTiming
    private static final Locale LOCALE_EN = Locale.ENGLISH;
    private static final Locale LOCALE_DE = Locale.GERMAN;
    private static final Locale LOCALE_FR = Locale.FRENCH;
    private static final Locale LOCALE_NL = new Locale("nl");

    // FHIR event timing codes that must be present in all bundles
    private static final String[] REQUIRED_TIME_OF_DAY_CODES = {
        "MORN", "MORN.early", "MORN.late", "NOON", 
        "AFT", "AFT.early", "AFT.late",
        "EVE", "EVE.early", "EVE.late", "NIGHT"
    };

    private static final String[] REQUIRED_SLEEP_CODES = {
        "PHS", "HS", "WAKE", "IMD"
    };

    private static final String[] REQUIRED_MEAL_CODES = {
        "C", "CM", "CD", "CV",
        "AC", "ACM", "ACD", "ACV",
        "PC", "PCM", "PCD", "PCV"
    };

    /**
     * Provides all supported locales and their corresponding bundle classes for parameterized testing.
     */
    private static Stream<Arguments> provideBundleLocales() {
        return Stream.of(
                Arguments.of(LOCALE_EN, EventTiming.class),
                Arguments.of(LOCALE_DE, EventTiming_de.class),
                Arguments.of(LOCALE_FR, EventTiming_fr.class),
                Arguments.of(LOCALE_NL, EventTiming_nl.class)
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
        assertTrue(contents.length >= 35, "Should have at least 35 event timing codes for locale: " + locale);
    }

    /**
     * Test that all bundles have the same number of keys (ensuring consistency across locales).
     */
    @Test
    void testAllBundlesHaveSameNumberOfKeys() throws Exception {
        ListResourceBundle enBundle = new EventTiming();
        ListResourceBundle deBundle = new EventTiming_de();
        ListResourceBundle frBundle = new EventTiming_fr();
        ListResourceBundle nlBundle = new EventTiming_nl();

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
        Set<String> enKeys = getKeysFromBundle(new EventTiming());
        Set<String> deKeys = getKeysFromBundle(new EventTiming_de());
        Set<String> frKeys = getKeysFromBundle(new EventTiming_fr());
        Set<String> nlKeys = getKeysFromBundle(new EventTiming_nl());

        assertEquals(enKeys, deKeys, "German bundle should have same keys as English");
        assertEquals(enKeys, frKeys, "French bundle should have same keys as English");
        assertEquals(enKeys, nlKeys, "Dutch bundle should have same keys as English");
    }

    /**
     * Test that all time-of-day codes exist in each bundle.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testTimeOfDayCodesExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        for (String code : REQUIRED_TIME_OF_DAY_CODES) {
            assertTrue(keys.contains(code), 
                "Bundle should contain time-of-day code '" + code + "' for locale: " + locale);
        }
    }

    /**
     * Test that all sleep-related codes exist in each bundle.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testSleepRelatedCodesExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        for (String code : REQUIRED_SLEEP_CODES) {
            assertTrue(keys.contains(code), 
                "Bundle should contain sleep-related code '" + code + "' for locale: " + locale);
        }
    }

    /**
     * Test that all meal-related codes exist in each bundle.
     */
    @ParameterizedTest
    @MethodSource("provideBundleLocales")
    void testMealRelatedCodesExist(Locale locale, Class<?> bundleClass) throws Exception {
        ListResourceBundle bundle = (ListResourceBundle) bundleClass.getDeclaredConstructor().newInstance();
        Set<String> keys = getKeysFromBundle(bundle);

        for (String code : REQUIRED_MEAL_CODES) {
            assertTrue(keys.contains(code), 
                "Bundle should contain meal-related code '" + code + "' for locale: " + locale);
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
     * Test that MORN (morning) is properly localized in all languages.
     */
    @Test
    void testMorningLocalization() throws Exception {
        String enValue = getValueFromBundle(new EventTiming(), "MORN");
        String deValue = getValueFromBundle(new EventTiming_de(), "MORN");
        String frValue = getValueFromBundle(new EventTiming_fr(), "MORN");
        String nlValue = getValueFromBundle(new EventTiming_nl(), "MORN");

        assertTrue(enValue.toLowerCase().contains("morning"), "English should reference 'morning'");
        assertTrue(deValue.toLowerCase().contains("vormittag") || deValue.toLowerCase().contains("morgen"), 
            "German should reference morning time");
        assertTrue(frValue.toLowerCase().contains("matin"), "French should reference 'matin'");
        assertTrue(nlValue.toLowerCase().contains("ochtend") || nlValue.toLowerCase().contains("morgen"), 
            "Dutch should reference morning time");
    }

    /**
     * Test that meal-related codes are properly localized.
     */
    @Test
    void testMealLocalization() throws Exception {
        // Test breakfast code "CM"
        String enBreakfast = getValueFromBundle(new EventTiming(), "CM");
        String deBreakfast = getValueFromBundle(new EventTiming_de(), "CM");
        String frBreakfast = getValueFromBundle(new EventTiming_fr(), "CM");
        String nlBreakfast = getValueFromBundle(new EventTiming_nl(), "CM");

        assertTrue(enBreakfast.toLowerCase().contains("breakfast"), "English should reference 'breakfast'");
        assertTrue(deBreakfast.toLowerCase().contains("frühstück") || deBreakfast.toLowerCase().contains("fruhstuck"), 
            "German should reference 'Frühstück'");
        assertTrue(frBreakfast.toLowerCase().contains("déjeuner") || frBreakfast.toLowerCase().contains("petit"), 
            "French should reference breakfast");
        assertTrue(nlBreakfast.toLowerCase().contains("ontbijt"), "Dutch should reference 'ontbijt'");
    }

    /**
     * Test that sleep-related codes are properly localized.
     */
    @Test
    void testSleepLocalization() throws Exception {
        // Test "HS" (before sleep)
        String enSleep = getValueFromBundle(new EventTiming(), "HS");
        String deSleep = getValueFromBundle(new EventTiming_de(), "HS");
        String frSleep = getValueFromBundle(new EventTiming_fr(), "HS");
        String nlSleep = getValueFromBundle(new EventTiming_nl(), "HS");

        assertTrue(enSleep.toLowerCase().contains("sleep"), "English should reference 'sleep'");
        assertTrue(deSleep.toLowerCase().contains("schlaf"), "German should reference 'Schlaf'");
        assertTrue(frSleep.toLowerCase().contains("sommeil") || frSleep.toLowerCase().contains("coucher"), 
            "French should reference sleep");
        assertTrue(nlSleep.toLowerCase().contains("slapen") || nlSleep.toLowerCase().contains("bed"), 
            "Dutch should reference sleep");
    }

    /**
     * Test that ResourceBundle.getBundle correctly loads localized versions.
     */
    @Test
    void testResourceBundleLoadingWithLocale() {
        ResourceBundle enBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", LOCALE_EN);
        ResourceBundle deBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", LOCALE_DE);
        ResourceBundle frBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", LOCALE_FR);
        ResourceBundle nlBundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", LOCALE_NL);

        assertNotNull(enBundle);
        assertNotNull(deBundle);
        assertNotNull(frBundle);
        assertNotNull(nlBundle);

        // Verify different translations
        String enMorn = enBundle.getString("MORN");
        String deMorn = deBundle.getString("MORN");
        assertNotEquals(enMorn, deMorn, "German and English translations should differ");
    }

    /**
     * Test fallback behavior for unsupported locale.
     */
    @Test
    void testFallbackToEnglishForUnsupportedLocale() {
        ResourceBundle bundle = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", Locale.JAPANESE);
        assertNotNull(bundle);
        assertTrue(bundle.getString("MORN").toLowerCase().contains("morning"), 
            "Should fallback to English for unsupported locale");
    }

    /**
     * Test that keys follow FHIR event timing code naming conventions.
     */
    @Test
    void testKeyNamingConventions() throws Exception {
        Set<String> keys = getKeysFromBundle(new EventTiming());

        // Check for specific FHIR patterns
        assertTrue(keys.stream().anyMatch(k -> k.contains(".")), 
            "Should have keys with dot notation (e.g., MORN.early)");
        
        // All meal codes should have AC/PC/C prefixes
        assertTrue(keys.stream().anyMatch(k -> k.startsWith("AC")), "Should have AC (ante cibum) codes");
        assertTrue(keys.stream().anyMatch(k -> k.startsWith("PC")), "Should have PC (post cibum) codes");
        assertTrue(keys.stream().anyMatch(k -> k.startsWith("C") && !k.startsWith("CD")), 
            "Should have C (cibum) codes");
    }

    /**
     * Test specific timing descriptions for afternoon codes.
     */
    @Test
    void testAfternoonTimingCodes() throws Exception {
        EventTiming enBundle = new EventTiming();
        Set<String> keys = getKeysFromBundle(enBundle);

        assertTrue(keys.contains("AFT"), "Should have AFT code");
        assertTrue(keys.contains("AFT.early"), "Should have AFT.early code");
        assertTrue(keys.contains("AFT.late"), "Should have AFT.late code");

        String aft = getValueFromBundle(enBundle, "AFT");
        String aftEarly = getValueFromBundle(enBundle, "AFT.early");
        String aftLate = getValueFromBundle(enBundle, "AFT.late");

        assertTrue(aft.toLowerCase().contains("afternoon"));
        assertTrue(aftEarly.toLowerCase().contains("early"));
        assertTrue(aftLate.toLowerCase().contains("late"));
    }

    /**
     * Test specific timing descriptions for evening codes.
     */
    @Test
    void testEveningTimingCodes() throws Exception {
        EventTiming enBundle = new EventTiming();
        
        String eve = getValueFromBundle(enBundle, "EVE");
        String eveEarly = getValueFromBundle(enBundle, "EVE.early");
        String eveLate = getValueFromBundle(enBundle, "EVE.late");

        assertTrue(eve.toLowerCase().contains("evening"));
        assertTrue(eveEarly.toLowerCase().contains("early"));
        assertTrue(eveLate.toLowerCase().contains("late"));
    }

    /**
     * Test that IMD (immediate) is properly translated.
     */
    @Test
    void testImmediateCode() throws Exception {
        String enImmediate = getValueFromBundle(new EventTiming(), "IMD");
        assertNotNull(enImmediate);
        assertTrue(enImmediate.toLowerCase().contains("single") || enImmediate.toLowerCase().contains("once") 
            || enImmediate.toLowerCase().contains("immediate"), 
            "IMD should indicate single/immediate timing");
    }

    /**
     * Test before/after meal code consistency.
     */
    @Test
    void testMealTimingCodeConsistency() throws Exception {
        EventTiming enBundle = new EventTiming();
        
        // Before meals
        assertNotNull(getValueFromBundle(enBundle, "AC"));
        assertNotNull(getValueFromBundle(enBundle, "ACM"));
        assertNotNull(getValueFromBundle(enBundle, "ACD"));
        assertNotNull(getValueFromBundle(enBundle, "ACV"));

        // After meals
        assertNotNull(getValueFromBundle(enBundle, "PC"));
        assertNotNull(getValueFromBundle(enBundle, "PCM"));
        assertNotNull(getValueFromBundle(enBundle, "PCD"));
        assertNotNull(getValueFromBundle(enBundle, "PCV"));

        // At meals
        assertNotNull(getValueFromBundle(enBundle, "C"));
        assertNotNull(getValueFromBundle(enBundle, "CM"));
        assertNotNull(getValueFromBundle(enBundle, "CD"));
        assertNotNull(getValueFromBundle(enBundle, "CV"));
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