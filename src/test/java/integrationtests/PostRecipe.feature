Feature: Recipe POST API Test

  Background:
    * url 'http://localhost:8080/v1'

  Scenario: FAILURE - POST Recipe fails due to security check
    Given path '/recipe'
    And request {"data":{"title":"My Recipe","numberOfServings":1,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method POST
    Then status 403

  Scenario: SUCCESS - Create a new recipe
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

  Scenario: FAILURE - POST Recipe fails due to missing mandatory fields
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token


    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe","instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method POST
    Then status 400
    And match response == {"code":11115,"message":"Missing mandatory field, field: numberOfServings"}

  Scenario: FAILURE - POST Recipe fails due to sending id within request body
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token


    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"id":1,"title":"My Recipe","numberOfServings":1,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method POST
    Then status 400
    And match response == {"code":11113,"message":"Input should not have an ID"}

  Scenario: FAILURE - POST Recipe fails due to sending a null request body
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token


    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data": null}
    When method POST
    Then status 400
    And match response == {"code":11112,"message":"Request body can not be null"}
