package vn.fun.webbansach_backend.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import vn.fun.webbansach_backend.entity.Sach;

@RepositoryRestResource(path = "sach")
public interface SachRepository extends JpaRepository<Sach, Integer> {
    // Thêm endpoint search theo tên sách trong spring boot
    Page<Sach> findByTenSachContaining(@RequestParam("tenSach") String tenSach, Pageable pageable);

}
