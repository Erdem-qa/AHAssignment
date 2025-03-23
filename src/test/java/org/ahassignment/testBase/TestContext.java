package org.ahassignment.testBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.ahassignment.utils.ConfigurationReader;
import org.ahassignment.utils.DotEnvToSystemEnv;


public class TestContext {
    protected static Response response;
    protected static Response secondPageResponse;
    protected static String involvedMaker;
    protected static String objectNumber;
    protected static String userSetId;
    protected static String searchKeyword;
    protected static int pageSize;


    protected String api_key = DotEnvToSystemEnv.getEnvValue("API_KEY");
    protected static void baseUrlEn() {
        RestAssured.baseURI = ConfigurationReader.get("baseUrlEn");
    }
    protected static void baseUrlNl() {
        RestAssured.baseURI = ConfigurationReader.get("baseUrlNl");
    }
}
