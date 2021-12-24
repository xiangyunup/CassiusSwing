package org.lxy.swing.panel.niuniu.dto;

import lombok.Data;

/**
 * @author Cassius
 * @date 2021/12/21 6:22 PM
 * @description 字牌
 */
@Data
public class Brands {

    /**
     * 字牌点数
     */
    private int number;
    /**
     * 字牌名称
     */
    private String name;
    /**
     * 图标名称 path
     */
    private String iconFileName;
    // 判断是否是牛牛,牛牛的规则中，最大 = 10
    public int getNumber(boolean isNiu) {
        if(isNiu && number > 10){
            return 10;
        }
        return number;
    }
}
