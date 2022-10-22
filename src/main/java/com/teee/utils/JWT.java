package com.teee.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JWT {

    private static String signature = "Xu ZhengTao";
    //**
    // * time 为1天
    // */
    private static long time = 1000*60*60*24;


    public static String jwtEncrypt(long uid, String role){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                // header
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg", "HS256")
                // payload
                .claim("uid", uid)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                //Signature
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }
    public static Claims parse(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body;
    }

    public static Long getUid(String token){
        Object uid = parse(token).get("uid");
        String str = Integer.toString((Integer) uid);
        return Long.valueOf(str);
    }
}
