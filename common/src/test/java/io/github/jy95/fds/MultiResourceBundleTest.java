package io.github.jy95.fds;

import io.github.jy95.fds.common.bundle.MultiResourceBundle;

import io.github.jy95.fds.common.bundle.MultiResourceBundleControl;
import io.github.jy95.fds.common.config.DefaultImplementations;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class MultiResourceBundleTest {

    @Test
    public void emptyBundle() {
        MultiResourceBundle bundle = new MultiResourceBundle(null);
        var keys = bundle.getKeys();
        assertFalse(keys.hasMoreElements(), "Keys should be empty in the case of a null or empty bundle");
    }

    @Test
    public void defaultBundle() {
        var bundle = DefaultImplementations.selectResourceBundle(Locale.ENGLISH);
        var keys = bundle.getKeys();
        assertTrue(keys.hasMoreElements(), "Default bundle must have keys");
    }

    @Test
    public void invalidBundle() {
        var bundleControl = new MultiResourceBundleControl(
                "translations2",
                "",
                null
        );
        var bundle = ResourceBundle.getBundle(
                bundleControl.getBaseName(),
                Locale.ENGLISH,
                bundleControl
        );
        var keys = bundle.getKeys();
        assertFalse(keys.hasMoreElements(), "Keys should be empty in the case of a bundle with any valid keys");
    }

    /**
     * Test that DefaultImplementations uses the new l10n classes correctly.
     */
    @Test
    void testDefaultImplementationsUsesNewL10nClasses() {
        var bundle = DefaultImplementations.selectResourceBundle(Locale.ENGLISH);
        assertNotNull(bundle, "Bundle should not be null");
        
        // Test that we can retrieve keys from the new DosageFields bundle
        assertTrue(bundle.containsKey("linkwords.and"), "Should contain keys from DosageFields");
        
        // Test that we can retrieve keys from EventTiming bundle
        assertTrue(bundle.containsKey("MORN"), "Should contain keys from EventTiming");
        
        // Test that we can retrieve keys from QuantityComparator bundle
        assertTrue(bundle.containsKey("<"), "Should contain keys from QuantityComparator");
    }

    /**
     * Test that German locale loads correctly with new l10n classes.
     */
    @Test
    void testGermanLocaleWithNewL10nClasses() {
        var bundle = DefaultImplementations.selectResourceBundle(Locale.GERMAN);
        assertNotNull(bundle);
        
        assertEquals("und", bundle.getString("linkwords.and"), "German 'and' should be 'und'");
        assertTrue(bundle.getString("MORN").toLowerCase().contains("vormittag") 
            || bundle.getString("MORN").toLowerCase().contains("morgen"), 
            "German morning should reference 'Vormittag' or 'Morgen'");
    }

    /**
     * Test that French locale loads correctly with new l10n classes.
     */
    @Test
    void testFrenchLocaleWithNewL10nClasses() {
        var bundle = DefaultImplementations.selectResourceBundle(Locale.FRENCH);
        assertNotNull(bundle);
        
        assertEquals("et", bundle.getString("linkwords.and"), "French 'and' should be 'et'");
        assertTrue(bundle.getString("MORN").toLowerCase().contains("matin"), 
            "French morning should reference 'matin'");
    }

    /**
     * Test that Dutch locale loads correctly with new l10n classes.
     */
    @Test
    void testDutchLocaleWithNewL10nClasses() {
        Locale nlLocale = new Locale("nl");
        var bundle = DefaultImplementations.selectResourceBundle(nlLocale);
        assertNotNull(bundle);
        
        assertEquals("en", bundle.getString("linkwords.and"), "Dutch 'and' should be 'en'");
        assertTrue(bundle.getString("MORN").toLowerCase().contains("ochtend") 
            || bundle.getString("MORN").toLowerCase().contains("morgen"), 
            "Dutch morning should reference 'ochtend' or 'morgen'");
    }

    /**
     * Test that all keys from all three bundles are accessible through DefaultImplementations.
     */
    @Test
    void testAllBundleKeysAccessibleThroughDefaultImplementations() {
        var bundle = DefaultImplementations.selectResourceBundle(Locale.ENGLISH);
        var keys = bundle.getKeys();
        
        Set<String> keySet = new HashSet<>();
        while (keys.hasMoreElements()) {
            keySet.add(keys.nextElement());
        }
        
        // Should have keys from all three bundles
        assertTrue(keySet.size() > 60, "Should have keys from DosageFields, EventTiming, and QuantityComparator");
        
        // Verify keys from each bundle type
        assertTrue(keySet.stream().anyMatch(k -> k.startsWith("linkwords.")), "Should have linkwords from DosageFields");
        assertTrue(keySet.stream().anyMatch(k -> k.startsWith("fields.")), "Should have fields from DosageFields");
        assertTrue(keySet.stream().anyMatch(k -> k.equals("MORN")), "Should have event timing codes");
        assertTrue(keySet.stream().anyMatch(k -> k.equals("<")), "Should have comparator symbols");
    }

    /**
     * Test MultiResourceBundleControl with ClassLoader parameter.
     */
    @Test
    void testMultiResourceBundleControlWithClassLoader() {
        var bundleControl = new MultiResourceBundleControl(
                "translations",
                "io.github.jy95.fds.common.l10n.DosageFields",
                "io.github.jy95.fds.common.l10n.EventTiming"
        );
        
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        var bundle = ResourceBundle.getBundle(
                bundleControl.getBaseName(),
                Locale.ENGLISH,
                loader,
                bundleControl
        );
        
        assertNotNull(bundle);
        assertTrue(bundle.getKeys().hasMoreElements(), "Bundle should have keys");
    }

    /**
     * Test that fallback works properly for unsupported locales.
     */
    @Test
    void testFallbackForUnsupportedLocale() {
        // Japanese is not supported, should fallback to English
        var bundle = DefaultImplementations.selectResourceBundle(Locale.JAPANESE);
        assertNotNull(bundle);
        
        assertEquals("and", bundle.getString("linkwords.and"), 
            "Should fallback to English for unsupported locale");
    }

    /**
     * Test that bundle control handles null or empty base names gracefully.
     */
    @Test
    void testBundleControlWithNullBaseName() {
        var bundleControl = new MultiResourceBundleControl(
                "translations",
                "io.github.jy95.fds.common.l10n.DosageFields",
                null,
                ""
        );
        
        var bundle = ResourceBundle.getBundle(
                bundleControl.getBaseName(),
                Locale.ENGLISH,
                bundleControl
        );
        
        assertNotNull(bundle);
        // Should still have keys from the valid bundle
        assertTrue(bundle.containsKey("linkwords.and"));
    }

    /**
     * Test that MultiResourceBundle aggregates keys from multiple sources.
     */
    @Test
    void testMultiResourceBundleAggregatesKeys() {
        ResourceBundle dosageFields = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", Locale.ENGLISH);
        ResourceBundle eventTiming = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", Locale.ENGLISH);
        ResourceBundle quantityComp = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.QuantityComparator", Locale.ENGLISH);
        
        MultiResourceBundle multiBundle = new MultiResourceBundle(Arrays.asList(dosageFields, eventTiming, quantityComp));
        
        // Should have keys from all bundles
        assertTrue(multiBundle.containsKey("linkwords.and"), "Should have DosageFields keys");
        assertTrue(multiBundle.containsKey("MORN"), "Should have EventTiming keys");
        assertTrue(multiBundle.containsKey("<"), "Should have QuantityComparator keys");
    }

    /**
     * Test that key lookup works correctly when key exists in first bundle.
     */
    @Test
    void testKeyLookupInFirstBundle() {
        ResourceBundle dosageFields = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", Locale.ENGLISH);
        ResourceBundle eventTiming = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", Locale.ENGLISH);
        
        MultiResourceBundle multiBundle = new MultiResourceBundle(Arrays.asList(dosageFields, eventTiming));
        
        String value = multiBundle.getString("linkwords.and");
        assertEquals("and", value, "Should retrieve value from first bundle");
    }

    /**
     * Test that key lookup works correctly when key exists only in second bundle.
     */
    @Test
    void testKeyLookupInSecondBundle() {
        ResourceBundle dosageFields = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.DosageFields", Locale.ENGLISH);
        ResourceBundle eventTiming = ResourceBundle.getBundle("io.github.jy95.fds.common.l10n.EventTiming", Locale.ENGLISH);
        
        MultiResourceBundle multiBundle = new MultiResourceBundle(Arrays.asList(dosageFields, eventTiming));
        
        String value = multiBundle.getString("MORN");
        assertTrue(value.toLowerCase().contains("morning"), "Should retrieve value from second bundle");
    }
}