package jy95.fhir.common;

import jy95.fhir.common.bundle.MultiResourceBundle;

import jy95.fhir.common.bundle.MultiResourceBundleControl;
import jy95.fhir.common.config.DefaultImplementations;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

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
}
