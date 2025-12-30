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
@Data
@Table(name = "sach")
public class Sach {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ma_sach")
        private int maSach;
        @Column(name = "ten_sach", length = 256)
        private String tenSach;
        @Column(name = "ten_tac_gia", length = 512)
        private String tenTacGia;
        @Column(name = "isbn", length = 256)
        private String ISBN;
        @Column(name = "mo_ta", columnDefinition = "text")
        private String moTa;
        @Column(name = "gia_niem_yet")
        private double giaNiemYet;

        @Column(name = "gia_ban")
        private double giaBan;
        @Column(name = "so_luong")
        private int soLuong;
        @Column(name = "trung_binh_xep_hang")
        private double trungBinhXepHang;

        @ManyToMany(fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH
        })
        @JoinTable(name = "sach_theloai", joinColumns = @JoinColumn(name = "ma_sach"), inverseJoinColumns = @JoinColumn(name = "ma_the_loai"))
        List<TheLoai> danhSachTheLoai;

        @OneToMany(mappedBy = "sach", fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE })
        List<HinhAnh> danhSachHinhAnh;

        @OneToMany(mappedBy = "sach", fetch = FetchType.LAZY, cascade = {
                        CascadeType.ALL })
        List<SuDanhGia> danhSachSuDanhGia; // Khi sách bị xóa thì tất cả sự đánh giả cũng bị xóa theo

        @OneToMany(mappedBy = "sach", fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH })
        List<ChiTietDonHang> danhSachChiTietDonHang; // Xóa sách thì không nên xóa chi tiết đơn hàng vì sẽ ảnh hưởng tới
                                                     // thống kê doanh thu

        @OneToMany(mappedBy = "sach", fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE })
        List<SachYeuThich> danhSachYeuThich;
}