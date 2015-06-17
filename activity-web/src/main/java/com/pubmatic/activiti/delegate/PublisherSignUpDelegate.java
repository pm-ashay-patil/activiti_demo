package com.pubmatic.activiti.delegate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class PublisherSignUpDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        boolean responseResult = false;

        String firstName = (String) execution.getVariable("firstName");
        String lastName = (String) execution.getVariable("lastName");
        String emailAddress = (String) execution.getVariable("emailAddress");
        String phone = (String) execution.getVariable("phone");
        String password = (String) execution.getVariable("password");
        String streetOne = (String) execution.getVariable("streetOne");
        String city = (String) execution.getVariable("city");
        String state = (String) execution.getVariable("state");
        String zip = (String) execution.getVariable("zip");
        String country = (String) execution.getVariable("country");
        String companyName = (String) execution.getVariable("companyName");
        String siteUrl = (String) execution.getVariable("siteUrl");
        String result = "";

        try {
            // Creating HttpClient object for making REST call to casturm
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://localhost:8080/inventory/publisher/create");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("PubToken", "adminuser");

            post.setEntity(new StringEntity("{" +
            		"\"firstName\":\"" + firstName + "\"" +
                    ",\"lastName\":\"" + lastName +  "\"" +
                    ",\"emailAddress\":\"" + emailAddress +  "\"" +
                    ",\"phone\":\"" + phone +  "\"" +
                    ",\"password\":\"" + password +  "\"" +
                    ",\"streetOne\":\"" + streetOne +  "\"" +
                    ",\"streetTwo\":\"" + "" +  "\"" +
                    ",\"streetThree\":\"" + "" +  "\"" +
                    ",\"city\":\"" + city +  "\"" +
                    ",\"state\":\"" + state +  "\"" +
                    ",\"zip\":\"" + zip +  "\"" +
                    ",\"country\":\"" + country +  "\"" +
                    ",\"companyName\":\"" + companyName +  "\"" +
                    ",\"siteUrl\":\"" + siteUrl +  "\"" +
                    ",\"vertical\":\"" + "Technology" +  "\"" +
                    ",\"microvertical\":\"" + "Not Applicable" +  "\"" +
                    ",\"impressions\":\"" + "1000" +  "\"" +
                    ",\"applyAdnetworks\":\"" + "true" +  "\"" +
                    ",\"currencyId\":\"" + "1" +  "\"" +
                    "}",ContentType.create("application/json")));

            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == HttpStatus.SC_OK){
                responseResult = true;
            }
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
               System.out.println(line);  
               result += line;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        execution.setVariable("responseResult", responseResult);
        execution.setVariable("result", result);
   }
}