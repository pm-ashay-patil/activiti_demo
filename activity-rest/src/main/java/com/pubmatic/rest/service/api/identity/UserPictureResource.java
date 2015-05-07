package com.pubmatic.rest.service.api.identity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ashay Patil
 */
@RestController
public class UserPictureResource extends org.activiti.rest.service.api.identity.UserPictureResource {
  
  @RequestMapping(value="/identity/profile/picture", method = RequestMethod.PUT)
  public void updateProfilePicture(HttpServletRequest request, HttpServletResponse response) {
    updateUserPicture(request.getRemoteUser(), request, response);
  }
}
