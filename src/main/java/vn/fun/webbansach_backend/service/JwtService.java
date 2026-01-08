package vn.fun.webbansach_backend.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import vn.fun.webbansach_backend.entity.NguoiDung;
import vn.fun.webbansach_backend.entity.Quyen;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {

    private static final String SECRET = "NDEyNzk0MTJGOTQxMjRBMzEyMzkwMTJQMzAxMjQxMjQxMkU0MTI0NTEyNTdZMjM1MTMyNQ==";

    @Autowired
    private UserService userService;

    // ================== GENERATE TOKEN ==================
    public String generateToken(String tenDangNhap) {

        Map<String, Object> claims = new HashMap<>();

        Optional<NguoiDung> optionalNguoiDung = userService.findByUserName(tenDangNhap);

        boolean isAdmin = false;
        boolean isStaff = false;
        boolean isUser = false;

        if (optionalNguoiDung.isPresent()) {
            NguoiDung nguoiDung = optionalNguoiDung.get();

            if (nguoiDung.getDanhSachQuyen() != null) {
                for (Quyen q : nguoiDung.getDanhSachQuyen()) {
                    switch (q.getTenQuyen()) {
                        case "ADMIN" -> isAdmin = true;
                        case "STAFF" -> isStaff = true;
                        case "USER" -> isUser = true;
                    }
                }
            }
        }

        claims.put("isAdmin", isAdmin);
        claims.put("isStaff", isStaff);
        claims.put("isUser", isUser);

        return createToken(claims, tenDangNhap);
    }

    // ================== CREATE TOKEN ==================
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 phút
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // ✅ API mới
                .compact();
    }

    // ================== KEY ==================
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ================== PARSE ==================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ================== VALIDATE ==================
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}
