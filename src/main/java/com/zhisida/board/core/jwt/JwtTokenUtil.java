
package com.zhisida.board.core.jwt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.*;
import com.zhisida.board.core.context.constant.ConstantContextHolder;
import com.zhisida.board.core.util.CryptogramUtil;
import java.util.Date;

/**
 * JwtToken工具类
 *
 * @author young-pastor
 */
public class JwtTokenUtil {

    /**
     * 生成token
     *
     * @author young-pastor
     */
    public static String generateToken(JwtPayLoad jwtPayLoad) {

        DateTime expirationDate = DateUtil.offsetSecond(new Date(), Convert.toInt(ConstantContextHolder.getTokenExpireSec()));
        String jwtToken =  Jwts.builder()
                .setClaims(BeanUtil.beanToMap(jwtPayLoad))
                .setSubject(jwtPayLoad.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, ConstantContextHolder.getJwtSecret())
                .compact();
        if (ConstantContextHolder.getCryptogramConfigs().getTokenEncDec()) {
            // 加密token
            return CryptogramUtil.doEncrypt(jwtToken);
        }
        return jwtToken;
    }

    /**
     * 根据token获取Claims
     *
     * @author young-pastor
     */
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(ConstantContextHolder.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取JwtPayLoad部分
     *
     * @author young-pastor
     */
    public static JwtPayLoad getJwtPayLoad(String token) {
        Claims claims = getClaimsFromToken(token);
        return BeanUtil.mapToBean(claims, JwtPayLoad.class, false);
    }

    /**
     * 校验token是否正确
     *
     * @author young-pastor
     */
    public static Boolean checkToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException jwtException) {
            return false;
        }
    }

    /**
     * 校验token是否失效
     *
     * @author young-pastor
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            final Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }
}
