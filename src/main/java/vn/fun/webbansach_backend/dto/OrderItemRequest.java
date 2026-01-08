package vn.fun.webbansach_backend.dto;

import lombok.Data;

// Đây là dữ liệu từ frontend gửi lên khi đặt hàng.
// Mục đích: backend biết người dùng muốn mua gì, số lượng bao nhiêu, địa chỉ, hình thức thanh toán/giao hàng.
// Chỉ chứa thông tin đặt hàng, chưa có ID đơn hàng vì đơn hàng chưa được tạo
@Data
public class OrderItemRequest {
    private Integer maSach;
    private Integer soLuong;
}
