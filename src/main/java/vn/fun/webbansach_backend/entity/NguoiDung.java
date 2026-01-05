package vn.fun.webbansach_backend.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDung {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ma_nguoi_dung")
        private int maNguoiDung;
        @Column(name = "ho_dem", length = 256)
        private String hoDem;
        @Column(name = "ten", length = 256)
        private String ten;
        @Column(name = "ten_dang_nhap", length = 256)
        private String tenDangNhap;
        @Column(name = "mat_khau", length = 512)
        private String matKhau;
        @Column(name = "gioi_tinh")
        private char gioiTinh;
        @Column(name = "email")
        private String email;
        @Column(name = "do_dien_thoai")
        private String soDienThoai;
        @Column(name = "dia_chi_mua_hang")
        private String diaChiMuaHang;
        @Column(name = "dia_chi_giao_hang")
        private String diaChiGiaoHang;
        @Column(name = "da_kich_hoat")
        private boolean daKichHoat;
        @Column(name = "ma_kich_hoat")
        private String maKichHoat;

        @OneToMany(mappedBy = "nguoiDung", fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH, })
        private List<SuDanhGia> danhSachSuDanhGia;
        @OneToMany(mappedBy = "nguoiDung", fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH, })
        private List<SachYeuThich> danSachYeuThich;
        @ManyToMany(fetch = FetchType.EAGER, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH
        })
        @JoinTable(name = "nguoidung_quyen", joinColumns = @JoinColumn(name = "ma_nguoi_dung"), inverseJoinColumns = @JoinColumn(name = "ma_quyen"))
        private List<Quyen> danhSachQuyen;
        @OneToMany(mappedBy = "nguoiDung", fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH, })
        private List<DonHang> danhSachDonHang;
}
