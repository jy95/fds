package jy95.fhir.dosage.utils.types;

public sealed interface Dosage permits DosageR4, DosageR5 {
}

record DosageR4(org.hl7.fhir.r4.model.Dosage dosage) implements Dosage {}
record DosageR5(org.hl7.fhir.r5.model.Dosage dosage) implements Dosage {}

/*
Dosage dosageR4 = new DosageR4(new org.hl7.fhir.r4.model.Dosage());
Dosage dosageR5 = new DosageR5(new org.hl7.fhir.r5.model.Dosage());
 */