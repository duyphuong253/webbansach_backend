package vn.fun.webbansach_backend.dto;

import java.util.List;
// Đây là dữ liệu backend trả về sau khi tạo đơn hoặc khi lấy đơn đã tồn tại.
// Mục đích: frontend hiển thị thông tin đơn hàng, bao gồm ID đơn, ngày tạo, tổng tiền, chi tiết từng món.
// Có thêm các trường maDonHang, tongTien, tongTien từng chi tiết, mà OrderRequest không cần.
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangDTO {
    private int maDonHang;
    private String ngayTao;
    private String diaChiMuaHang;
    private String diaChiNhanHang;
    private double tongTien;
    private double chiPhiGiaoHang;
    private List<ChiTietDonHangDTO> danhSachChiTietDonHang;
}
