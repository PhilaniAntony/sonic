# Sonic

Sonic is a lightweight, superfast API test automation framework inspired by the Sonic Hedgehog sequel.

The framework is designed for simplicity and speed, achieving record-breaking test execution times, especially when working with optimized APIs.

## PRE-REQUISITES ##
- Gradle: Version 8.5+
- Java: Version 17
- IDE: IntelliJ IDEA (recommended)

# Info and usage instructions

## FEATURES ##

Sonic implements a Behavior-Driven Development (BDD) approach to API testing, offering simplicity, speed, and flexibility.

## FRAMEWORK STRUCTURE & LOGIC ##

*BaseTest.java*

- Contains setup and teardown methods shared across all tests.
- Example: setup() is called before each test method to initialize the framework and prepare the environment.

*Config.java*

- Manages custom properties loaded from the sonic.properties file.
- Allows testers to:
   - Define multiple API URIs and endpoints.
   - Extend default properties with custom variables and getter methods.

*SampleTest.java*

- Serves as a proof of concept for tests using custom properties.
- Contains actual test steps for verifying specific business processes.

### build.gradle file ###

The build.gradle file includes all dependencies required to compile the project, including:

- Libraries for API testing.
- Unit testing framework dependencies.
- Key customizations:
   - Use the useTestNG {} block to specify TestNG as the unit testing framework.

## EXECUTING TEST SUITE ##

### Running tests locally (Command Line or IDE:) ###

1. If you plan to run the tests via your IDE e.g. IntelliJ then make sure the following parameters are set:
   *"api.uri"* - specify api host uri e.g. https://api.instantwebtools.net
   *"api.base.path"* - specify api base path. e.g. base=/v1/airlines
2. Disable any tests you want to exclude in you test run using the TestNG enabled parameter e.g. @Test(enabled=false)
3. Save changes
4. Execute the following commands in your IDE terminal:
```$xslt
In Windows terminal: ./gradlew.bat clean test
In Mac terminal: ./gradlew clean test