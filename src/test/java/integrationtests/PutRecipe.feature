Feature: Recipe PUT API Test

  Background:
    * url 'http://localhost:8080/v1'

  Scenario: FAILURE - PUT Recipe fails due to security check
    Given path '/recipe/1'
    And request {"data":{"title":"My Recipe","numberOfServings":1,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method PUT
    Then status 403

  Scenario: SUCCESS - Update an existing Recipe
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
    And request {"data":{"id":#(createdID),"createdAt":#(createdAt),"title":"My Recipe","numberOfServings":3,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method PUT
    Then status 200
    And match response == {"data":{"id":#number ,"title":"My Recipe","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":3,"instructions":"You need to cook this in the oven","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}

    Given path '/recipe/' + createdID
    And header Authorization = "Bearer " + token
    When method GET
    Then status 200
    And match response == {"data":{"id":#number ,"title":"My Recipe","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":3,"instructions":"You need to cook this in the oven","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    And assert response.data.id == createdID

  Scenario: FAILURE - PUT Recipe fails due to missing mandatory fields
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
    And request {"data":{"id":#(createdID),"title":"My Recipe","numberOfServings":3,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method PUT
    Then status 400
    And match response == {"code":11115,"message":"Missing mandatory field, field: createdAt"}

  Scenario: FAILURE - PUT Recipe fails due to not finding the original recipe
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

    Given path '/recipe/300000'
    And header Authorization = "Bearer " + token
    And request {"data":{"id":#(createdID),"createdAt":#(createdAt),"title":"My Recipe","numberOfServings":3,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method PUT
    Then status 400
    And match response == {"code":11117,"message":"Recipe is not found"}

  Scenario: FAILURE - PUT Recipe fails due to sending a null request body
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
    And request {"data":null}
    When method PUT
    Then status 400
    And match response == {"code":11112,"message":"Request body can not be null"}