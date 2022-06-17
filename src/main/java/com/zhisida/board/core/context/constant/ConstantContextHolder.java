
package com.zhisida.board.core.context.constant;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.pojo.cryptogram.CryptogramConfigs;
import com.zhisida.board.core.pojo.druid.DruidProperties;
import com.zhisida.board.core.pojo.email.EmailConfigs;
import com.zhisida.board.core.pojo.oauth.OauthConfigs;
import com.zhisida.board.core.pojo.sms.AliyunSmsConfigs;
import com.zhisida.board.core.pojo.sms.TencentSmsConfigs;

import java.util.List;

import static com.zhisida.board.core.exception.enums.ServerExceptionEnum.CONSTANT_EMPTY;

/**
 * 系统参数配置获取
 *
 * @author young-pastor
 */
public class ConstantContextHolder {

    private static final Log log = Log.get();

    /**
     * 获取租户功能是否开启
     *
     * @author young-pastor
     */
    public static Boolean getTenantOpenFlag() {
        return getSysConfigWithDefault("TENANT_OPEN", Boolean.class, false);
    }

    /**
     * 获取放开xss过滤的接口
     *
     * @author young-pastor
     */
    public static List<String> getUnXssFilterUrl() {
        String boardUnXssFilterUrl = getSysConfigWithDefault("UN_XSS_FILTER_URL", String.class, null);
        if (ObjectUtil.isEmpty(boardUnXssFilterUrl)) {
            return CollectionUtil.newArrayList();
        } else {
            return CollectionUtil.toList(boardUnXssFilterUrl.split(SymbolConstant.COMMA));
        }
    }

    /**
     * 获取演示环境开关是否开启，默认为false
     *
     * @author young-pastor
     */
    public static Boolean getDemoEnvFlag() {
        return getSysConfigWithDefault("DEMO_ENV_FLAG", Boolean.class, false);
    }

    /**
     * 邮件的配置
     *
     * @author young-pastor
     */
    public static EmailConfigs getEmailConfigs() {
        String host = getSysConfig("EMAIL_HOST", String.class, true);
        String username = getSysConfig("EMAIL_USERNAME", String.class, true);
        String password = getSysConfig("EMAIL_PASSWORD", String.class, true);
        Integer port = getSysConfig("EMAIL_PORT", Integer.class, true);
        String from = getSysConfig("EMAIL_FROM", String.class, true);
        Boolean ssl = getSysConfig("EMAIL_SSL", Boolean.class, true);

        EmailConfigs emailConfigs = new EmailConfigs();
        emailConfigs.setHost(host);
        emailConfigs.setUser(username);
        emailConfigs.setPass(password);
        emailConfigs.setPort(port);
        emailConfigs.setFrom(from);
        emailConfigs.setSslEnable(ssl);
        return emailConfigs;
    }

    /**
     * 获取腾讯云短信的配置
     *
     * @author young-pastor
     */
    public static TencentSmsConfigs getTencentSmsConfigs() {
        String boardTencentSmsSecretId = getSysConfig("TENCENT_SMS_SECRET_ID", String.class, true);
        String boardTencentSmsSecretKey = getSysConfig("TENCENT_SMS_SECRET_KEY", String.class, true);
        String boardTencentSmsSdkAppId = getSysConfig("TENCENT_SMS_SDK_APP_ID", String.class, true);
        String boardTencentSmsSign = getSysConfig("TENCENT_SMS_SIGN", String.class, true);

        TencentSmsConfigs tencentSmsConfigs = new TencentSmsConfigs();
        tencentSmsConfigs.setSecretId(boardTencentSmsSecretId);
        tencentSmsConfigs.setSecretKey(boardTencentSmsSecretKey);
        tencentSmsConfigs.setSdkAppId(boardTencentSmsSdkAppId);
        tencentSmsConfigs.setSign(boardTencentSmsSign);
        return tencentSmsConfigs;
    }

    /**
     * 获取Druid默认用户名密码
     *
     * @author young-pastor
     */
    public static DruidProperties getDruidLoginConfigs() {
        String boardDruidLoginUsername = getSysConfigWithDefault("DRUID_LOGIN_USERNAME", String.class, RandomUtil.randomString(10));
        String boardDruidLoginPassword = getSysConfigWithDefault("DRUID_LOGIN_PASSWORD", String.class, RandomUtil.randomString(10));

        DruidProperties druidProperties = new DruidProperties();
        druidProperties.setLoginUsername(boardDruidLoginUsername);
        druidProperties.setLoginPassword(boardDruidLoginPassword);
        return druidProperties;
    }

    /**
     * 获取阿里云短信的配置
     *
     * @author young-pastor
     */
    public static AliyunSmsConfigs getAliyunSmsConfigs() {
            String boardSmsAccesskeyId = getSysConfig("ALIYUN_SMS_ACCESSKEY_ID", String.class, true);
        String boardSmsAccesskeySecret = getSysConfig("ALIYUN_SMS_ACCESSKEY_SECRET", String.class, true);
        String boardSmsSignName = getSysConfig("ALIYUN_SMS_SIGN_NAME", String.class, true);
        String boardSmsLoginTemplateCode = getSysConfig("ALIYUN_SMS_LOGIN_TEMPLATE_CODE", String.class, true);
        String boardSmsInvalidateMinutes = getSysConfig("ALIYUN_SMS_INVALIDATE_MINUTES", String.class, true);

        AliyunSmsConfigs aliyunSmsProperties = new AliyunSmsConfigs();
        aliyunSmsProperties.setAccessKeyId(boardSmsAccesskeyId);
        aliyunSmsProperties.setAccessKeySecret(boardSmsAccesskeySecret);
        aliyunSmsProperties.setSignName(boardSmsSignName);
        aliyunSmsProperties.setLoginTemplateCode(boardSmsLoginTemplateCode);
        aliyunSmsProperties.setInvalidateMinutes(Convert.toInt(boardSmsInvalidateMinutes));
        return aliyunSmsProperties;
    }

    /**
     * 获取jwt密钥，默认是32位随机字符串
     *
     * @author young-pastor
     */
    public static String getJwtSecret() {
        return getSysConfigWithDefault("JWT_SECRET", String.class, RandomUtil.randomString(32));
    }

    /**
     * 获取默认密码
     *
     * @author young-pastor
     */
    public static String getDefaultPassWord() {
        return getSysConfigWithDefault("DEFAULT_PASSWORD", String.class, CommonConstant.DEFAULT_PASSWORD);
    }

    /**
     * 获取会话过期时间，默认2小时
     *
     * @author young-pastor
     */
    public static Long getSessionTokenExpireSec() {
        return getSysConfigWithDefault("SESSION_EXPIRE", Long.class, 2 * 60 * 60L);
    }

    /**
     * 获取token过期时间（单位：秒）
     * <p>
     * 默认时间1天
     *
     * @author young-pastor
     */
    public static Long getTokenExpireSec() {
        return getSysConfigWithDefault("TOKEN_EXPIRE", Long.class, 86400L);
    }

    /**
     * 获取自定义的windows环境本地文件上传路径
     *
     * @author young-pastor
     */
    public static String getDefaultFileUploadPathForWindows() {
        return getSysConfigWithDefault("FILE_UPLOAD_PATH_FOR_WINDOWS", String.class, "");
    }

    /**
     * 获取自定义的linux环境本地文件上传路径
     *
     * @author young-pastor
     */
    public static String getDefaultFileUploadPathForLinux() {
        return getSysConfigWithDefault("FILE_UPLOAD_PATH_FOR_LINUX", String.class, "");
    }

    /**
     * 获取是否允许单用户登陆的开关， 默认为false
     *
     * @author young-pastor
     */
    public static Boolean getEnableSingleLogin() {
        return getSysConfigWithDefault("ENABLE_SINGLE_LOGIN", Boolean.class, false);
    }

    /**
     * 获取阿里云定位接口
     *
     * @author young-pastor
     **/
    public static String getIpGeoApi() {
        return getSysConfig("IP_GEO_API", String.class, false);
    }

    /**
     * 获取阿里云定位appCode
     *
     * @author young-pastor
     **/
    public static String getIpGeoAppCode() {
        return getSysConfig("IP_GEO_APP_CODE", String.class, false);
    }

    /**
     * 获取Oauth码云第三方登录的配置
     *
     * @author young-pastor
     **/
    public static OauthConfigs getGiteeOauthConfigs() {
        String clientId = getSysConfig("OAUTH_GITEE_CLIENT_ID", String.class, true);
        String clientSecret = getSysConfig("OAUTH_GITEE_CLIENT_SECRET", String.class, true);
        String redirectUri = getSysConfig("OAUTH_GITEE_REDIRECT_URI", String.class, true);

        OauthConfigs oauthConfigs = new OauthConfigs();
        oauthConfigs.setClientId(clientId);
        oauthConfigs.setClientSecret(clientSecret);
        oauthConfigs.setRedirectUri(redirectUri);
        return oauthConfigs;
    }

    /**
     * 获取OauthGithub第三方登录的配置
     *
     * @author young-pastor
     **/
    public static OauthConfigs getGithubOauthConfigs() {
        String clientId = getSysConfig("OAUTH_GITHUB_CLIENT_ID", String.class, true);
        String clientSecret = getSysConfig("OAUTH_GITHUB_CLIENT_SECRET", String.class, true);
        String redirectUri = getSysConfig("OAUTH_GITHUB_REDIRECT_URI", String.class, true);

        OauthConfigs oauthConfigs = new OauthConfigs();
        oauthConfigs.setClientId(clientId);
        oauthConfigs.setClientSecret(clientSecret);
        oauthConfigs.setRedirectUri(redirectUri);
        return oauthConfigs;
    }

    /**
     * 获取是否允许Oauth用户登陆的开关， 默认为false
     *
     * @author young-pastor
     **/
    public static Boolean getEnableOauthLogin() {
        return getSysConfigWithDefault("ENABLE_OAUTH_LOGIN", Boolean.class, false);
    }

    /**
     * 获取前端项目的地址
     *
     * @author young-pastor
     **/
    public static String getWebUrl() {
        return getSysConfig("WEB_URL", String.class, true);
    }

    /**
     * 获取支付宝支付成功转发地址
     *
     * @author young-pastor
     **/
    public static String getAlipayReturnUrl() {
        return getSysConfig("ALIPAY_RETURN_URL", String.class, true);
    }

    /**
     * 获取OnlyOffice地址
     *
     * @author young-pastor
     **/
    public static String getOnlyOfficeUrl() {
        return getSysConfig("ONLY_OFFICE_SERVICE_URL", String.class, true);
    }

    /**
     * 获取config表中的配置，如果为空返回默认值
     *
     * @param configCode   变量名称，对应sys_config表中的code
     * @param clazz        返回变量值的类型
     * @param defaultValue 如果结果为空返回此默认值
     * @author young-pastor
     */
    public static <T> T getSysConfigWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        String configValue = ConstantContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            // 将默认值加入到缓存常量
            log.warn(">>> 系统配置sys_config表中存在空项，configCode为：{}，系统采用默认值：{}", configCode, defaultValue);
            ConstantContext.me().put(configCode, defaultValue);
            return defaultValue;
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    /**
     * 获取config表中的配置，如果为空，是否抛出异常
     *
     * @param configCode   变量名称，对应sys_config表中的code
     * @param clazz        返回变量值的类型
     * @param nullThrowExp 若为空是否抛出异常
     * @author young-pastor
     */
    public static <T> T getSysConfig(String configCode, Class<T> clazz, Boolean nullThrowExp) {
        String configValue = ConstantContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            if (nullThrowExp) {
                String format = StrUtil.format(">>> 系统配置sys_config表中存在空项，configCode为：{}", configCode);
                log.error(format);
                throw new ServiceException(CONSTANT_EMPTY.getCode(), format);
            } else {
                return null;
            }
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                if (nullThrowExp) {
                    String format = StrUtil.format(">>> 系统配置sys_config表中存在格式错误的值，configCode={}，configValue={}", configCode, configValue);
                    log.error(format);
                    throw new ServiceException(CONSTANT_EMPTY.getCode(), format);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * 获取验证码 开关标识
     *
     * @author young-pastor
     */
    public static Boolean getCaptchaOpenFlag() {
        return getSysConfigWithDefault("CAPTCHA_OPEN", Boolean.class, true);
    }

    /**
     * 获取加解密的配置
     *
     * @author young-pastor
     */
    public static CryptogramConfigs getCryptogramConfigs() {
        boolean tokenEncDec = getSysConfigWithDefault("TOKEN_ENCRYPTION_OPEN", Boolean.class, true);
        boolean visLogEnc = getSysConfigWithDefault("VISLOG_ENCRYPTION_OPEN", Boolean.class, true);
        boolean opLogEnc = getSysConfigWithDefault("OPLOG_ENCRYPTION_OPEN", Boolean.class, true);
        boolean fieldEncDec = getSysConfigWithDefault("FIELD_ENC_DEC_OPEN", Boolean.class, true);

        CryptogramConfigs cryptogramConfigs = new CryptogramConfigs();
        cryptogramConfigs.setTokenEncDec(tokenEncDec);
        cryptogramConfigs.setVisLogEnc(visLogEnc);
        cryptogramConfigs.setOpLogEnc(opLogEnc);
        cryptogramConfigs.setFieldEncDec(fieldEncDec);
        return cryptogramConfigs;
    }

}
