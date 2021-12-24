package org.lxy.swing.panel;

import lombok.Getter;
import org.lxy.swing.constant.LanguageEnum;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * @author Cassius
 * @date 2021/12/21 4:44 PM
 * @description mac
 */
@Getter
@Component
public class HomeJPanel extends JPanel {

    public static HomeJPanel homeJPanel;

    public HomeJPanel() {
        setName(LanguageEnum.HOME.getMsg());
        setLayout(null);
    }

    public JPanel getJPanel(JPanel panel){
        if(homeJPanel == null || !homeJPanel.getName().equals(panel.getName())){
            homeJPanel = new HomeJPanel();
            homeJPanel.add(panel);
            return homeJPanel;
        }
        return homeJPanel;
    }
}
