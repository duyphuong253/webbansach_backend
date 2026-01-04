package vn.fun.webbansach_backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import vn.fun.webbansach_backend.entity.NguoiDung;

public interface UserService extends UserDetailsService {
    public NguoiDung findByUserName(String tenDangNhap);
}
