package org.lxy.swing.panel.niuniu.dto;

import java.io.File;

/**
 * @author Cassius
 * @date 2021/12/21 6:19 PM
 * @description 梅花
 */
public class PlumBrands extends Brands {

    @Override
    public String getName() {
        return "梅花" + super.getName();
    }

    @Override
    public String getIconFileName() {
        return "img" + File.separator + "梅花.png";
    }
}
