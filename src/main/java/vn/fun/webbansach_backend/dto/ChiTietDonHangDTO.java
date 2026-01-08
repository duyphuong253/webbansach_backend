package vn.fun.webbansach_backend.dto;

// Đây là dữ liệu backend trả về sau khi tạo đơn hoặc khi lấy đơn đã tồn tại.
// Mục đích: frontend hiển thị thông tin đơn hàng, bao gồm ID đơn, ngày tạo, tổng tiền, chi tiết từng món.
// Có thêm các trường maDonHang, tongTien, tongTien từng chi tiết, mà OrderRequest không cần.
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHangDTO {
    private int maSach;
    private String tenSach;
    private double giaBan;
    private int soLuong;
    private double tongTien;
}
