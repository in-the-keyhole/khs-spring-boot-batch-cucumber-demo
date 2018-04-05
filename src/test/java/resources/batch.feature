Feature: A batch process for files 
 
  Scenario: All data is returned in uppercase
    Given a set of data is read in 
    When that data is processed
    Then the output data is all uppercase