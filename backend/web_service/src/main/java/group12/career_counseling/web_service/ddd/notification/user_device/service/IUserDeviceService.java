package group12.career_counseling.web_service.ddd.notification.user_device.service;

import group12.career_counseling.web_service.ddd.notification.user_device.UserDevice;

import java.util.List;

public interface IUserDeviceService {
    public void AddUserDevice(String userId,String role, String token);
    public void RemoveUserDevice(String userId,String role, String token);
    public List<UserDevice> getUserDevices(String userId);
}
