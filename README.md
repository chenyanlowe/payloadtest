# payloadtest
This demo is for the **QA Automation Code Challenge** from Airwallex
The demo implements the function of checking the functionality of the endpoint "http://preview.airwallex.com:30001/bank" 

## Classes Information

### PayloadTestFactory
    TestNg factory class. It will create a suite of the cases according to the json array which it gets from the "Payload.json".

    - createInstances: Create test instances.
    - getBankData: Transfer the date to a Bank type array.
    - convertToPlatformPath: Convert the file path to fit the different os. 

### PayloadTest
    Template testing file for the test generating.

    - getHttpConnection: Get connection with the endpoint.
    - payloadTest: Send the post request and get the response message.
    - closeHttpConnection: close connection after test.
    - fieldsCheck: Check the bank data and give a expecting code to compare with the returning from the server.
    - errorMessage: ErrorMessage accords to the expecting code.

### Bank
    Bank instance class for the json type data saving.

### Payload.json
    An json array to save all of the test data. Every single bank type json object in the array will create a new test case when running.

## To install

### Prerequisites
* [JDK]
* [Maven] 

### Normal installation
1. Download project: `git@github.com:chenyanlowe/payloadtest.git`
2. Https: https://github.com/chenyanlowe/payloadtest.git

## To test
teminal: java -jar payloadtest.jar run.xml
eclipse: run run.xml as testng
