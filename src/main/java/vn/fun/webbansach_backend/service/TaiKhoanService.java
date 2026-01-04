package vn.fun.webbansach_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.fun.webbansach_backend.dao.NguoiDungRepository;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.entity.ThongBao;

@Service
public class TaiKhoanService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public ResponseEntity<?> dangKyNguoiDung(NguoiDung nguoiDung) {
        // Kiểm tra tên đăng nhập tồn tại chưa
        if (nguoiDungRepository.existsByTenDangNhap(nguoiDung.getTenDangNhap())) {
            return ResponseEntity.badRequest().body(new ThongBao("Tên đăng nhập đã tồn tại!"));
        }

        // Kiểm tra email tồn tại chưa
        if (nguoiDungRepository.existsByEmail(nguoiDung.getEmail())) {
            return ResponseEntity.badRequest().body(new ThongBao("Email đã tồn tại!"));
        }

        // Lưu người dùng vào DB
        NguoiDung nguoiDung_daDangKy = nguoiDungRepository.save(nguoiDung);
        return ResponseEntity.ok("Đăng ký thành công!");
    }
}
