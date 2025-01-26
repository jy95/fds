keywords: getting started, quickstart, quick start
description: Quickstart guide to set up the library

# ⚡ Getting Started

1. Add the library as a dependency to your project. You can find instructions about it on [Maven Coordinates page](./dependency-info.html)
2. Use the library in your Java code

Here is a complete code sample to illustrate what the library can do for you :

```java
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a configuration to be used by the library
        FDSConfigR4 configR4 = FDSConfigR4
                .builder()
                .build();

        // Create the library with that configuration
        DosageAPIR4 lib = new DosageAPIR4(configR4);
        
        // TODO Your HAPI FHIR Dosage instance you would like to deal with ;)
        Dosage dosage = new Dosage();

        // Converts the dosage object into human-readable text
        String asText = lib.asHumanReadableText(dosage).get();

        // Extract specific existing field(s) to meet your requirements
        String singleField = lib.getFields(dosage, DisplayOrder.DOSE_QUANTITY).get();
        String multipleFields = lib.getFields(dosage, DisplayOrder.TEXT, DisplayOrder.PATIENT_INSTRUCTION).get();
        
        // Converts the dosage object into human-readable text
        String multipleAsText = lib.asHumanReadableText(List.of(dosage));
        
        // And much more ...
    }
}
```

Refer to the [documentation for more details](./apidocs/index.html).