package com.pubmatic.rest.service.api.repository;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.repository.Model;
import org.activiti.rest.service.api.repository.BaseModelResource;
import org.activiti.rest.service.api.repository.ModelResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author Ashay Patil
 *
 */
@RestController
public class ModelSourcesResource extends BaseModelResource {
  
  @RequestMapping(value="/repository/models/{modelId}/sources", method = RequestMethod.PUT, produces = "application/json")
  protected ModelResponse setModelSources(@PathVariable String modelId, HttpServletRequest request) {
    Model model = getModelFromRequest(modelId);
    if (model != null) {
      
      if (request instanceof MultipartHttpServletRequest == false) {
        throw new ActivitiIllegalArgumentException("Multipart request is required");
      }
      
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      
      if (multipartRequest.getFileMap().size() == 0) {
        throw new ActivitiIllegalArgumentException("Multipart request with file content is required");
      }
      
      Iterator<MultipartFile> files = multipartRequest.getFileMap().values().iterator();
      MultipartFile editorSourceFile = null;
      MultipartFile editorSourceExtraFile = null;
      while(files.hasNext()){
        MultipartFile file = files.next();
        if("json".equalsIgnoreCase(file.getName()))
          editorSourceFile = file;
        else if("svg".equalsIgnoreCase(file.getName()))
          editorSourceExtraFile = file;
      }
      
      if(editorSourceFile == null && editorSourceExtraFile == null) {
        throw new ActivitiIllegalArgumentException("No file content was found in request body.");
      }
      
      try {
        if(editorSourceFile!=null)
          repositoryService.addModelEditorSource(modelId, editorSourceFile.getBytes());
        if(editorSourceExtraFile!=null)
          repositoryService.addModelEditorSourceExtra(modelId, editorSourceExtraFile.getBytes());
        
        String serverRootUrl = request.getRequestURL().toString();
        serverRootUrl = serverRootUrl.substring(0, serverRootUrl.indexOf("/repository/models/"));
        return restResponseFactory.createModelResponse(model, serverRootUrl);
        
      } catch (Exception e) {
        throw new ActivitiException("Error adding model editor source extra", e);
      }
    }
    return null;
  }
}
