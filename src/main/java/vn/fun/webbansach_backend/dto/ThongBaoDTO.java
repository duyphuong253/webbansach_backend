package vn.fun.webbansach_backend.dto;

import lombok.Data;

@Data
public class ThongBaoDTO {
    private String message;

    public ThongBaoDTO(String message) {
        this.message = message;
    }
}
