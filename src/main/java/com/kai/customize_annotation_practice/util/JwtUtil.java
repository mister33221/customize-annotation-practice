package com.kai.customize_annotation_practice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    /*
    從 application.properties 中取得 secret 和 expiration 的值，
     */
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    /*
    setClaims(Map<String, Object> claims)
    通常 claims 會存放一些自定義的資訊，例如我們可以存放用戶的角色、權限等等，
    setId(String jti): 這是 JWT 的 ID，通常用來作為一次性 token 的唯一標識符。
    setSubject(String sub):
    設定 JWT 的 subject，也就是這個 JWT 的主題，通常是指用戶的唯一識別符，這邊我們用 username 來代表
    setIssuedAt(Date iat): 設定 JWT 的發行時間
    setIssuer(String iss): 設定 JWT 的發行者
    setExpiration(Date exp): 設定 JWT 的過期時間
    signWith(SignatureAlgorithm alg, byte[] secretKey): 設定 JWT 的簽名算法和密鑰
    compact(): 生成 JWT 字串
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

/*
在 parseClaimsJws 方法中，驗證了以下內容
ExpiredJwtException: JWT 是否過期
UnsupportedJwtException: JWT 格式是否正確
MalformedJwtException: JWT 是否被篡改
SignatureException: JWT 簽名是否正確，也就是 Secret 是否正確
IllegalArgumentException: JWT 是否為空
 */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
