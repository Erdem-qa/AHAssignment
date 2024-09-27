package org.ahassignement.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.ahassignement.Utils.ConfigurationReader;


public class BaseUrl {
    protected Response response;
    protected Response secondPageResponse;
    protected String objectNumber;
    protected String api_key = ConfigurationReader.get("api-key");
    protected static void baseUrlEn() {
        RestAssured.baseURI = ConfigurationReader.get("baseUrlEn");
    }
    protected static void baseUrlNl() {
        RestAssured.baseURI = ConfigurationReader.get("baseUrlNl");
    }
}
