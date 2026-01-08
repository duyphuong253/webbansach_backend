package vn.fun.webbansach_backend.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "don_hang")
@Data
public class DonHang {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ma_don_hang")
        private int maDonHang;
        @Column(name = "ngay_tao")
        private Date ngayTao;
        @Column(name = "dia_chi_mua_hang", length = 512)
        private String diaChiMuaHang;
        @Column(name = "dia_chi_nhan_hang", length = 512)
        private String diaChiNhanHang;
        @Column(name = "tong_tien")
        private double tongTien;
        @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<ChiTietDonHang> danhSachChiTietDonHang;

        @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
        @JoinColumn(name = "ma_nguoi_dung", nullable = false)
        private NguoiDung nguoiDung;

        @Column(name = "chi_phi_giao_hang")
        private double chiPhiGiaoHang;
        @Column(name = "chi_phi_thanh_toan")
        private double chiPhiThanhToan;
        @ManyToOne(cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH })
        @JoinColumn(name = "ma_hinh_thuc_thanh_toan")
        private HinhThucThanhToan hinhThucThanhToan;
        @ManyToOne(cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH })
        @JoinColumn(name = "ma_hinh_thuc_giao_hang")
        private HinhThucGiaoHang hinhThucGiaoHang;
}
