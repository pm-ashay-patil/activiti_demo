package com.pubmatic.rest.service.api.runtime.task;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ashay Patil
 */
@RestController
public class TaskAttachmentContentResource extends org.activiti.rest.service.api.runtime.task.TaskAttachmentContentResource {
  
  @RequestMapping(value="/runtime/tasks/{taskId}/attachments/{attachmentId}/content/{attachmentName}", method = RequestMethod.GET, produces="application/json")
  public @ResponseBody byte[] getNamedAttachmentContent(@PathVariable("taskId") String taskId, 
      @PathVariable("attachmentId") String attachmentId, HttpServletResponse response) {
    return getAttachmentContent(taskId, attachmentId, response);
  }
}
