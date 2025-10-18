keywords: roadmap, demo, example, specifications
description: Specifications

# 🔎 Specifications

---

## 💊 Core Dosage Details

* **[`sequence`](specs/sequence.html)**: The order in which the dosage instructions should be applied or interpreted (integer).
* **[`text`](specs/text.html)**: Free text dosage instructions (e.g., SIG).
* **[`additionalInstruction`](specs/additionalInstruction.html)**: Supplemental instructions or warnings (e.g., "with meals").
* **[`patientInstruction`](specs/patientInstruction.html)**: Instructions in terms understood by the patient or consumer.
* **As Needed** (`asNeeded[x]`)
    * **[`asNeededBoolean`](specs/asNeeded.html)**: Take "as needed" (Boolean).
    * **[`asNeededCodeableConcept`](specs/asNeeded.html)**: Take "as needed" (for a reason).
* **[`site`](specs/site.html)**: Body site to administer to.
* **[`route`](specs/route.html)**: How drug should enter the body.
* **[`method`](specs/method.html)**: Technique for administering medication.

---

## 🗓️ Specific Schedule (`timing`)

* **[`timing.event`](specs/event.html)**: Specific Date/Times when the medication should be administered
* **[`timing.code`](specs/code.html)**: Complete Statement of the schedule, provided as a code or descriptive text.

## ⏱️ Repetitive Schedule (`timing.repeat`)

* **Administration Count**
  * [`timing.repeat.count`](specs/countCountMax.html): Number of times dose is given.
  * [`timing.repeat.countMax`](specs/countCountMax.html): Maximum number of times dose is given.
* **Frequency per Period**
  * [`timing.repeat.frequency`](specs/frequencyFrequencyMax.html): Number of times dose is given per period (integer).
  * [`timing.repeat.frequencyMax`](specs/frequencyFrequencyMax.html): Maximum number of times dose is given per period (integer).
* **Time Period**
  * [`timing.repeat.period`](specs/periodPeriodMax.html): The time interval between administrations.
  * [`timing.repeat.periodMax`](specs/periodPeriodMax.html): Maximum period interval.
  * [`timing.repeat.periodUnit`](specs/periodPeriodMax.html): Unit of time for the period (e.g., 'd', 'h').
* **Duration**
  * [`timing.repeat.duration`](specs/durationDurationMax.html): Total length of time to repeat the dosage.
  * [`timing.repeat.durationMax`](specs/durationDurationMax.html): Maximum total duration.
  * [`timing.repeat.durationUnit`](specs/durationDurationMax.html): Unit of time for the duration.
* **Boundary** (`bounds[x]`)
  * [`timing.repeat.boundsDuration`](specs/boundsDuration.html): The full duration boundary.
  * [`timing.repeat.boundsRange`](specs/boundsRange.html): The range boundary.
  * [`timing.repeat.boundsPeriod`](specs/boundsPeriod.html): The period boundary (start and end date).
* **Specific Time**
  * [`timing.repeat.dayOfWeek`](specs/dayOfWeek.html): Day(s) of the week for administration.
  * [`timing.repeat.timeOfDay`](specs/timeOfDay.html): Specific time(s) of day for administration.
  * [`timing.repeat.when`](specs/offsetWhen.html): Code for a time-event relationship (e.g., `AC` for "before meals").
  * [`timing.repeat.offset`](specs/offsetWhen.html): Time difference between the `when` event and administration.

---

## 🔢 Dose and Rate (`doseAndRate`)

* **Dose** (`dose[x]`)
    * [`doseAndRate.doseQuantity`](specs/doseQuantity.html): Amount of medication per dose (Quantity).
    * [`doseAndRate.doseRange`](specs/doseRange.html): Range of medication per dose (Range).
* **Rate** (`rate[x]`)
    * [`doseAndRate.rateQuantity`](specs/rateQuantity.html): Amount of medication per unit of time (SimpleQuantity).
    * [`doseAndRate.rateRange`](specs/rateRange.html): Range of rate (Range).
    * [`doseAndRate.rateRatio`](specs/rateRatio.html): Rate expressed as a Ratio.

---

## 🚫 Maximum Limits

* [`maxDosePerPeriod`](specs/maxDosePerPeriod.html): Upper limit on medication per unit of time (Ratio).
* [`maxDosePerAdministration`](specs/maxDosePerAdministration.html): Upper limit on medication per single administration (Quantity).
* [`maxDosePerLifetime`](specs/maxDosePerLifetime.html): Upper limit on medication per patient's lifetime (Quantity).