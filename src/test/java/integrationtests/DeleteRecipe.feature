Feature: Recipe DELETE API Test

  Background:
    * url 'http://localhost:8080/v1'

  Scenario: FAILURE - DELETE Recipe fails due to security check
    Given path '/recipe/1'
    When method DELETE
    Then status 403

  Scenario: SUCCESS - Delete an existing Recipe
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe","numberOfServings":1,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":1,"instructions":"You need to cook this in the oven","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    And def createdID = response.data.id
    And def createdAt = response.data.createdAt

    Given path '/recipe/' + createdID
    And header Authorization = "Bearer " + token
    When method DELETE
    Then status 200

    Given path '/recipe/' + createdID
    And header Authorization = "Bearer " + token
    When method GET
    Then status 404
    And assert response.data == null

  Scenario: FAILURE - DELETE Recipe fails due to recipe not found
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token

    Given path '/recipe/50000'
    And header Authorization = "Bearer " + token
    When method DELETE
    Then status 400
    And match response == {"code":11117,"message":"Recipe is not found"}