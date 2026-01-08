package vn.fun.webbansach_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.fun.webbansach_backend.dao.TheLoaiRepository;
import vn.fun.webbansach_backend.entity.TheLoai;

@RestController
@RequestMapping("/the-loai")
@CrossOrigin(origins = "http://localhost:3000")
public class TheLoaiController {

    @Autowired
    private TheLoaiRepository theLoaiRepository;

    @GetMapping
    public List<TheLoai> layTatCaTheLoai() {
        return theLoaiRepository.findAll();
    }
}
