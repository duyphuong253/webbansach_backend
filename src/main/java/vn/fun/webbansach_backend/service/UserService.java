package vn.fun.webbansach_backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import vn.fun.webbansach_backend.entity.NguoiDung;

public interface UserService extends UserDetailsService {
    Optional<NguoiDung> findByUserName(String tenDangNhap);
}
