keywords: config, configuration, setup
description: How to customize the library?

# ⚙️ Configuration

<!-- MACRO{toc|fromDepth=2|toDepth=2|id=toc} -->

This page provides details on configuring the library with [`FDSConfig`](./apidocs/io/github/jy95/fds/common/config/FDSConfig.html) class.

## locale

Sets the `Locale` to determine the humanized dosage string's language.

### Why this exists

By default, the library uses English. This allows users to select other locales for internationalization.

### Example Usage

```java
import io.github.jy95.fds.common.config.FDSConfig;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        // Custom locale
        var config = FDSConfig.builder()
                .locale(Locale.FRENCH) // Setting to French
                .build();

        System.out.println("Configured Locale: " + config.getLocale());
    }
}
```

## Display Order

Define the sequence in which dosage elements are displayed. 
Users can adjust or disable specific rules for custom output formatting.

### Why this exists

Dosage information can include many elements. 
This provides control over the order in which these elements are displayed or whether they should appear at all.

### Example Usage

```java
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Custom display order
        var config = FDSConfig.builder()
                .displayOrder(List.of(
                        DisplayOrder.DOSE_QUANTITY, 
                        DisplayOrder.ROUTE, 
                        DisplayOrder.PATIENT_INSTRUCTION
                ))
                .build();

        System.out.println("Configured Display Order: " + config.getDisplayOrder());
    }
}
```

## Display Separator

Overrides the default separator used between dosage elements when constructing a string.

### Why this exists

The default separator, `" - "`, might not suit all use cases. 
This lets users choose a separator that matches their desired format.

### Example Usage

```java
import io.github.jy95.fds.common.config.FDSConfig;

public class Main {
    public static void main(String[] args) {
        // Custom display separator
        var config = FDSConfig.builder()
                .displaySeparator(" | ") // Using a pipe separator
                .build();

        System.out.println("Configured Display Separator: " + config.getDisplaySeparator());
    }
}
```

## Resource Bundle Selector

A function to customize the selection of `ResourceBundle` for a given locale.

### Why this exists

This allows advanced users to override the default resource bundles provided by the library, 
enabling full customization of localized messages or support for additional locales not built into the library.

### Example Usage

```java
import io.github.jy95.fds.common.config.FDSConfig;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        // Custom resource bundle selector
        var config = FDSConfig.builder()
                .selectResourceBundle(locale -> {
                    // Custom logic to return a ResourceBundle
                    return ResourceBundle.getBundle("MyCustomBundle", locale);
                })
                .build();

        ResourceBundle bundle = config.getSelectResourceBundle().apply(Locale.GERMAN);
        System.out.println("Loaded Bundle: " + bundle.getBaseBundleName());
    }
}
```

## Quantity value formatting

A function to customize the display of [`Quantity value`](https://hl7.org/fhir/datatypes-definitions.html#Quantity.value)

### Why this exists

This allows advanced users to override the default formatting provided by the library, empowering users to :
* Apply **locale-specific** number grouping and decimal separators (e.g., using commas in Europe).
* Implement **strict safety rules** (like limiting precision).
* Provide **human-friendly output** for common values (e.g., displaying `0.5` as `1/2`).

### Example Usage

```java
import io.github.jy95.fds.common.config.FDSConfig;
import com.ibm.icu.text.RuleBasedNumberFormat;

import java.math.BigDecimal;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        // Custom resource bundle selector
        var config = FDSConfig.builder()
                .formatQuantityNumber((locale, value) -> {
                    // Custom logic for formatting
                    // https://unicode-org.github.io/icu/userguide/format_parse/numbers/rbnf.html
                    var formatter = new RuleBasedNumberFormat(
                        locale,
                        RuleBasedNumberFormat.SPELLOUT
                    );
                    return formatter.format(value);
                })
                .build();

        BigDecimal testValue = new BigDecimal("5");
        var formatter = config.getFormatQuantityNumber();

        String resultUS = formatter.apply(Locale.US, testValue);
        System.out.println("US: " + testValue + " -> " + resultUS);

        String resultFR = formatter.apply(Locale.FRENCH, testValue);
        System.out.println("FR: " + testValue + " -> " + resultFR);
    }
}
```