package vn.fun.webbansach_backend.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.fun.webbansach_backend.dao.NguoiDungRepository;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.entity.ThongBao;

@Service
public class TaiKhoanService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> dangKyNguoiDung(NguoiDung nguoiDung) {

        // Kiểm tra tên đăng nhập tồn tại chưa
        if (nguoiDungRepository.existsByTenDangNhap(nguoiDung.getTenDangNhap())) {
            return ResponseEntity.badRequest().body(new ThongBao("Tên đăng nhập đã tồn tại!"));
        }

        // Kiểm tra email tồn tại chưa
        if (nguoiDungRepository.existsByEmail(nguoiDung.getEmail())) {
            return ResponseEntity.badRequest().body(new ThongBao("Email đã tồn tại!"));
        }

        // Mã hóa mật khẩu
        String endcryptPassword = passwordEncoder.encode(nguoiDung.getMatKhau());
        nguoiDung.setMatKhau(endcryptPassword);

        // Gán và gửi thông tin kích hoạt tài khoản
        nguoiDung.setMaKichHoat(taoMaKichHoat());
        nguoiDung.setDaKichHoat(false);

        // Lưu người dùng vào DB
        NguoiDung nguoiDung_daDangKy = nguoiDungRepository.save(nguoiDung);

        // Gửi email cho người dùng
        guiEmailKichHoat(
                nguoiDung_daDangKy.getEmail(),
                nguoiDung_daDangKy.getMaKichHoat());

        return ResponseEntity.ok("Đăng ký thành công!");
    }

    private String taoMaKichHoat() {
        int code = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    private void guiEmailKichHoat(String email, String maKichHoat) {

        String subject = "Kích hoạt tài khoản WebBanSach";

        String text = """
                    <h2>Kích hoạt tài khoản</h2>
                    <p>Email: <b>%s</b></p>
                    <p>Mã kích hoạt:</p>
                    <h3>%s</h3>
                    <p>Vui lòng nhập mã này để kích hoạt tài khoản.</p>
                """.formatted(email, maKichHoat);
        text += "<br/> Click vào đường link để kích hoạt tà khoản: ";
        String url = "http://localhost:3000/kich-hoat/"
                + URLEncoder.encode(email, StandardCharsets.UTF_8)
                + "/" + maKichHoat;

        text += "<br/> <a href=" + url + ">" + url + "</a> ";
        emailService.sendMessage(
                "phuongdang.020503@gmail.com",
                email,
                subject,
                text);
    }

    public ResponseEntity<?> kichHoatTaiKhoan(String email, String maKichHoat) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email);

        if (nguoiDung == null) {
            return ResponseEntity.badRequest().body(new ThongBao("Người dùng không tồn tại!"));

        }

        if (nguoiDung.isDaKichHoat()) {
            return ResponseEntity.badRequest().body(new ThongBao("Tài khoản đã được kích hoạt!"));

        }

        if (maKichHoat.equals(nguoiDung.getMaKichHoat())) {
            nguoiDung.setDaKichHoat(true);
            nguoiDungRepository.save(nguoiDung);
            return ResponseEntity.ok("Kích hoạt tài khoản thành công!");
        } else {
            return ResponseEntity.badRequest().body(new ThongBao("Mã kích hoạt không chính xác!"));
        }
    }
}
