package com.example.ypp.mylife.Utils;

/**
 * Created by User on 2017/3/24.
 */

public class Account {
    public static final String PREFS_USER_SEX = "user_sex";                     // 性别
    public static final String PREFS_USER_AGE = "user_age";                     // 年龄
    public static final String PREFS_USER_BIRTH = "user_birth";                 // 出生日期
    public static final String PREFS_USER_LOGIN_TYPE = "user_login_type";       // 登录类型
    public static final String PREFS_USER_NICKNAME = "user_nickname";           // 用户名称
    public static final String PREFS_USER_ICON_URL = "user_icon";               // 用户头像
    public static final String PREFS_USER_SIGN = "user_sign";
    public static final String PREFS_USER_UID = "user_uid";
    public static final String PREFS_USERID = "userid";
    public static final String PREFS_USER_MOBILE = "mobile";
    public static final String PREFS_USER_QQ_BIND = "qq_bind";
    public static final String PREFS_USER_WEIXIN_BIND = "weixin_bind";
    public static final String PREFS_USER_SINA_BIND = "sina_bind";
    public static final String PREFS_IS_LOGIN = "is_login";
    public static final String PREFS_MEDAL = "user_medal";
    public static final String PREFS_USER_BANDING_EMAIL = "user_banding_email"; // 绑定安全邮箱
    public static final String PREFS_USER_ADDRESS = "user_address";             // 地址
    public static final String PREFS_USER_EXIT = "user_exit_login"; // 强制退出登录

    public static class UserInfo {
        public String user_sex;
        public String user_age;
        public String user_birth;
        public String user_login_type;
        public String user_nickname;
        public String user_icon;
        public String user_userid;
        public String user_uid;// 修改微信 android和ios端数据不统一
        public String openid;
        public String unionid;

        public UserInfo(String user_sex, String user_age, String user_birth, String user_login_type,
                        String user_nickname, String user_icon, String user_userid, String user_uid,
                        String openid, String unionid) {
            this.user_sex = user_sex;
            this.user_age = user_age;
            this.user_birth=user_birth;
            this.user_login_type = user_login_type;
            this.user_nickname = user_nickname;
            this.user_icon = user_icon;
            this.user_userid = user_userid;
            this.user_uid = user_uid;
            this.openid = openid;
            this.unionid = unionid;
        }
    }
}
