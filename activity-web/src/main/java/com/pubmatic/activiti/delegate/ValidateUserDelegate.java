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

public class ValidateUserDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        boolean responseResult = false;

        String username = (String) execution.getVariable("username");
        String password = (String) execution.getVariable("password");

        try {
            // Creating HttpClient object for making REST call to casturm
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://localhost:8080/castrum/login");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("PubToken", "adminuser");

            post.setEntity(new StringEntity("{\"userName\":\"" + username + "\",\"password\":\"" + password + "\"}",
                    ContentType.create("application/json")));

            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == HttpStatus.SC_OK){
                responseResult = true;
            }
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
               System.out.println(line);    
            }
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        execution.setVariable("responseResult", responseResult);
    }
}