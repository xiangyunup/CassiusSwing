package org.lxy.swing.panel;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import org.lxy.CassiusSwing;
import org.lxy.swing.constant.LanguageEnum;
import org.lxy.swing.constant.SwingConstant;
import org.lxy.swing.panel.niuniu.NiuNiuJPanel;
import org.lxy.swing.server.biz.ActionPerformedService;
import org.lxy.swing.util.PropertiesUtil;
import org.lxy.swing.util.SwingUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.text.TextAction;
import java.awt.event.ActionEvent;

import static org.lxy.swing.constant.SwingConstant.*;

/**
 * @author Cassius
 * @date 2021/12/20 6:17 PM
 * @description 登录面板
 */
@Getter
@Component
public class LoginJPanel {

    @Resource
    private ActionPerformedService actionPerformedService;
    @Resource
    private NiuNiuJPanel niuNiuJPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CassiusSwing ex = new CassiusSwing();
            HomeJPanel homeJPanel = new HomeJPanel();
            LoginJPanel loginJPanel = new LoginJPanel();
            // 加载登录面板
            ex.add(homeJPanel.getJPanel(loginJPanel.getJPanel()));
            // 设置窗体可见
            ex.setVisible(true);
        });
    }

    public JPanel getJPanel(){
        JPanel panel = new JPanel();
        panel.setName(LanguageEnum.LOGIN_OK.getMsg());
        panel.setLayout(null);
        if(HomeJPanel.homeJPanel == null || HomeJPanel.homeJPanel.getHeight() == SwingConstant.WINDOWS_HEIGHT){
            panel.setBounds((int)(SwingConstant.WINDOWS_WEDTH * 0.35),(int)(SwingConstant.WINDOWS_HEIGHT * 0.4),300,200);
        } else {
            panel.setBounds((int)(HomeJPanel.homeJPanel.getWidth() * 0.35),(int)(HomeJPanel.homeJPanel.getHeight() * 0.4),300,200);
        }
        // 创建 JLabel
        JLabel userLabel = new JLabel(LanguageEnum.LOGIN_USER.getMsg());
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setText(PropertiesUtil.boValue("swing.dev.enable") ? PropertiesUtil.getValue("swing.dev.username") : null);
        userText.setBounds(100,20,165,INPUT_HEIGHT);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel(LanguageEnum.LOGIN_PASS_WORD.getMsg());
        passwordLabel.setBounds(10,50,BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setText(PropertiesUtil.boValue("swing.dev.enable") ? PropertiesUtil.getValue("swing.dev.password") : null);
        passwordText.setBounds(100,50,165,INPUT_HEIGHT);
        panel.add(passwordText);

        Action action = new TextAction(LanguageEnum.LOGIN_OK.getMsg()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username",userText.getText());
                jsonObject.put("password",new String(passwordText.getPassword()));
                if(PropertiesUtil.eqValue("dev","spring.profiles.active")){
                    actionPerformedService = new ActionPerformedService();
                }
                boolean result = actionPerformedService.actionPerformed(jsonObject, e);
                if(result){
                    if(PropertiesUtil.eqValue("dev","spring.profiles.active")){
                        SwingUtil.switchJpanel(panel,new NiuNiuJPanel().getJPanel());
                    } else {
                        SwingUtil.switchJpanel(panel,niuNiuJPanel.getJPanel());
                    }
                }
            }
        };
        // 创建登录按钮
        JButton loginButton = new JButton(action);
        loginButton.setBounds(190, 80, BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(loginButton);
        return panel;
    }
}
