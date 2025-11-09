keywords: config, configuration, setup
description: How to customize the library?

# ⚙️ Configuration

<!-- MACRO{toc|fromDepth=2|toDepth=2|id=toc} -->

This page provides details on configuring the library with [`FDSConfigR4`](./apidocs/io/github/jy95/fds/r4/config/FDSConfigR4.html) class.
Remember to check the [Configuration page of the common module](../common/configuration.html) as well.

## fromCodeableConceptToString

Converts a FHIR `CodeableConcept` to a string representation.

### Why this method exists

The default CodeableConcept representation can be verbose. 
This method extracts key information (e.g., code, display) to make it more human-readable and useful.

### Example Usage

```java
import io.github.jy95.fds.r4.config.FDSConfigR4;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        // Sample
        CodeableConcept concept = new CodeableConcept();
        concept.setText("Example concept");
        concept.addCoding(new Coding().setCode("1234").setDisplay("Example display"));

        // Create a configuration to be used by the library
        var config = FDSConfigR4
                .builder()
                .fromCodeableConceptToString(
                        // TODO Your custom "fromCodeableConceptToString" logic here
                        // Including potential call(s) to your terminology server to turn code(s) to text
                        c -> CompletableFuture.completedFuture(c.getCodingFirstRep().getCode() + " - " + c.getCodingFirstRep().getDisplay())
                ).build();

        // To test the function in action
        String result = config.fromCodeableConceptToString(concept).get();
        System.out.println(result);
    }
}
```

## fromExtensionsToString

Converts a list of FHIR Extension objects to a string representation.

### Why this method exists

FHIR extensions are designed to store extra information, but they can contain pretty much anything possible in FHIR.  
This method empowers users to :  
- Select the extension(s) they would like to display
- Customize each extension rendering to their liking or/and their specifications
- Order the rendering of existing extension(s) in case of precedence rule(s) (e.g., Extension B before Extension A)

### Example Usage

```java
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.StringType;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        // Sample
        List<Extension> extensions = new ArrayList<>();
        extensions.add(new Extension("url1", new StringType("value1")));
        extensions.add(new Extension("url2", new StringType("value2")));

        // Create a configuration to be used by the library
        var config = FDSConfigR4
                .builder()
                .fromExtensionsToString(
                        // TODO Your custom "fromExtensionsToString" logic here
                        // Including potential async processing for each extension
                        exts -> CompletableFuture.completedFuture(
                                exts
                                        .stream()
                                        .map(ext -> ext.getUrl() + ": " + ext.getValue().primitiveValue())
                                        .collect(Collectors.joining(", ", "{", "}"))
                        )
                ).build();

        // To test the function in action
        String result = config.fromExtensionsToString(extensions).get();
        System.out.println(result);
    }
}
```

## fromFHIRQuantityUnitToString

Converts a FHIR Quantity object to a string representation of its unit or code.

### Why this method exists

By default, FHIR `Quantity` object isn't bound to a single Code System (e.g., UCUM, SNOMED-CT, ...) or not (e.g., raw units). 
This method extracts only the unit regardless of its format, ensuring consistent and compact formatting, which is critical for displaying units in UI or reports.

### Example Usage

```java
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Quantity;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        // Sample
        Quantity quantity = new Quantity()
                .setValue(3)
                .setCode("theCode")
                .setSystem("MySuperSystem");

        // Create a configuration to be used by the library
        var config = FDSConfigR4
                .builder()
                .fromFHIRQuantityUnitToString(
                        // TODO Your custom "fromFHIRQuantityUnitToString" logic here
                        // Including potential call(s) to your terminology server to turn code(s) to text 
                        q -> CompletableFuture.completedFuture(q.getCode() + " (" + q.getSystem() + ")")
                ).build();

        // To test the function in action
        String result = config.fromFHIRQuantityUnitToString(quantity).get();
        System.out.println(result);
    }
}
```

## selectDosageAndRateField

Select a specific dosage and rate field from a list of Dosage.DosageDoseAndRateComponent.

### Why this method exists

The FHIR definition of doseAndRate field is flexible, to support both ordered quantity by human or computed quantity by something else. 
This method provides a standardized way to extract the relevant field for a specific use case, improving data clarity and reducing processing complexity.

### Example Usage

```java
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.common.types.DoseAndRateExtractor;
import org.hl7.fhir.r4.model.*;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Sample
        List<Dosage.DosageDoseAndRateComponent> components = new ArrayList<>();
        components.add(new Dosage.DosageDoseAndRateComponent().setDoseQuantity(new Quantity().setValue(10)));
        
        // Create a configuration to be used by the library
        var config = FDSConfigR4
                .builder()
                .selectDosageAndRateField(
                        (doseAndRateComponentList, doseAndRateKey) -> {
                            // TODO Your custom "selectDosageAndRateField" logic here
                            // Here is the default implementation in the library
                            DoseAndRateExtractor<Dosage.DosageDoseAndRateComponent, Type> doseAndRateExtractor = switch (doseAndRateKey) {
                                case DOSE_QUANTITY -> Dosage.DosageDoseAndRateComponent::getDoseQuantity;
                                case DOSE_RANGE -> Dosage.DosageDoseAndRateComponent::getDoseRange;
                                case RATE_QUANTITY -> Dosage.DosageDoseAndRateComponent::getRateQuantity;
                                case RATE_RANGE -> Dosage.DosageDoseAndRateComponent::getRateRange;
                                case RATE_RATIO -> Dosage.DosageDoseAndRateComponent::getRateRatio;
                            };
                            var firstRep = doseAndRateComponentList.get(0);
                            return doseAndRateExtractor.extract(firstRep);
                        }
                ).build();

        // To test the function in action
        var doseAndRateKey = DoseAndRateKey.DOSE_QUANTITY;
        var result = config.selectDosageAndRateField(components, doseAndRateKey);
        System.out.println(result);
    }
}
```

## hasMatchingComponent

Check if a Dosage has any component matching a given predicate.

### Why this method exists

FHIR dosage components often require custom validation or filtering based on specific criteria. 
This method allows for flexible checks to ensure the dosage meets your application's specific requirements, for instance :
- Take ordered quantity by human only
- Take computed quantity by something else only

### Example Usage

```java
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        // Sample
        Dosage dosage = new Dosage();
        dosage.addDoseAndRate(new Dosage.DosageDoseAndRateComponent().setDoseQuantity(new Quantity().setValue(5)));

        // Create a configuration to be used by the library
        var config = FDSConfigR4
                .builder()
                .hasMatchingComponent(
                        // TODO Your custom "hasMatchingComponent" logic here
                        // Here is the default implementation in the library
                        (d, predicate) -> d.hasDoseAndRate() && d.getDoseAndRate().stream().anyMatch(predicate)
                ).build();

        // To test the function in action
        boolean result = config.hasMatchingComponent(dosage);
        System.out.println(result);
    }
}
```
