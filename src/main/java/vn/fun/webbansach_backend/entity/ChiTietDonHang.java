package vn.fun.webbansach_backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "chi_tiet_don_hang")
@Data
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chi_tiet_don_hang")
    private long ChiTietDonHang;
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, })
    @JoinColumn(name = "ma_sach", nullable = false) // Xóa mã sách thì chi tiết đơn hàng đó sẽ không tồn tại
    private Sach sach;
    @Column(name = "so_luong")
    private int soLuong;
    @Column(name = "gia_ban")
    private double giaBan;
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, })
    @JoinColumn(name = "ma_don_hang", nullable = false)
    private DonHang donHang;

}
