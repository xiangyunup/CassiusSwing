package org.lxy.swing.util;

import org.lxy.CassiusSwing;
import org.lxy.swing.panel.HomeJPanel;

import javax.swing.*;

/**
 * @author Cassius
 * @date 2021/12/21 12:19 PM
 * @description mac
 */
public class SwingUtil {

    public static CassiusSwing cassiusSwing;
    public static String TITLE = "Cassius-Swing";

    /**
     * 切换面板中的内容
     * @param panel
     * @param switchPanel
     */
    public static void switchJpanel(JPanel panel, JPanel switchPanel) {
        // 移除面板中的所有组件
        HomeJPanel.homeJPanel.remove(panel);
        // 添加要切换的面板
        HomeJPanel.homeJPanel.add(switchPanel);
        // 刷新页面，重绘面板
        HomeJPanel.homeJPanel.updateUI();
        // 更新窗口标题
        SwingUtil.cassiusSwing.setTitle(TITLE + "-" + switchPanel.getName());
    }

    public static void showMsg(String msg,int type){
        if(JOptionPane.INFORMATION_MESSAGE == type || JOptionPane.QUESTION_MESSAGE == type){
            showInfoMsg(msg);
        } else if(JOptionPane.ERROR_MESSAGE == type){
            showErrorMsg(msg);
        } else if(JOptionPane.WARNING_MESSAGE == type){
            showWarningMsg(msg);
        } else {
            JOptionPane.showMessageDialog(null,msg,"提示 ", JOptionPane.PLAIN_MESSAGE);
        }
    }
    public static void showInfoMsg(String msg){
        JOptionPane.showMessageDialog(null,msg,"提示 ", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMsg(String msg){
        JOptionPane.showMessageDialog(null,msg,"错误 ", JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarningMsg(String msg){
        JOptionPane.showMessageDialog(null,msg,"警告 ", JOptionPane.WARNING_MESSAGE);
    }

}
