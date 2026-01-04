package vn.fun.webbansach_backend.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.fun.webbansach_backend.dao.NguoiDungRepository;
import vn.fun.webbansach_backend.dao.QuyenRepository;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.entity.Quyen;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private QuyenRepository quyenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(username);

        if (nguoiDung == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại!");
        }
        User user = new User(nguoiDung.getTenDangNhap(), nguoiDung.getMatKhau(),
                rolesToAuthorities(nguoiDung.getDanhSachQuyen()));
        return user;
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Quyen> quyens) {
        return quyens.stream().map(quyen -> new SimpleGrantedAuthority(quyen.getTenQuyen()))
                .collect(Collectors.toList());
    }

    @Override
    public NguoiDung findByUserName(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    }

    @Autowired
    public UserServiceImpl(NguoiDungRepository nguoiDungRepository, QuyenRepository quyenRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.quyenRepository = quyenRepository;
    }

}
