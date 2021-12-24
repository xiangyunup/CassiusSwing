package org.lxy;

import org.lxy.swing.config.SpringContext;
import org.lxy.swing.panel.HomeJPanel;
import org.lxy.swing.panel.LoginJPanel;
import org.lxy.swing.util.LoginUtil;
import org.lxy.swing.util.SwingUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.lxy.swing.constant.SwingConstant.WINDOWS_HEIGHT;
import static org.lxy.swing.constant.SwingConstant.WINDOWS_WEDTH;

@SpringBootApplication
public class CassiusSwing extends JFrame {

    public CassiusSwing(){
        setTitle(SwingUtil.TITLE);
        setSize(WINDOWS_WEDTH, WINDOWS_HEIGHT);
        // 居中
        setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                LoginUtil.delUser();
                System.exit(0);
            }
        });
    }

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(CassiusSwing.class)
                        .headless(false).run(args);
        SwingUtilities.invokeLater(() -> {
            SwingUtil.cassiusSwing = ctx.getBean(CassiusSwing.class);
            HomeJPanel home = ctx.getBean(HomeJPanel.class);
            LoginJPanel login = ctx.getBean(LoginJPanel.class);
            // 加载首页-登录面板
            SwingUtil.cassiusSwing.add(home.getJPanel(login.getJPanel()));
            // 设置窗体可见
            SwingUtil.cassiusSwing.setVisible(true);
        });
    }

}
