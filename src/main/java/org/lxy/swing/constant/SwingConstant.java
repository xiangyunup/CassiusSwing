package org.lxy.swing.constant;

import java.awt.*;

/**
 * @author Cassius
 * @date 2021/12/21 2:32 PM
 * @description mac
 */
public interface SwingConstant extends javax.swing.SwingConstants {

    /**
     * 得到显示器屏幕的宽高
     */
    int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    /**
     * 定义窗体的宽高
     */
    int WINDOWS_WEDTH = 1000;
    int WINDOWS_HEIGHT = 800;

    /**
     * 定义按钮的宽高
     */
    int BUTTON_WEDTH = 80;
    int BUTTON_WEDTH_MIN = 30;
    int BUTTON_HEIGHT = 25;
    int INPUT_HEIGHT = 25;

}
