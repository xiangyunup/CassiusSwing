package org.lxy.swing.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author Cassius
 * @date 2021/12/23 9:59 AM
 * @description 头像工具类
 */
@Slf4j
public class AvatarUtil {

    public static LinkedHashMap<String,String> avatarNanFiles = new LinkedHashMap<String,String>(){{
        putAll(getFiles(PropertiesUtil.getValue("avatar") + "/nan"));
    }};

    public static LinkedHashMap<String,String> avatarNvFiles = new LinkedHashMap<String,String>(){{
        putAll(getFiles(PropertiesUtil.getValue("avatar") + "/nv"));
    }};

    public static LinkedHashMap<String,String> avatarFiles = new LinkedHashMap<String,String>(){{
        log.debug("AvatarUtil.in");
        putAll(avatarNvFiles);
        putAll(avatarNanFiles);
        if(CollectionUtils.isEmpty(avatarNvFiles)
            && CollectionUtils.isEmpty(avatarNanFiles)){
            log.debug("AvatarUtil.red");
            putAll(getFiles(PropertiesUtil.getValue("avatar")));
        }
    }};

    public static String avatarFile(String key){
        return avatarFiles.get(key);
    }

    /**
     * 读取某个目录下所有文件、文件夹,名称不重复
     * @param path
     * @return LinkedHashMap<String,String>
     */
    public static LinkedHashMap<String,String> getFiles(String path) {
        LinkedHashMap<String,String> files = new LinkedHashMap<>();
        File file = new File(path);
        if(file.isDirectory()){
            File[] tempList = file.listFiles();
            for (int i = 0; i < Objects.requireNonNull(tempList).length; i++) {
                if (tempList[i].isDirectory()) {
                    files.putAll(getFiles(tempList[i].getPath()));
                } else {
                    files.put(tempList[i].getName(),tempList[i].getPath());
                }
            }
        } else {
            files.put(file.getName(),file.getPath());
        }
        return files;
    }

    public static void main(String[] args) {
        System.out.println(AvatarUtil.avatarFiles.size());
        for (String s : AvatarUtil.avatarFiles.keySet()) {
            System.out.println(AvatarUtil.avatarFiles.get(s));

        }
    }

}
