package vn.fun.webbansach_backend.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "hinh_thuc_thanh_toan")
@Data
public class HinhThucThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hinh_thuc_thanh_toan")
    private int maHinhThucThanhToan;
    @Column(name = "ten_hinh_thuc_thanh_toan")
    private String tenHinhThucThanhToan;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "chi_phi_thanh_toan")
    private double chiPhiThanhToan;
    @OneToMany(mappedBy = "hinhThucThanhToan", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE })
    private List<DonHang> danhSachDonHang;
}
