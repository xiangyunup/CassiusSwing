package org.lxy.swing.constant;

import org.lxy.swing.util.PropertiesUtil;

/**
 * @author Cassius
 * @date 2021/12/20 6:27 PM
 * @description mac
 */
public enum LanguageEnum {
    HOME("首页","home"),
    LOGIN_OK("登录","login"),
    GEN("代码生成","gen"),

    USER_NAME("账户:","userName:"),
    PASS_WORD("密码:","passWord:"),
    URL("地址:","url:"),
    TABLE("表名:","table:"),

    NIU_NIU("牛牛","niuniu"),
    NUMBER_OF_GAMES("游戏人数:","Number of games:"),

    FIGURE("人物","figure"),
    RESULT("结果","result"),
    WIN("胜","WIN"),
    LOSE("负","LOSE"),


    START("开始","start"),
    RETURN("返回","return"),
    ;
    private String zh;
    private String en;

    public String getMsg() {
        if(PropertiesUtil.eqValue("en","language")){
            return getEn();
        }
        return getZh();
    }
    private String getZh() {
        return zh;
    }

    private String getEn() {
        return en;
    }

    LanguageEnum(String zh, String en) {
        this.zh = zh;
        this.en = en;
    }
}
