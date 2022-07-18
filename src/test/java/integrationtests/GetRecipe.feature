Feature: Recipe GET By ID API Test

  Background:
    * url 'http://localhost:8080/v1'

  Scenario: FAILURE - GET Recipe by ID request fails due to security check
    Given path '/recipe/1'
    When method GET
    Then status 403

  Scenario: SUCCESS - Retrieve an existing Recipe by ID
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

    Given path '/recipe/' + createdID
    And header Authorization = "Bearer " + token
    When method GET
    Then status 200
    And match response == {"data":{"id":#number ,"title":"My Recipe","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":1,"instructions":"You need to cook this in the oven","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    And assert response.data.id == createdID

  Scenario: FAILURE - GET Recipe by ID request fails due to invalid ID
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token

    Given path '/recipe/100000'
    And header Authorization = "Bearer " + token
    When method GET
    Then status 404
    And assert response.data == null