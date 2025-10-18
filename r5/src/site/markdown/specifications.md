keywords: roadmap, demo, example, specifications
description: FHIR R5 Dosage Specifications

# 🔎 Specifications

---

## 💊 Core Dosage Details

* **[`sequence`](specs/sequence.html)**: The order in which the dosage instructions should be applied or interpreted (integer).
* **[`text`](specs/text.html)**: Free text dosage instructions (e.g., SIG).
* **[`additionalInstruction`](specs/additionalInstruction.html)**: Supplemental instructions or warnings (e.g., "with meals").
* **[`patientInstruction`](specs/patientInstruction.html)**: Instructions in terms understood by the patient or consumer.
* **As Needed**
    * **[`asNeeded`](specs/asNeeded.html)**: Boolean indicator for "as needed" dosing.
    * **[`asNeededFor`](specs/asNeeded.html)**: A CodeableConcept specifying the reason for "as needed" dosing.
* **Administration Context**
    * **[`site`](specs/site.html)**: Body site to administer to.
    * **[`route`](specs/route.html)**: How drug should enter the body.
    * **[`method`](specs/method.html)**: Technique for administering medication.

---

## ⏱️ Timing Schedule (`timing`)

* **[`timing`](specs/timing.html)**: The parent element for the full timing schedule (Timing datatype).
    * **Repeat (`timing.repeat`)**
        * **Boundary** (`bounds[x]`)
            * **[`timing.repeat.boundsDuration`](specs/boundsDuration.html)**: The overall duration of the schedule.
            * **[`timing.repeat.boundsRange`](specs/boundsRange.html)**: The range of time the schedule is to be repeated.
            * **[`timing.repeat.boundsPeriod`](specs/boundsPeriod.html)**: The period (start/end date) of the schedule.
        * **Counts**
            * **[`timing.repeat.count`](specs/countCountMax.html)**: Number of times the dose is to be given.
            * **[`timing.repeat.countMax`](specs/countCountMax.html)**: Maximum number of times the dose may be given.
        * **Duration**
            * **[`timing.repeat.duration`](specs/durationDurationMax.html)**: Total length of time to repeat the dosage.
            * **[`timing.repeat.durationMax`](specs/durationDurationMax.html)**: Maximum total duration.
            * **[`timing.repeat.durationUnit`](specs/durationDurationMax.html)**: Unit of time for the duration.
        * **Frequency / Period**
            * **[`timing.repeat.frequency`](specs/frequencyFrequencyMax.html)**: Number of times dose is given per period.
            * **[`timing.repeat.frequencyMax`](specs/frequencyFrequencyMax.html)**: Maximum frequency.
            * **[`timing.repeat.period`](specs/periodPeriodMax.html)**: The time interval between administrations.
            * **[`timing.repeat.periodMax`](specs/periodPeriodMax.html)**: Maximum period interval.
            * **[`timing.repeat.periodUnit`](specs/periodPeriodMax.html)**: Unit of time for the period (e.g., 'd', 'h').
        * **Specific Time**
            * **[`timing.repeat.dayOfWeek`](specs/dayOfWeek.html)**: Day(s) of the week for administration.
            * **[`timing.repeat.timeOfDay`](specs/timeOfDay.html)**: Specific time(s) of day for administration.
            * **[`timing.repeat.when`](specs/offsetWhen.html)**: Code for a time-event relationship (e.g., `AC` for "before meals").
            * **[`timing.repeat.offset`](specs/offsetWhen.html)**: Time difference from the `when` event to administration.

---

## 🔢 Dose and Rate (`doseAndRate`)

* **[`doseAndRate`](specs/doseAndRate.html)**: A list of components defining the amount and/or rate.
    * **Dose** (`doseAndRate.dose[x]`)
        * **[`doseAndRate.doseRange`](specs/doseRange.html)**: Dose amount expressed as a Range.
        * **[`doseAndRate.doseQuantity`](specs/doseQuantity.html)**: Dose amount expressed as a Quantity.
    * **Rate** (`doseAndRate.rate[x]`)
        * **[`doseAndRate.rateRatio`](specs/rateRatio.html)**: Rate expressed as a Ratio.
        * **[`doseAndRate.rateRange`](specs/rateRange.html)**: Rate expressed as a Range.
        * **[`doseAndRate.rateQuantity`](specs/rateQuantity.html)**: Rate expressed as a Quantity.

---

## 🚫 Maximum Limits

* **[`maxDosePerPeriod`](specs/maxDosePerPeriod.html)**: Upper limit on medication per unit of time (Ratio).
* **[`maxDosePerAdministration`](specs/maxDosePerAdministration.html)**: Upper limit on medication per single administration (Quantity).
* **[`maxDosePerLifetime`](specs/maxDosePerLifetime.html)**: Upper limit on medication per patient's lifetime (Quantity).