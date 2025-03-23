
---

# AH Assignment


This repository contains an automated test set against the public version of the Rijksmuseum.

---
## Test Scope and Coverage

This test set covers the following key areas of the Rijksmuseum API:
* Sending a request with an Invalid API key
* Retrieving the existing collections with page size
* Testing pagination, filtering, sorting
* Testing Collection details endpoint
* Testing Collection image endpoint
* Testing Usersets endpoint
* Testing Userset Detail endpoint
---
## Test Cases

The test cases are written in Gherkin syntax and are located in the `src/test/resources/features` directory.
* `src/test/resources/features/en/collectionEn.feature`
* `src/test/resources/features/nl/collectionNl.feature`

---
## Project Stack

This project uses the following technologies:

* **Programming Language:** Java
* **Build Tool:** Maven
* **Test Framework:** JUnit 5
* **Library:** REST-Assured
* **Methodology:** BDD
---

## Setup Instructions
1. Clone the repository.
```
git clone https://github.com/
```
2. Open the `.env` file in the project root.
3. In the `.env` file, replace the placeholder value for `API_KEY` with the real value in the assignment.
> **The .env file was committed to version control,since it has a placeholder value**

> **Do not commit this file to version control with the real value.**

### To Run the tests via Terminal/Command Prompt
1. Navigate to the project directory:
* ```
    cd AHAssignment
  ```
2. Run the required command from the following commands
* Run all tests:
    * `mvn test`
* Run all tests via runner:
    * `mvn test -Dtest=org.ahassignment.runners.TestRunner`
* Run Failed tests :
    * `mvn test -Dtest=org.ahassignment.runners.FailedTestCaseRunner`
      _Failed tests are logged in target/failed-tests.txt and can be re-run using this command._

### To Run the tests via IDE _(recommended IDE: intellij)_
1. To run the tests via Runner class,
    * Run all tests: go to `src/test/java/org/ahassignement/runners/TestRunner.java` and click on the `Run` button.
    * Failed tests: go to `src/test/java/org/ahassignement/runners/FailedTestCaseRunner.java` and click on the `Run` button.
    * 
2. To run all the tests via Maven tab, click on Maven tab then double-click on "test", "verify" or "install.
---
## Test Reports
Test reports are automatically generated and stored in the `test_reports` directory. Reports include:
* Cucumber Reports: Detailed BDD execution reports.
* JSON Reports: Machine-readable test result logs.
* PDF Reports: Comprehensive, printable summaries.
* Spark Reports: Interactive HTML reports.
* XML Reports: Standardized test result logs for integration.

---
## Contact
For support or questions, please contact:
* _e.yilmaz.qa@gmail.com_