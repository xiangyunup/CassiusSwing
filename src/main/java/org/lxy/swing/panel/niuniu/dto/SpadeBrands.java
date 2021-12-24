package org.lxy.swing.panel.niuniu.dto;

import java.io.File;

/**
 * @author Cassius
 * @date 2021/12/21 6:19 PM
 * @description 黑桃
 */
public class SpadeBrands extends Brands {

    @Override
    public String getName() {
        return "黑桃" + super.getName();
    }

    @Override
    public String getIconFileName() {
        return "img" + File.separator + "黑桃.png";
    }
}
