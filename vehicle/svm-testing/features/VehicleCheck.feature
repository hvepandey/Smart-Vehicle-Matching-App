@Complete
Feature: Vehicle Information Checking

Scenario: Vehicle Make and Colour checking
    Given I have vehicles details in 6 csv files and 2 excel files at a pre-configured input directory
    When I launch get vehicle information from DVLA site https://www.gov.uk/get-vehicle-information-from-dvla
    Then I check colour and make of each vehicle on the Vehicle Details Result Page


    