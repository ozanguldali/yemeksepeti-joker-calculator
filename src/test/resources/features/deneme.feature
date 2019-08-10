@test
Feature: Test Feature

  Scenario: 1 - happy path

    Given I mock happyRequest rest service

    And the mock status is 200 in the response

    Then the elements equal to the followings in the response
      | customers[0].name | Ozan Güldali |
      | customers[0].cost | 23.0 |
      | customers[1].name | Özgür Cetintas |
      | customers[1].cost | 16.0 |
      | customers[2].name | Murat Ham |
      | customers[2].cost | 17.25 |
      | customers[3].name | Meltem Sen |
      | customers[3].cost | 10.5 |
      | customers[4].name | Kubra Ozcan |
      | customers[4].cost | 13.5 |
      | totalActualCost   | 125.5 |
      | totalDiscountedCost | 80.5 |
      | totalDiscountRatio  | %35.86 |
      | totalRoundedCost    | 80.25  |

  Scenario: 2 - invalid request

    Given I mock happyRequest rest service by playing with existing parameters:
      | customers | remove |

    And the mock status is 200 in the response

    Then the elements equal to the followings in the response
      | errorMessage | 'customers' is not included in the request |

    And the elements are not included in the response
      | customers |
      | totalActualCost   |
      | totalDiscountedCost |
      | totalDiscountRatio  |
      | totalRoundedCost    |
