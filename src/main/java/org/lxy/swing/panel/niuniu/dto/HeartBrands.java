package org.lxy.swing.panel.niuniu.dto;

import java.io.File;

/**
 * @author Cassius
 * @date 2021/12/21 6:19 PM
 * @description 红桃
 */
public class HeartBrands extends Brands {

    @Override
    public String getName() {
        return "红桃" + super.getName();
    }

    @Override
    public String getIconFileName() {
        return "img" + File.separator + "红桃.png";
    }
}
