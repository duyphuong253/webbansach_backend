// Repository.java
package vn.fun.webbansach_backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.fun.webbansach_backend.entity.DonHang;
import vn.fun.webbansach_backend.entity.NguoiDung;

@RepositoryRestResource(path = "don-hang")
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

    List<DonHang> findByNguoiDung(NguoiDung user);

}