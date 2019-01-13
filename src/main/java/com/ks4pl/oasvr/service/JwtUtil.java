package com.ks4pl.oasvr.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

public class JwtUtil {
    private static Logger logger = LogManager.getLogger();
    static final String strKey = "3d8469faede7c7bac0587d56f52ac495f95e50e393eed34753ca5c4f35bcc037";
    public static String token(Integer uid){
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.MONTH, 1);

        SecretKey key = Keys.hmacShaKeyFor(strKey.getBytes());

        String jws = Jwts.builder()
                .setSubject(uid.toString())
                .setExpiration(exp.getTime())
                .setIssuedAt(new Date())
                .setIssuer("kingsun")
                .signWith(key)
                .compact();
        System.out.println(jws);
        return jws;
    }

    public static Boolean isValid(String token){
        SecretKey key = Keys.hmacShaKeyFor(strKey.getBytes());
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Boolean valid = false;
        if (jws != null){
            Date exp = jws.getBody().getExpiration();
            String issuer = jws.getBody().getIssuer();
            if (exp != null && exp.after(new Date()) &&
                issuer != null && issuer.equals("kingsun")) {
                    valid = true;
            }
        }
        else{
            System.out.println("jws null");
        }
        return valid;
    }

    public static String fetchSubject(String token){
        SecretKey key = Keys.hmacShaKeyFor(strKey.getBytes());
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        String sub = null;
        if (jws != null){
            sub = jws.getBody().getSubject();
        }
        return sub;

    }

    public static Claims fetchClaims(String token){
        SecretKey key = Keys.hmacShaKeyFor(strKey.getBytes());
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Claims claims = null;
        if (jws != null){
            claims = jws.getBody();
        }
        return claims;
    }
}
