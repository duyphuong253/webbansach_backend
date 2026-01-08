package vn.fun.webbansach_backend.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.fun.webbansach_backend.dao.NguoiDungRepository;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.entity.Quyen;

@Service
public class UserServiceImpl implements UserService {

    private final NguoiDungRepository nguoiDungRepository;

    public UserServiceImpl(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        NguoiDung nguoiDung = nguoiDungRepository
                .findByTenDangNhap(username)
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại!"));

        return new User(
                nguoiDung.getTenDangNhap(),
                nguoiDung.getMatKhau(),
                rolesToAuthorities(nguoiDung.getDanhSachQuyen()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(
            Collection<Quyen> quyens) {

        return quyens.stream()
                .map(q -> new SimpleGrantedAuthority(q.getTenQuyen()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NguoiDung> findByUserName(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    }
}
