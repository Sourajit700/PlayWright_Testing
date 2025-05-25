Feature: Log in Feature

  @tag001
  Scenario Outline: Log in Feature
    Given I login to the Application "URL"
    When I Execute "<TestCase>" of "<File>" of "<Data>" from Data Sheet
		Then I goto fill the UserName and PassWord
    Examples: 
      | Test Cases | File | Data            |
      | Test001    | Data | TestCase01.xlsx |
