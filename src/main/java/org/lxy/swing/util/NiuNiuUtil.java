package org.lxy.swing.util;

import com.alibaba.fastjson.JSONObject;
import org.lxy.swing.panel.niuniu.dto.Brands;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author Cassius
 * @date 2021/12/21 6:17 PM
 * @description 牛牛工具类
 */
public class NiuNiuUtil {

    /**
     * 牛牛字牌张数
     */
    public static int NIU_NIU_OF_PAGE = 5;
    /**
     * 游戏人数
     */
    public static int NUMBER_OF_GAMES = 5;
    public static int MIN_NUMBER_OF_GAMES = 1;
    public static int MAX_NUMBER_OF_GAMES = 10;

    private static final String NIU = "牛";
    private static final String WU_CAI = "五彩";
    private static final String NIU_NIU = "牛牛";
    private static final String NOT_NIU = "无牛";

    /**
     * 计算牛牛
     * @param brandsList
     */
    public static String syncBull(List<Brands> brandsList) {
        int sum = 0;
        int wuSum = 0;
        int bull = 0;
        LinkedList<Integer> card = new LinkedList<>();
        for (int i = 0; i < brandsList.size(); i++) {
            card.add(brandsList.get(i).getNumber(true));
            sum += card.get(i);
            if(brandsList.get(i).getNumber() > 10){
                wuSum++;
            }
        }
        // 最高为50点，则代表全部为10
        if(sum == 50){
            // 全部大于10点，则全部为花牌，理解为五彩
            if(wuSum == 5){
                return WU_CAI;
            }
            return NIU_NIU;
        } else {
            // 计算是否有牛
            if(checkNiu(card)){
                bull = (card.get(0) + card.get(1));
            }
        }
        if (bull == 0) {
            return NOT_NIU;
        } else if (bull == 20) {
            return NIU_NIU;
        } else {
            return NIU + (bull % 10);
        }
    }

    public static boolean checkNiu(LinkedList<Integer> card){
        for (int i = 0; i < card.size(); i++) {
            for (int j = i + 1; j < card.size(); j++) {
                for (int k = j + 1; k < card.size(); k++) {
                    Integer cardNum = card.get(i) + card.get(j) + card.get(k);
                    if(cardNum % 10 == 0){
                        card.remove(k);
                        card.remove(j);
                        card.remove(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 比较器
     * @param results
     * @return
     */
    public static int compare(List<JSONObject> results) {
        JSONObject result = null;
        for (JSONObject obj : results) {
            if(result == null){
                result = obj;
                continue;
            }
            int n = compareNiu(obj.getString("niu"), result.getString("niu"));
            if(n == 0){
                n = BrandUtil.compareBrand(BrandUtil.getMaxBrand(obj), BrandUtil.getMaxBrand(result));
            }
            if(n == 1){
                result = obj;
            }
        }
        return result == null ? 0 : result.getIntValue("figure");
    }

    /**
     * 牛牛比较
     * @param niu1
     * @param niu2
     * @return
     */
    private static int compareNiu(String niu1,String niu2) {
        // 1，判断相等返回0，后续判断牌面
        // 2，判断是否有牛牛，如果有返回获胜
        // 3，一方无牛的情况下，返回另一方获胜
        // 4，去除多余描述，比较数字大小
        if(niu1.equals(niu2)){
            return 0;
        }else if(NIU_NIU.equals(niu1)){
            return 1;
        } else if(NIU_NIU.equals(niu2)){
            return 2;
        } else if(NOT_NIU.equals(niu1)){
            return 2;
        } else if(NOT_NIU.equals(niu2)){
            return 1;
        } else {
            String n1 = niu1.replace(NIU, "");
            String n2 = niu2.replace(NIU, "");
            return Integer.parseInt(n1) > Integer.parseInt(n2) ? 1 : 2;
        }
    }

    /**
     * 是否是数字
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static int checkGmaes(String games) {
        if(!isInteger(games)){
            SwingUtil.showErrorMsg("请输入数字！");
        } else {
            int gm = Integer.parseInt(games);
            if(MAX_NUMBER_OF_GAMES < gm || MIN_NUMBER_OF_GAMES > gm){
                SwingUtil.showWarningMsg("请输入" + MIN_NUMBER_OF_GAMES + "-" + MAX_NUMBER_OF_GAMES + "的数字！");
            } else {
                return gm;
            }
        }
        return 0;
    }

}
