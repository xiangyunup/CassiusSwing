package org.lxy.swing.util;

import org.lxy.swing.server.model.NnUser;

import java.util.LinkedHashMap;

/**
 * @author Cassius
 * @date 2021/12/23 4:49 PM
 * @description mac
 */
public class LoginUtil {

    public static LinkedHashMap<String, NnUser> loginUsers = new LinkedHashMap<>();

    public static int gamesNumber = 0;

    public static void addUser(NnUser user) {
        try {
            loginUsers.put(SystemUtil.getCpuId(),user);
            gamesNumber = loginUsers.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NnUser getUser() {
        try {
            return loginUsers.get(SystemUtil.getCpuId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NnUser getUser(LinkedHashMap<String, NnUser> loginUsers) {
        try {
            return loginUsers.get(SystemUtil.getCpuId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delUser() {
        try {
            loginUsers.remove(SystemUtil.getCpuId());
            gamesNumber = loginUsers.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void delUser(LinkedHashMap<String, NnUser> loginUsers) {
        try {
            loginUsers.remove(SystemUtil.getCpuId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
