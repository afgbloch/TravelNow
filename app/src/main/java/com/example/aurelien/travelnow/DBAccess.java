package com.example.aurelien.travelnow;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.builder.api.SignatureType;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


/**
 * Created by martin on 18/03/17.
 */

public class DBAccess extends AsyncTask<Void, Void, Void> {

    private static final String PROTECTED_RESOURCE_URL = "https://simulator-api.db.com/gw/dbapi/v1/cashAccounts";
    @Override
    protected Void doInBackground(Void... params) {


        String secretState = "active";

        try {
            final OAuth20Service service = new ServiceBuilder()
                    .apiKey("9a87904d-d5f9-45b3-94bb-623ec3b8ac6d")
                    .apiSecret("V3H8ESc75cinGJdFP0BqjU8dPaSvCSgCY0NMW0Eq6DPF-iZs0NMQEt3miqPM7nxKoCfUlhcKT2C7YpcyQx-Cig")
                    .build(new DeutschBankApi());

            final Scanner in = new Scanner(System.in, "UTF-8");

            System.out.println("=== DB's OAuth Workflow ===");
            System.out.println();

            // Obtain the Authorization URL
            System.out.println("Fetching the Authorization URL...");
            //pass access_type=offline to get refresh token
            final Map<String, String> additionalParams = new HashMap<>();
            additionalParams.put("access_type", "offline");
            //force to reget refresh token (if usera are asked not the first time)
            additionalParams.put("prompt", "consent");
            final String authorizationUrl = service.getAuthorizationUrl(additionalParams);
            System.out.println("Got the Authorization URL!");
            System.out.println("Now go and authorize ScribeJava here:");
            System.out.println(authorizationUrl);
            System.out.println("And paste the authorization code here");
            System.out.print(">>");
            final String code = in.nextLine();
            System.out.println();

            System.out.println("And paste the state from server here. We have set 'secretState'='" + secretState + "'.");
            System.out.print(">>");
            final String value = in.nextLine();
            if (secretState.equals(value)) {
                System.out.println("State value does match!");
            } else {
                System.out.println("Ooops, state value does not match!");
                System.out.println("Expected = " + secretState);
                System.out.println("Got      = " + value);
                System.out.println();
            }

            // Trade the Request Token and Verfier for the Access Token
            System.out.println("Trading the Request Token for an Access Token...");
            final OAuth2AccessToken accessToken = service.getAccessToken(code);
            System.out.println("Got the Access Token!");
            System.out.println("(If you're curious, it looks like this: " + accessToken
                    + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");

            System.out.println();

            // Now let's go and ask for a protected resource!
            System.out.println("Now we're going to access a protected resource...");
            final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
            service.signRequest(accessToken, request);
            final Response response = service.execute(request);
            System.out.println("Got it! Lets see what we found...");
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());

            System.out.println("Thats it man! Go and build something awesome with ScribeJava! :)");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
        //return toReturn;
    }

    private class DeutschBankApi extends DefaultApi20 {

        @Override
        public String getAccessTokenEndpoint() {
            return "https://simulator-api.db.com/gw/oidc/token";
        }

        @Override
        protected String getAuthorizationBaseUrl() {
            return "https://simulator-api.db.com/gw/oidc/authorize";
        }

        @Override
        public SignatureType getSignatureType() {
            return SignatureType.BEARER_URI_QUERY_PARAMETER;
        }
    }

}
