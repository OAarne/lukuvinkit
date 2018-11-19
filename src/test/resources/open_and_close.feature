Feature: User can open and close the application

  Scenario: User can open application and close it with "lopeta" command
    When command "lopeta" is entered
    Then application is closed