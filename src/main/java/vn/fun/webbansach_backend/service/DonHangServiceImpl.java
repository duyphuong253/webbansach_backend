package vn.fun.webbansach_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.fun.webbansach_backend.dao.DonHangRepository;
import vn.fun.webbansach_backend.dao.HinhThucGiaoHangRepository;
import vn.fun.webbansach_backend.dao.HinhThucThanhToanRepository;
import vn.fun.webbansach_backend.dao.NguoiDungRepository;
import vn.fun.webbansach_backend.dao.SachRepository;
import vn.fun.webbansach_backend.dto.OrderItemRequest;
import vn.fun.webbansach_backend.dto.OrderRequest;
import vn.fun.webbansach_backend.entity.ChiTietDonHang;
import vn.fun.webbansach_backend.entity.DonHang;
import vn.fun.webbansach_backend.entity.HinhThucGiaoHang;
import vn.fun.webbansach_backend.entity.HinhThucThanhToan;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.entity.Sach;

@Service
@Transactional
public class DonHangServiceImpl implements DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private SachRepository sachRepository;

    @Autowired
    private HinhThucGiaoHangRepository hinhThucGiaoHangRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Override
    public DonHang taoDonHang(NguoiDung nguoiDung, OrderRequest request) {
        // Lấy hình thức giao hàng từ DB (managed)
        HinhThucGiaoHang hinhThucGH = hinhThucGiaoHangRepository
                .findById(request.getMaHinhThucGiaoHang())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy HTGH"));
        // Lấy hình thức thanh toán từ DB (managed)
        HinhThucThanhToan hinhThucTT = hinhThucThanhToanRepository
                .findById(request.getMaHinhThucThanhToan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy HTTT"));
        // Tạo DonHang
        DonHang donHang = new DonHang();
        donHang.setNguoiDung(nguoiDung);
        donHang.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
        donHang.setDiaChiMuaHang(request.getDiaChiMuaHang());
        donHang.setDiaChiNhanHang(request.getDiaChiNhanHang());
        donHang.setHinhThucGiaoHang(hinhThucGH);
        donHang.setHinhThucThanhToan(hinhThucTT);

        // Khởi tạo danh sách chi tiết
        List<ChiTietDonHang> chiTietList = new ArrayList<>();
        double tongTien = 0;

        for (OrderItemRequest item : request.getItems()) {
            Sach sach = sachRepository.findById(item.getMaSach())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với id: " + item.getMaSach()));

            if (item.getSoLuong() > sach.getSoLuong()) {
                throw new RuntimeException("Số lượng sách '" + sach.getTenSach() + "' không đủ");
            }

            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setSach(sach);
            chiTiet.setSoLuong(item.getSoLuong());
            chiTiet.setGiaBan(sach.getGiaBan());
            chiTiet.setDonHang(donHang);

            chiTietList.add(chiTiet);

            tongTien += sach.getGiaBan() * item.getSoLuong();

            sach.setSoLuong(sach.getSoLuong() - item.getSoLuong());
            sachRepository.save(sach);
        }
        double chiPhiGiaoHang = hinhThucGH.getMaHinhThucGiaoHang() == 1 ? 15000 : 30000;

        donHang.setChiPhiGiaoHang(chiPhiGiaoHang);
        donHang.setChiPhiThanhToan(0);

        donHang.setDanhSachChiTietDonHang(chiTietList);
        donHang.setTongTien(tongTien + chiPhiGiaoHang);

        return donHangRepository.save(donHang);
    }
}
