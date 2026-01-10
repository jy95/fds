<div align="center">

<img src="src/site/resources/images/logo.png" alt="FHIR Dosage Icon" style="width: 100px; object-fit: cover; object-position: center;" />

<h1 align="center">FHIR Dosage Support</h1>

Turn [FHIR Dosage](https://build.fhir.org/dosage.html) into human-readable text in your desired language and much more.

[![Build Status](https://github.com/jy95/fds/actions/workflows/maven.yml/badge.svg)](https://github.com/jy95/fds/actions/workflows/maven.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/56beb6b5e12a4de481cd74ad164989f9)](https://app.codacy.com/gh/jy95/fds/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/56beb6b5e12a4de481cd74ad164989f9)](https://app.codacy.com/gh/jy95/fds/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
[![Java Version](https://img.shields.io/badge/Java-17%2B-brightgreen.svg)](https://www.oracle.com/java/)
[![We ❤️ PRs](https://img.shields.io/badge/We%20%E2%9D%A4%EF%B8%8F-Pull%20Requests-brightgreen.svg)](https://github.com/jy95/fds/pulls)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://github.com/jy95/fhir-dosage-utils/blob/main/LICENSE)

<a href="https://www.buymeacoffee.com/GPFR" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" height="41" width="174" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;" ></a>

</div>

---

## Overview

Key Features:

- **Compatible:** Works with [Dosage R4](https://hl7.org/fhir/R4/dosage.html) and [Dosage R5](https://hl7.org/fhir/R5/dosage.html)
- **Extensibility** Easily adaptable and extendable to accommodate your requirements
- **Internationalization** Seamlessly extendable to other languages thanks to [`ICU4J`](https://unicode-org.github.io/icu/userguide/icu4j/)
- **Integration Ease** Works effortlessly in various environments like server, client, ...

Read more on: https://jy95.github.io/fds/

## Supported Locales

The library currently provides out-of-the-box support for the following languages:
- <img src="https://raw.githubusercontent.com/lipis/flag-icons/main/flags/4x3/us.svg" width="20"/> **English** (`en`)
- <img src="https://raw.githubusercontent.com/lipis/flag-icons/main/flags/4x3/fr.svg" width="20"/> **French** (`fr`)
- <img src="https://raw.githubusercontent.com/lipis/flag-icons/main/flags/4x3/nl.svg" width="20"/> **Dutch** (`nl`)
- <img src="https://raw.githubusercontent.com/lipis/flag-icons/main/flags/4x3/de.svg" width="20"/> **German** (`de`)
- <img src="https://raw.githubusercontent.com/lipis/flag-icons/main/flags/4x3/es.svg" width="20"/> **Spanish** (`es`)
- <img src="https://raw.githubusercontent.com/lipis/flag-icons/main/flags/4x3/it.svg" width="20"/> **Italian** (`it`)
- <img src="https://raw.githubusercontent.com/lipis/flag-icons/main/flags/4x3/pt.svg" width="20"/> **Portuguese** (`pt`)

## Credits

Special thanks to : 
- [The original JavaScript library which this library is coming from](https://github.com/jy95/fhir-dosage-utils)
- [NHS England](https://digital.nhs.uk/), the author of `Implementation guide for interoperable medicines` documents ( [Dose to text translations](https://simplifier.net/guide/ukcoreimplementationguideformedicines/dosetotexttranslation?version=current) / [Dosage explanations](https://simplifier.net/guide/ukcoreimplementationguideformedicines/elementdosage?version=current) )

## Contributors

<a href="https://github.com/jy95/fds/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=jy95/fds" alt="Contributors" />
</a>

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=jy95/fds&type=Date)](https://star-history.com/#jy95/fds&Date)
