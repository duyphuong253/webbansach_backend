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
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "the_loai")
@Data
public class TheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_the_loai")
    private int maTheLoai;
    @Column(name = "ten_the_loai", length = 256)
    private String tenTheLoai;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(name = "sach_danhsachtheloai", joinColumns = @JoinColumn(name = "ma_the_loai"), inverseJoinColumns = @JoinColumn(name = "ma_sach"))
    private List<Sach> danhSachQuyenSach;
}
