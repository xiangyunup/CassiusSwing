package org.lxy.swing.util;

import com.alibaba.fastjson.JSONObject;
import org.lxy.swing.panel.niuniu.dto.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Cassius
 * @date 2021/12/22 10:37 AM
 * @description 字牌工具类封装
 */
public class BrandUtil {
    /**
     * 斗地主字牌张数
     */
    public static int DDZ_OF_PAGE = 17;
    /**
     * 字牌定义
     */
    public static List<String> NUMBERS = Arrays.asList("1,2,3,4,5,6,7,8,9,10,11,12,13".split(","));
    public static List<String> BRANDS = Arrays.asList("A,2,3,4,5,6,7,8,9,10,J,Q,K".split(","));
    /**
     * 洗牌
     * @return 52张牌 + 2张王 = 54张
     * @param isKing 是否包含大小王
     */
    public static LinkedList<Brands> xiPai(boolean isKing){
        List<Brands> kingBrands = new ArrayList<>();
        // 大小王单独定义
        if(isKing){
            kingBrands.add(new DKingBrands());
            kingBrands.add(new XKingBrands());
        }
        // 黑红梅方
        kingBrands.addAll(getBrands());
        return new LinkedList<>(kingBrands);
    }

    /**
     * 获取黑红梅方
     * @return
     */
    private static List<Brands> getBrands(){
        List<Brands> brands = new ArrayList<>();
        Brands brand = null;
        for (int i = 0; i < 4; i++) {
            for (String number : NUMBERS) {
                // 黑红梅方
                if(i == 0){
                    brand = new SquareBrands();
                } else if(i == 1){
                    brand = new HeartBrands();
                } else if(i == 2){
                    brand = new PlumBrands();
                } else {
                    brand = new SpadeBrands();
                }
                int i1 = Integer.parseInt(number);
                brand.setNumber(i1);
                brand.setName(BRANDS.get(i1 - 1));
                brands.add(brand);
            }
        }

        return brands;
    }

    /**
     * 牌面比较
     * @param niu1
     * @param niu2
     * @return
     */
    public static int compareBrand(Brands niu1,Brands niu2) {
        // 1,判断点数
        // 2,判断花色-黑>红>梅>方
        if(niu1.getNumber() > niu2.getNumber()){
            return 1;
        } else if(niu1.getNumber() < niu2.getNumber()){
            return 2;
        } else {
            return compareHua(niu1.getName(),niu2.getName());
        }
    }
    /**
     * 花色比较
     * @param brandName1
     * @param brandName2
     * @return
     */
    private static int compareHua(String brandName1,String brandName2){
        return getHuaSize(brandName1) > getHuaSize(brandName2) ? 1 : 2;
    }
    /**
     * 获取花色定义点数
     * @param brandName
     * @return
     */
    private static int getHuaSize(String brandName){
        if(brandName.startsWith("黑")){
            return 4;
        } else if(brandName.startsWith("红")){
            return 3;
        } else if(brandName.startsWith("梅")){
            return 2;
        } else {
            // 方
            return 1;
        }
    }

    /**
     * 取出最大的一张牌面
     * @param result
     * @return
     */
    public static Brands getMaxBrand(JSONObject result) {
        // 取出最大的一张牌面
        List<Brands> brandsList = result.getJSONArray("brandsList").toJavaList(Brands.class);
        return getMaxBrand(brandsList);
    }
    public static Brands getMaxBrand(List<Brands> brandsList) {
        Brands brand = null;
        for (Brands brands : brandsList) {
            if(brand == null || compareBrand(brands, brand) == 1){
                brand = brands;
            }
        }
        return brand;
    }

}
