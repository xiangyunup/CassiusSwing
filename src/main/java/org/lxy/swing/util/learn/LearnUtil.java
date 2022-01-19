package org.lxy.swing.util.learn;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author Cassius
 * @date 2022/1/18 2:42 PM
 * @description 学习类
 */
public class LearnUtil {


    public static void main(String[] args) {
//        System.out.println(prefixWith("asflase","adflaervgeq","afflaervvveq","agflaxaerveq","ahflaaseq","ajflas"));

        System.out.println(repeatSize("aassdsasdadasdasdasdasdafqqwerasada","a"));
        System.out.println(deduplication("你好你好"));
    }

    /**
     * 获取最大相同前缀-没有则返回空
     * @return ""
     */
    public static String prefixWith(String... ss){
        String str = Arrays.stream(ss).sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList()).get(0);
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < ss.length; j++) {
                if(!ss[j].startsWith(str.substring(0, i + 1))){
                    return StringUtils.isEmpty(result) ? "\"\"" : result;
                } else if(j == ss.length - 1 || i == 0){
                    result = str.substring(0, i + 1);
                } else {
                    result = str.substring(0, i);
                }
            }
        }
        return StringUtils.isEmpty(result) ? "\"\"" : result;
    }

    /**
     * 字符串去重
     */
    public static String deduplication(String str){
        String result = "";
        if(StringUtils.isNotBlank(str)){
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if(str.indexOf(c) == i ){
                    result += c;
                }
            }
        }
        return result;
    }

    /**
     * 字符重复次数
     */
    public static int repeatSize(String str,String key){
        int n = 0;
        if(StringUtils.isNotBlank(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (key.charAt(0) == str.charAt(i)) {
                    n++;
                }
            }
        }
        return n;
    }
}
