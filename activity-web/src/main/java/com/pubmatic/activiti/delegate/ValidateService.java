package com.pubmatic.activiti.delegate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


public class ValidateService implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws ClientProtocolException, IOException {

		String responseResult="false";
		//call some web Service and get the response in some local variable
		
		//here i assume response from the web service is false.
		HttpClient httpClient =HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet("http://www.google.com");
		getRequest.addHeader("accept", "application/json");
 
		HttpResponse response = httpClient.execute(getRequest);
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		System.out.println("============Output:============");

		// Simply iterate through XML response and show on console.
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		if (response.getStatusLine().getStatusCode() != 200) {
			responseResult="false";
		}
		else
			responseResult="true";
	
		
		System.out.println("in execute method");
		execution.setVariable("responseResult", responseResult); //here i am setting this "responseResult" variable which will make it 
		 //to be used for conditiin checking

		//execution.setVariable("responseResult", "success");
	}
}