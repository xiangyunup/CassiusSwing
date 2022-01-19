package org.lxy.swing.panel;

import lombok.Getter;
import org.lxy.CassiusSwing;
import org.lxy.swing.constant.LanguageEnum;
import org.lxy.swing.constant.SwingConstant;
import org.lxy.swing.panel.niuniu.NiuNiuJPanel;
import org.lxy.swing.util.GeneratorUtil;
import org.lxy.swing.util.PropertiesUtil;
import org.lxy.swing.util.SwingUtil;
import org.springframework.stereotype.Component;

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
public class GenJPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingUtil.cassiusSwing = new CassiusSwing();
            // 加载登录面板
            SwingUtil.cassiusSwing.add(new HomeJPanel()
                    .getJPanel(new GenJPanel().getJPanel()));
            // 设置窗体可见
            SwingUtil.cassiusSwing.setVisible(true);
        });
    }

    public JPanel getJPanel(){
        JPanel panel = new JPanel();
        panel.setName(LanguageEnum.GEN.getMsg());
        panel.setLayout(null);
        if(HomeJPanel.homeJPanel == null || HomeJPanel.homeJPanel.getHeight() == SwingConstant.WINDOWS_HEIGHT){
            panel.setBounds((int)(SwingConstant.WINDOWS_WEDTH * 0.35),(int)(SwingConstant.WINDOWS_HEIGHT * 0.4),800,400);
        } else {
            panel.setBounds((int)(HomeJPanel.homeJPanel.getWidth() * 0.35),(int)(HomeJPanel.homeJPanel.getHeight() * 0.4),800,400);
        }
        // 创建 JLabel
        JLabel urlLabel = new JLabel(LanguageEnum.URL.getMsg());
        urlLabel.setBounds(10,20,BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(urlLabel);
        JTextArea urlText = new JTextArea(5,20);
        urlText.setLineWrap(true);
        urlText.setText(PropertiesUtil.boValue("swing.dev.enable") ? PropertiesUtil.getValue("spring.datasource.url") : null);
        urlText.setBounds(100,20,300,INPUT_HEIGHT);
        panel.add(urlText);

        // 创建 JLabel
        JLabel userLabel = new JLabel(LanguageEnum.USER_NAME.getMsg());
        userLabel.setBounds(10,50,BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setText(PropertiesUtil.boValue("swing.dev.enable") ? PropertiesUtil.getValue("spring.datasource.username") : null);
        userText.setBounds(100,50,300,INPUT_HEIGHT);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel(LanguageEnum.PASS_WORD.getMsg());
        passwordLabel.setBounds(10,80,BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setText(PropertiesUtil.boValue("swing.dev.enable") ? PropertiesUtil.getValue("spring.datasource.password") : null);
        passwordText.setBounds(100,80,300,INPUT_HEIGHT);
        panel.add(passwordText);

        // 创建 JLabel
        JLabel tableLabel = new JLabel(LanguageEnum.TABLE.getMsg());
        tableLabel.setBounds(10,110,BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(tableLabel);
        JTextField tableText = new JTextField(20);
        tableText.setBounds(100,110,300,INPUT_HEIGHT);
        panel.add(tableText);


        Action action = new TextAction(LanguageEnum.GEN.getMsg()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] args = {urlText.getText(),userText.getText(),new String(passwordText.getPassword()),tableText.getText()};
                    GeneratorUtil.main(args);
                    SwingUtil.showInfoMsg("代码已生成");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtil.showErrorMsg(ex.getMessage());
                }
            }
        };

        // 创建开始按钮
        JButton startButton = new JButton(action);
        startButton.setBounds((int)(panel.getWidth() * NiuNiuJPanel.SINK_SCALE_W), (int)(panel.getHeight() * NiuNiuJPanel.SINK_SCALE_Y), BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(startButton);
        // 创建返回按钮
        JButton collbackButton = new JButton(LoginJPanel.getReturnAction(panel));
        collbackButton.setBounds((int)(panel.getWidth() * NiuNiuJPanel.SINK_SCALE_W) - 70, (int)(panel.getHeight() * NiuNiuJPanel.SINK_SCALE_Y), BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(collbackButton);
        return panel;
    }
}
