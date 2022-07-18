Feature: Recipe GET By Search Parameters API Test

  Background:
    * url 'http://localhost:8080/v1'

  Scenario: FAILURE - GET Recipe by search parameters fails due to security check
    Given path '/recipe/1'
    When method GET
    Then status 403

  Scenario: SUCCESS - Find all vegetarian recipes
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 1","numberOfServings":1,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 1","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":1,"instructions":"You need to cook this in the oven","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 2","numberOfServings":2,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 2","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":2,"instructions":"You need to cook this in the oven","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And param type = 'VEGETARIAN'
    When method GET
    Then status 200
    And match each response.data[*].type == 'VEGETARIAN'


  Scenario: SUCCESS - Find recipes that can serve 4 persons and have “potato” as an ingredient
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 1","numberOfServings":4,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 1","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":4,"instructions":"You need to cook this in the oven","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 2","numberOfServings":2,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 2","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":2,"instructions":"You need to cook this in the oven","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 3","numberOfServings":4,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 3","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":4,"instructions":"You need to cook this in the oven","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And params { numberOfServings: 4, includedIngredient: 'potato' }
    When method GET
    Then status 200
    And match each response.data[*].numberOfServings == 4
    And match each response.data[*].ingredients contains {"name":"potato","quantity":#number}

  Scenario: SUCCESS - Find recipes without “salmon” as an ingredient that has “oven” in the instructions.
    Given path '/authentication'
    And request {"data": { "username": "abnamro", "password": "abnamro"}}
    When method POST
    Then status 200
    And def token = response.data.token

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 1","numberOfServings":4,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 1","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":4,"instructions":"You need to cook this in the oven","ingredients":[{"name":"potato","quantity":2},{"name":"cheese","quantity":1}]}}

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 2","numberOfServings":2,"instructions":"You need to cook this in the oven","type":"VEGETARIAN","ingredients":[{"name":"salmon","quantity":1},{"name":"cheese","quantity":3}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 2","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":2,"instructions":"You need to cook this in the oven","ingredients":[{"name":"salmon","quantity":1},{"name":"cheese","quantity":3}]}}

    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And request {"data":{"title":"My Recipe 3","numberOfServings":4,"instructions":"This recipe is really good and efficient","type":"VEGETARIAN","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}
    When method POST
    Then status 201
    And match response == {"data":{"id":#number ,"title":"My Recipe 3","type":"VEGETARIAN","createdAt":"#ignore","numberOfServings":4,"instructions":"This recipe is really good and efficient","ingredients":[{"name":"tomato","quantity":1},{"name":"cheese","quantity":3}]}}


    Given path '/recipe'
    And header Authorization = "Bearer " + token
    And params { excludedIngredient: 'salmon', freeText: 'oven' }
    When method GET
    Then status 200
    And match each response.data[*].ingredients[*].name != 'salmon'
    And match each response.data[*].instructions contains 'oven'