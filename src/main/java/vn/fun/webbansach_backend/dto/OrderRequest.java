package vn.fun.webbansach_backend.dto;

import java.util.List;

import lombok.Data;
import vn.fun.webbansach_backend.entity.HinhThucGiaoHang;
import vn.fun.webbansach_backend.entity.HinhThucThanhToan;

// Đây là dữ liệu từ frontend gửi lên khi đặt hàng.
// Mục đích: backend biết người dùng muốn mua gì, số lượng bao nhiêu, địa chỉ, hình thức thanh toán/giao hàng.
// Chỉ chứa thông tin đặt hàng, chưa có ID đơn hàng vì đơn hàng chưa được tạo
@Data
public class OrderRequest {
    private List<OrderItemRequest> items;

    private String diaChiMuaHang;
    private String diaChiNhanHang;

    private int maHinhThucThanhToan;
    private int maHinhThucGiaoHang;

}
