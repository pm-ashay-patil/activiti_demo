package com.pubmatic.rest.service.api.repository;

import java.util.Iterator;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.rest.service.api.repository.DeploymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author Ashay Patil
 */
@RestController
public class DeploymentCollectionResource extends org.activiti.rest.service.api.repository.DeploymentCollectionResource {
  
  public DeploymentResponse uploadDeployment(String tenantId, HttpServletRequest request, HttpServletResponse response) {
    
    if (request instanceof MultipartHttpServletRequest == false) {
      throw new ActivitiIllegalArgumentException("Multipart request is required");
    }
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    
    if (multipartRequest.getFileMap().size() == 0) {
      throw new ActivitiIllegalArgumentException("Multipart request with file content is required");
    }
    
    Iterator<MultipartFile> files = multipartRequest.getFileMap().values().iterator();
    
    MultipartFile file = null;
    MultipartFile diagramFile = null;
    while(files.hasNext()){
      MultipartFile currentFile = files.next();
      if("diagram".equalsIgnoreCase(currentFile.getName()))
        diagramFile = currentFile;
      else
        file = currentFile;
    }
    
    try {
      DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
      String fileName = file.getOriginalFilename();
      if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
        deploymentBuilder.addInputStream(fileName, file.getInputStream());
        if(diagramFile!=null)
          deploymentBuilder.addInputStream(diagramFile.getOriginalFilename(), diagramFile.getInputStream());
      } else if (fileName.toLowerCase().endsWith(".bar") || fileName.toLowerCase().endsWith(".zip")) {
        deploymentBuilder.addZipInputStream(new ZipInputStream(file.getInputStream()));
      } else {
        throw new ActivitiIllegalArgumentException("File must be of type .bpmn20.xml, .bpmn, .bar or .zip");
      }
      deploymentBuilder.name(fileName);
      
      if(tenantId != null) {
        deploymentBuilder.tenantId(tenantId);
      }
      
      if(multipartRequest.getParameter("category")!=null){
        deploymentBuilder.category(multipartRequest.getParameter("category"));
      }
      
      Deployment deployment = deploymentBuilder.deploy();
      
      response.setStatus(HttpStatus.CREATED.value());
      
      return restResponseFactory.createDeploymentResponse(deployment, request.getRequestURL().toString().replace("/repository/deployments", ""));
      
    } catch (Exception e) {
      if (e instanceof ActivitiException) {
        throw (ActivitiException) e;
      }
      throw new ActivitiException(e.getMessage(), e);
    }
  }
}
