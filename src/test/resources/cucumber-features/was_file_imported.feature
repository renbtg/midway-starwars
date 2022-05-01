Feature: Was XXX file imported
  We want to know if a file was successfully imported

  Scenario: File was successfully imported to XXX
    Given a filename
    When I ask to import the file
    Then I should be told "Successful import"
