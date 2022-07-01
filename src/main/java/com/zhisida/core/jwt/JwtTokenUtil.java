
package com.zhisida.core.jwt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.jsonwebtoken.*;
import com.zhisida.system.cache.SysConfigCache;
import com.zhisida.core.util.CryptogramUtil;
import java.util.Date;

/**
 * JwtToken工具类
 *
 * @author Young-Pastor
 */
public class JwtTokenUtil {

    /**
     * 生成token
     *
     * @author Young-Pastor
     */
    public static String generateToken(JwtPayLoad jwtPayLoad) {

        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        DateTime expirationDate = DateUtil.offsetSecond(new Date(), Convert.toInt(sysConfigCache.getTokenExpireSec()));
        String jwtToken =  Jwts.builder()
                .setClaims(BeanUtil.beanToMap(jwtPayLoad))
                .setSubject(jwtPayLoad.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, sysConfigCache.getJwtSecret())
                .compact();
        if (sysConfigCache.getCryptogramConfigs().getTokenEncDec()) {
            // 加密token
            return CryptogramUtil.doEncrypt(jwtToken);
        }
        return jwtToken;
    }

    /**
     * 根据token获取Claims
     *
     * @author Young-Pastor
     */
    private static Claims getClaimsFromToken(String token) {
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        return Jwts.parser()
                .setSigningKey(sysConfigCache.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取JwtPayLoad部分
     *
     * @author Young-Pastor
     */
    public static JwtPayLoad getJwtPayLoad(String token) {
        Claims claims = getClaimsFromToken(token);
        return BeanUtil.mapToBean(claims, JwtPayLoad.class, false);
    }

    /**
     * 校验token是否正确
     *
     * @author Young-Pastor
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
     * @author Young-Pastor
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
