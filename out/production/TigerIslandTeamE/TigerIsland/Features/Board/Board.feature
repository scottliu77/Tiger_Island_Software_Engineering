Feature: Board

#Board scenario test
Scenario: Query Hexagon Neighbors
   Given I have initialized a board without a starting tile
   When I query a Hexagon's neighbors
   Then I receive that Hexagon's neighbors as a result
