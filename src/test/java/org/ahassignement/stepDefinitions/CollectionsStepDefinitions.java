package org.ahassignement.stepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.ahassignement.TestBase.BaseUrl;


import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
public class CollectionsStepDefinitions extends BaseUrl {

    @Given("user wants to retrieve collection or object from the Rijksmuseum API in English")
    public void userWantsToRetrieveCollectionOrObjectFromTheRijksmuseumAPIInEn() {
        BaseUrl.baseUrlEn();
    }

    @Given("user wants to retrieve collection or object from the Rijksmuseum API in Nederlands")
    public void userWantsToRetrieveCollectionOrObjectFromTheRijksmuseumAPIInNl() {
        BaseUrl.baseUrlNl();
    }

    @When("user sends a get request")
    public void user_sends_a_get_request() {
        response = given()
                .queryParam("key", api_key)
                .queryParam("p", "")
                .queryParam("ps", "")
                .when()
                .get();

    }

    @When("user sends a get request with an invalid API key")
    public void userSendsAGetRequestWithAnInvalidAPIKey() {
        response = given()
                .queryParam("key", "invalidKey")
                .when()
                .get();
    }

    @Then("the response should contain a list of collections with ps {int}")
    public void theResponseShouldContainAListOfCollectionsWithPs(int psNumber) {
        response.then().statusCode(200);
        assertEquals(response.jsonPath().getList("artObjects").size(), psNumber);
    }

    @Then("the response should contain the object's details")
    public void validateObjectDetailsResponse() {
        response.then().statusCode(200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("Object-schema.json"));

    }

    @Then("the response should not contain any object")
    public void theResponseShouldNotContainAnyObject() {
        response.then().statusCode(200);
        assertNull(response.jsonPath().getString("artObject"));
        assertNull(response.jsonPath().getString("artObjectPage"));
    }

    @And("the status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        try {
            assertEquals(expectedStatusCode, response.statusCode());
        } catch (Exception e) {
            throw new RuntimeException("The relevant Http status code should be displayed for the error " + e.getMessage());
        }
    }


    @And("the error message should be {string}")
    public void theErrorMessageShouldBe(String errorMessage) {
        try {
            assertEquals(response.asString(), errorMessage);
        } catch (Exception e) {
            throw new RuntimeException("An error message should be displayed " + e.getMessage());
        }
    }

    @When("user makes a request to the object details endpoint with {string}")
    public void userMakesARequestToTheObjectDetailsEndpointWith(String objectNumber) {
        response = given()
                .when()
                .queryParam("key", api_key)
                .get("/{objectNumber}", objectNumber);

    }


    @When("user makes a request for page {int} of collections")
    public void userMakesARequestForPageOfCollections(int pageNumber) {
        if (pageNumber == 1) {
            response = given()
                    .queryParam("key", api_key)
                    .queryParam("p", pageNumber)
                    .queryParam("ps", "10")
                    .when()
                    .get();
        } else {
            secondPageResponse = given()
                    .queryParam("key", api_key)
                    .queryParam("p", pageNumber)
                    .queryParam("ps", "10")
                    .when()
                    .get();
        }
    }


    @When("user makes a request to the collection endpoint with the involvedMaker filter by {string}")
    public void userMakesARequestToTheCollectionEndpointWithTheInvolvedMakerFilterBy(String involvedMaker) {
        response = given()
                .queryParam("key", api_key)
                .queryParam("involvedMaker", involvedMaker)
                .queryParam("ps", "100")
                .when()
                .get();
    }

    @Then("the results of the pages should be different")
    public void theResultsOfPageAndPageShouldBeDifferent() {
        assertNotNull(response);
        assertNotNull(secondPageResponse);
        assertEquals(10, response.jsonPath().getList("artObjects").size());
        assertEquals(10, secondPageResponse.jsonPath().getList("artObjects").size());
        assertNotEquals(response.jsonPath().getList("artObjects"), secondPageResponse.jsonPath().getList("artObjects"));
    }

    @Then("the response should contain collections related to {string}")
    public void validateInvolvedMakerFilteredResponse(String expectedMaker) {
        response.then().statusCode(200);

        List<String> PMakers = response.jsonPath()
                .getList("artObjects.principalOrFirstMaker");

        assertNotNull("The list of principal makers should not be null", PMakers);

        try {
            assertTrue("Some principal makers do not match the expected principalOrFirstMakers: ", PMakers.stream().allMatch(eachMaker -> eachMaker.equals(expectedMaker)));
        } catch (Exception e) {
            throw new RuntimeException("The allMatch condition does not meet " + e.getMessage());
        }

    }

    @Then("the response should return an empty result")
    public void theResponseShouldReturnAnEmptyResult() {
        response.then().statusCode(200);
        assertTrue(response.jsonPath().getList("artObjects").isEmpty());
    }

    @When("user makes a request with an invalid page number {int}")
    public void userMakesARequestWithAnInvalidPageNumber(int invalidPageNumber) {
        response = given()
                .queryParam("key", api_key)
                .queryParam("p", invalidPageNumber)
                .queryParam("ps", "10")
                .when()
                .get();
    }

    @When("user makes a request for collections by {string} and {string}")
    public void userMakesARequestForCollectionsByPNumberAndPsNumber(String pNumber, String psNumber) {
        response = given()
                .queryParam("key", api_key)
                .queryParam("p", pNumber)
                .queryParam("ps", psNumber)
                .when()
                .get();
    }

    @When("user sends a get request with filtering parameters in English")
    public void userSendsAGetRequestWithFilteringParametersInEn() {
        response = given()
                .queryParam("key", api_key)
                .queryParam("place", "Amsterdam")
                .queryParam("type", "painting")
                .queryParam("technique", "paint")
                .queryParam("f.dating.period", 17)
                .queryParam("imgonly", true)
                .queryParam("material", "oil paint (paint)")
                .queryParam("p", "1")
                .queryParam("ps", "100")
                .when()
                .get();
        objectNumber = response.jsonPath().getString("artObjects[0].objectNumber");

    }

    @When("user sends a get request with filtering parameters in Nederlands")
    public void userSendsAGetRequestWithFilteringParametersInNl() {
        response = given()
                .queryParam("key", api_key)
                .queryParam("place", "Amsterdam")
                .queryParam("type", "schilderij")
                .queryParam("technique", "schilderen")
                .queryParam("f.dating.period", 17)
                .queryParam("imgonly", true)
                .queryParam("material", "olieverf")
                .queryParam("p", "1")
                .queryParam("ps", "100")
                .when()
                .get();
        objectNumber = response.jsonPath().getString("artObjects[0].objectNumber");

    }


    @Then("user makes a request to the object details endpoint with one of the objectNumbers in the response")
    public void userMakesARequestToTheObjectDetailsEndpointWithOneOfTheObjectNumbersInTheResponse() {
        response = given()
                .when()
                .queryParam("key", api_key)
                .get("/{objectNumber}", objectNumber);

    }

    @Then("the response should contain the filtering parameters in the object's details")
    public void theResponseShouldContainTheFilteringParametersInTheObjectSDetails() {
        assertNotNull(response.jsonPath().getString("artObject.objectTypes"));
        assertNotNull(response.jsonPath().getString("artObject.materials"));
        assertNotNull(response.jsonPath().getString("artObject.techniques"));
        assertNotNull(response.jsonPath().getString("artObject.productionPlaces"));
        assertNotNull(response.jsonPath().getString("artObject.dating.period"));
        assertNotNull(response.jsonPath().getString("artObject.hasImage"));
    }


    @When("user sends a get request with sorting {string}")
    public void userSendsAGetRequestWithSorting(String parameters) {
        response = given()
                .queryParam("key", api_key)
                .queryParam("p", "1")
                .queryParam("ps", "10")
                .queryParam("s", parameters)
                .when()
                .get();

    }

    @Then("the response should be sorted")
    public void theResponseShouldBeSorted() {
        List<String> firstObjects = response.jsonPath().getList("artObjects.objectNumber");
        HashSet<String> uniqueFirstObjects = new HashSet<>(firstObjects);
        assertEquals(uniqueFirstObjects.size(), firstObjects.size());

    }


    @When("user makes a search with {string}")
    public void userMakesASearchWithA(String keyword) {
        response = given()
                .queryParam("key", api_key)
                .queryParam("q", keyword)
                .queryParam("place", "Amsterdam")
                .queryParam("p", "1")
                .queryParam("ps", "100")
                .when()
                .get();
        response.prettyPrint();
    }


    @Then("the response should contain search results related to {string}")
    public void theResponseShouldContainSearchResultsRelatedTo(String keyword) {
        List<Map<String, String>> searchResults = response.jsonPath().getList("artObjects");
        boolean foundKeyword = false;
        boolean notFoundKeyword = false;
        for (Map<String, String> result : searchResults) {
            String title = result.get("title").toLowerCase();
            String longTitle = result.get("longTitle").toLowerCase();
            String principalOrFirstMaker = result.get("principalOrFirstMaker").toLowerCase();

            List<String> productionPlaces = response.jsonPath().getList("artObjects.productionPlaces");

            boolean containsKeywordInProductionPlaces = productionPlaces.contains(keyword);
            boolean containsKeywordInTitle = title.contains(keyword.toLowerCase());
            boolean containsKeywordInLongTitle = longTitle.contains(keyword.toLowerCase());
            boolean containsKeywordInPrincipalOrFirstMaker = principalOrFirstMaker.contains(keyword.toLowerCase());

            boolean resultContainsKeyword = containsKeywordInTitle || containsKeywordInLongTitle || containsKeywordInPrincipalOrFirstMaker || containsKeywordInProductionPlaces;

            if (resultContainsKeyword) {
                foundKeyword = true;
            } else {
                notFoundKeyword = true;
            }
            if (foundKeyword && notFoundKeyword) {
                break;
            }
        }
        assertThat(foundKeyword, is(true));
        assertThat(notFoundKeyword, is(true));
    }

}
