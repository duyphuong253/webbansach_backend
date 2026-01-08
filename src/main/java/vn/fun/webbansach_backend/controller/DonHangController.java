package vn.fun.webbansach_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import vn.fun.webbansach_backend.dao.DonHangRepository;
import vn.fun.webbansach_backend.dao.NguoiDungRepository;
import vn.fun.webbansach_backend.dto.ChiTietDonHangDTO;
import vn.fun.webbansach_backend.dto.DonHangDTO;
import vn.fun.webbansach_backend.dto.OrderRequest;
import vn.fun.webbansach_backend.dto.ThongBaoDTO;
import vn.fun.webbansach_backend.entity.DonHang;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.service.DonHangService;

@RestController
@RequestMapping("/don-hang")
public class DonHangController {

        @Autowired
        private DonHangService donHangService;

        @Autowired
        private DonHangRepository donHangRepository;

        @Autowired
        private NguoiDungRepository nguoiDungRepository;

        @PostMapping
        public ResponseEntity<?> taoDonHang(@RequestBody OrderRequest request) {

                String username = SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getName();

                NguoiDung nguoiDung = nguoiDungRepository
                                .findByTenDangNhap(username)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

                DonHang donHang = donHangService.taoDonHang(nguoiDung, request);

                return ResponseEntity.ok(
                                Map.of("maDonHang", donHang.getMaDonHang()));
        }

        @GetMapping("/{maDonHang}")
        public ResponseEntity<?> layDonHang(@PathVariable int maDonHang) {

                String username = SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getName();

                DonHang donHang = donHangRepository.findById(maDonHang)
                                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

                // Kiểm tra quyền sở hữu
                if (!donHang.getNguoiDung().getTenDangNhap().equals(username)) {
                        return ResponseEntity.status(403)
                                        .body(new ThongBaoDTO("Bạn không có quyền xem đơn hàng này"));
                }

                return ResponseEntity.ok(convertToDTO(donHang));
        }

        private DonHangDTO convertToDTO(DonHang donHang) {

                List<ChiTietDonHangDTO> chiTietDTOs = donHang.getDanhSachChiTietDonHang().stream()
                                .map(ct -> new ChiTietDonHangDTO(
                                                ct.getSach().getMaSach(),
                                                ct.getSach().getTenSach(),
                                                ct.getGiaBan(),
                                                ct.getSoLuong(),
                                                ct.getGiaBan() * ct.getSoLuong()))
                                .toList();

                DonHangDTO dto = new DonHangDTO();
                dto.setMaDonHang(donHang.getMaDonHang());
                dto.setNgayTao(donHang.getNgayTao().toString());
                dto.setDiaChiMuaHang(donHang.getDiaChiMuaHang());
                dto.setDiaChiNhanHang(donHang.getDiaChiNhanHang());
                dto.setTongTien(donHang.getTongTien());
                dto.setChiPhiGiaoHang(donHang.getChiPhiGiaoHang());
                dto.setDanhSachChiTietDonHang(chiTietDTOs);

                return dto;
        }
}
