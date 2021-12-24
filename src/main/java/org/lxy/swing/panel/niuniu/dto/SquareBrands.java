package org.lxy.swing.panel.niuniu.dto;

import java.io.File;

/**
 * @author Cassius
 * @date 2021/12/21 6:19 PM
 * @description 方片
 */
public class SquareBrands extends Brands {

    @Override
    public String getName() {
        return "方片" + super.getName();
    }

    @Override
    public String getIconFileName() {
        return "img" + File.separator + "方片.png";
    }
}
