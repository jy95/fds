# FHIR Dosage Support  
  
Turn [FHIR Dosage](https://build.fhir.org/dosage.html) into human-readable text in your desired language and much more.  
  
## Key Features  
  
- **Compatible** — works with both [Dosage R4](https://hl7.org/fhir/R4/dosage.html) and [Dosage R5](https://hl7.org/fhir/R5/dosage.html)  
- **Extensible** — easily adaptable to accommodate your own requirements  
- **Internationalized** — seamlessly extendable to other languages thanks to [ICU4J](https://unicode-org.github.io/icu/userguide/icu4j/)  
- **Easy to integrate** — works effortlessly in server, client, or any other Java environment  
  
## Modules  
  
This project is split into three Maven modules:  
  
| Module | Description |  
|---|---|  
| [Common](common/) | Shared utilities and foundational components used by both R4 and R5 |  
| [R4](r4/) | Support for FHIR R4 Dosage |  
| [R5](r5/) | Support for FHIR R5 Dosage |  
  
## Supported Locales  
  
- 🇺🇸 English (`en`)  
- 🇫🇷 French (`fr`)  
- 🇳🇱 Dutch (`nl`)  
- 🇩🇪 German (`de`)  
- 🇪🇸 Spanish (`es`)  
- 🇮🇹 Italian (`it`)  
- 🇵🇹 Portuguese (`pt`)  
  
## Getting Started  
  
Head over to the [R4](r4/getting-started.html) or [R5](r5/getting-started.html) getting started guides depending on the FHIR version you use.  
  
## Links  
  
- [GitHub repository](https://github.com/jy95/fds)  
- [Issue tracker](https://github.com/jy95/fds/issues)  
- [License: Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)  
- Demo project: [spring-fhir-humanizer-api](https://github.com/jy95/spring-fhir-humanizer-api)
