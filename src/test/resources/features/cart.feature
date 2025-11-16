Feature: Remove product from cart
  As a user
  I want to remove a product from the cart
  So that I can adjust my order before checkout

  Background:
    Given User is logged in and on the inventory page

  @UC-2 @Regression
  Scenario Outline: UC-2: Remove product from cart
    When User adds product "<firstProduct>" to the cart
    And User adds product "<secondProduct>" to the cart
    Then Cart badge should display "2"
    When User opens the cart
    Then Cart should contain product "<firstProduct>"
    And Cart should contain product "<secondProduct>"
    When User removes product "<firstProduct>" from the cart
    Then Cart should not contain product "<firstProduct>"
    And Cart badge should display "1"
    When User proceeds to checkout
    And User enters address:
      | firstName | lastName    | postalCode |
      | Mateusz   | Konatkowski | 05520      |
    And User continues to the next checkout step
    And User cancels the checkout
    Then User should be redirected to the inventory page

    Examples:
      | firstProduct          | secondProduct            |
      | Sauce Labs Bike Light | Sauce Labs Fleece Jacket |
