package vn.fun.webbansach_backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "hinh_anh")
@Data
public class HinhAnh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hinh_anh")
    private int maHinhAnh;
    @Column(name = "ten_hinh_anh", length = 256)
    private String tenHinhAnh;
    @Column(name = "icon")
    private Boolean icon;
    @Column(name = "duong_dan")
    private String duongDan;
    @Column(name = "du_lieu_lon", columnDefinition = "LONGTEXT")
    @Lob
    private String duLieuAnh;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinColumn(name = "ma_sach", nullable = false) // Xác định ảnh cho quyển sách nào, nếu sách không tồn tại thì không
                                                    // tồn tại ảnh
    private Sach sach;
}
