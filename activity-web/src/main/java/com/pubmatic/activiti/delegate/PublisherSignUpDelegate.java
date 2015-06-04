package com.pubmatic.activiti.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class PublisherSignUpDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        boolean responseResult = false;

        String publisherName = (String) execution.getVariable("publisherName");
        String publisherSite = (String) execution.getVariable("publisherSite");
        String publisherEmail = (String) execution.getVariable("publisherEmail");
        int publisherId = 2000;

        System.out.println("Created Publisher Account with Following Details ");
        System.out.println("--------------------------------------------------");
        System.out.println("PUBLISHER ID : "+publisherId);
        System.out.println("PUBLISHER NAME : "+publisherName);
        System.out.println("PUBLISHER EMAIL : "+publisherSite);
        System.out.println("PUBLISHER SITE : "+publisherEmail);
        System.out.println("--------------------------------------------------");
        
        execution.setVariable("responseResult", true);
        execution.setVariable("publisherId", publisherId);
        execution.setVariable("publisherName", publisherName);
        execution.setVariable("publisherSite", publisherSite);
        execution.setVariable("publisherEmail", publisherEmail);
    }
}