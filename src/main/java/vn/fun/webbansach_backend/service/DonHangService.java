package vn.fun.webbansach_backend.service;

import vn.fun.webbansach_backend.dto.OrderRequest;
import vn.fun.webbansach_backend.entity.DonHang;
import vn.fun.webbansach_backend.entity.NguoiDung;

public interface DonHangService {
    DonHang taoDonHang(NguoiDung nguoiDung, OrderRequest request);

}
