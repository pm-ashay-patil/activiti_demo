package com.pubmatic.rest.service.api.identity;

/**
 * @author Ashay Patil
 *
 */
public class ChangePasswordRequest {

  protected String currentPassword;
  protected String newPassword;
  
  public String getCurrentPassword() {
    return currentPassword;
  }
  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }
  public String getNewPassword() {
    return newPassword;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
