// Repository.java
package vn.fun.webbansach_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fun.webbansach_backend.entity.DonHang;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

}