package org.lxy.swing.server.biz;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lxy.swing.constant.LanguageEnum;
import org.lxy.swing.server.model.NnUser;
import org.lxy.swing.server.service.INnUserService;
import org.lxy.swing.util.LoginUtil;
import org.lxy.swing.util.PropertiesUtil;
import org.lxy.swing.util.SwingUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * @author Cassius
 * @date 2021/12/20 4:27 PM
 * @description  Action 逻辑处理
 */
@Slf4j
@Service
public class ActionPerformedService {

    @Resource
    private INnUserService nnUserService;

    public boolean actionPerformed(JSONObject jsonObject, ActionEvent e) {
        log.info("actionPerformed:{},{}",e.getActionCommand(),jsonObject);
        if(LanguageEnum.LOGIN_OK.getMsg().equals(e.getActionCommand())){
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            if(StringUtils.isBlank(username)){
                SwingUtil.showErrorMsg("请输入账户！");
            }else if(StringUtils.isBlank(password)){
                SwingUtil.showErrorMsg("请输入密码！");
            }else if(PropertiesUtil.boValue("swing.dev.enable")
                    && PropertiesUtil.eqValue(username,"swing.dev.username")
                    && PropertiesUtil.eqValue(password,"swing.dev.password")){
                SwingUtil.showInfoMsg("开发者登录成功！");
                return true;
            } else {
                NnUser user = nnUserService.findUser(username, password);
                if(user == null){
                    SwingUtil.showErrorMsg("账户或密码错误！");
                } else {
                    LoginUtil.addUser(user);
                    SwingUtil.showInfoMsg("登录成功！");
                    return true;
                }
            }
        }
        return false;
    }
}
