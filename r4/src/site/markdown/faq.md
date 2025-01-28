keywords: faq, question, frequently asked questions
description: Frequently Asked Questions

# ❓ FAQ

<details>
    <summary>How can I customize the default behavior of the library ?</summary>
    <p>
        The library was built with customization in mind. To learn more, check out the 
        <a href="./configuration.html">configuration guide</a>.
    </p>
</details>

<details>
    <summary>Does this library support FHIR R4B release ?</summary>
    <p>
        Yes. According
        <a href="https://hapifhir.io/hapi-fhir/docs/getting_started/r4b.html">HAPI FHIR</a>,
        R4B is mainly identical to FHIR R4 but with specific resources backported from R5.
        You might need to cast your params to R4 through, as support for FHIR R4B is experimental on HAPI FHIR.
    </p>
</details>

<details>
  <summary>Which FHIR extensions should you support ?</summary>
  <ul>
    <li>Your country extensions (e.g. <a href="https://www.ehealth.fgov.be/standards/fhir/">Belgian ones</a>)</li>
    <li><a href="https://hl7.org/fhir/extensions/artifacts.html">FHIR Extensions Pack</a> (for international and interoperability reasons)</li>
  </ul>
  <p>Currently, five extensions are known to have impacts with Dosage / Timing with <a href="https://hl7.org/fhir/extensions/artifacts.html">FHIR Extensions Pack</a>:</p>
  <ul>
    <li><a href="https://hl7.org/fhir/extensions/StructureDefinition-dosage-conditions.html">Conditions</a></li>
    <li><a href="https://hl7.org/fhir/extensions/StructureDefinition-timing-daysOfCycle.html">Days of cycle</a></li>
    <li><a href="https://hl7.org/fhir/extensions/StructureDefinition-timing-dayOfMonth.html">Timing day of month</a></li>
    <li><a href="https://hl7.org/fhir/extensions/StructureDefinition-timing-exact.html">Timing Exact</a></li>
    <li><a href="https://hl7.org/fhir/extensions/StructureDefinition-dosage-minimumGapBetweenDose.html">Minimum Gap Between Doses</a></li>
  </ul>
</details>

<details>
    <summary>I still have a question ...</summary>
    <p>
        Create a ticket to speak about it on our discussions space on 
        <a href="https://github.com/jy95/fds/discussions">Github</a>.
    </p>
</details>