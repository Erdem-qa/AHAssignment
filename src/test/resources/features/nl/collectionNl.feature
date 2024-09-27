@test
Feature: Retrieve the collections

  Background:
    Given user wants to retrieve collection or object from the Rijksmuseum API in Nederlands

  Scenario: Sending a request with an Invalid API key
    When user sends a get request with an invalid API key
    Then the status code should be 401
    And the error message should be "Invalid key"

  Scenario: Retrieve all the existing collections
    When user sends a get request
    Then the response should contain a list of collections with ps 10
    And the status code should be 200

  Scenario: Retrieve details of a specific object
    When user makes a request to the object details endpoint with "SK-C-5"
    Then the response should contain the object's details
    And the status code should be 200

  Scenario: Retrieve details of a specific object and assert the fields
    When user sends a get request with filtering parameters in Nederlands
    Then user makes a request to the object details endpoint with one of the objectNumbers in the response
    Then the response should contain the filtering parameters in the object's details
    And the status code should be 200

  Scenario: Non-Existing object number request
    When user makes a request to the object details endpoint with "NO-X-123"
    Then the response should not contain any object
    And the status code should be 200

  Scenario: Invalid object number request
    When user makes a request to the object details endpoint with "!#$()=_"
    Then the response should not contain any object
    And the error message should be "Invalid Object Number"
    And the status code should be 400

  Scenario: Retrieve multiple pages of collections
    When user makes a request for page 1 of collections
    And user makes a request for page 2 of collections
    Then the results of the pages should be different
    And the status code should be 200

  Scenario: Filter collections by principalOrFirstMakers
    When user makes a request to the collection endpoint with the involvedMaker filter by "Rembrandt van Rijn"
    Then the response should contain collections related to "Rembrandt van Rijn"
    And the status code should be 200

  Scenario: Request with invalid page number
    When user makes a request with an invalid page number -1
    Then the response should contain a list of collections with ps 10
    And the status code should be 200

  Scenario Outline: Request with very large result page number
    When user makes a request for collections by "<pNumber>" and "<psNumber>"
    Then the response should contain a list of collections with ps 100
    And the status code should be 200
    Examples: page and ps numbers
      | pNumber | psNumber |
      | 100     | 100      |
      | 100     | 95       |
      | 100     | 105      |
      | 99      | 100      |
      | 99      | 105      |

  Scenario Outline: Request with very large page number
    When user makes a request for collections by "<pNumber>" and "<psNumber>"
    Then the response should return an empty result
    And the status code should be 200
    Examples: page and ps numbers
      | pNumber | psNumber |
      | 101     | 100      |
      | 101     | 110      |
      | 101     | 95       |

  Scenario Outline: Sort collections
    When user sends a get request with sorting "<parameters>"
    Then the response should be sorted
    And the status code should be 200

    Examples: sort with parameter
      | parameters                |
      | relevantie                |
      | Soort werk                |
      | Chronologisch oud > nieuw |
      | Chronologisch nieuw > oud |
      | Kunstenaar A > Z          |
      | Kunstenaar Z > A          |

  Scenario: Search for a non-existing item
    When user makes a search with "niet-bestaand-trefwoord"
    Then the response should return an empty result
    And the status code should be 200


  Scenario: Search for an existing item
    When user makes a search with "jonge man"
    Then the response should contain search results related to "jonge man"
    And the status code should be 200