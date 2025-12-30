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
@Table(name = "hinh_thuc_giao_hang")
@Data
public class HinhThucGiaoHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hinh_thuc_giao_hang")
    private int maHinhThucGiaoHang;
    @Column(name = "ten_hinh_thuc_giao_hang")
    private String tenHinhThucGiaoHang;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "chi_phi_giao_hang")
    private double chiPhiGiaoHang;
    @OneToMany(mappedBy = "hinhThucGiaoHang", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE })
    private List<DonHang> danhSachDonHang;
}
