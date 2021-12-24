package org.lxy.swing.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.SystemUtils;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Cassius
 * @date 2021/12/23 3:01 PM
 * @description 系统工具类
 */
public class SystemUtil {

    public static void main(String[] args) throws Exception {
        System.out.println( getCpuId());
    }
    /**
     * 获取当前系统CPU序列，区分系统
     */
    public static String getCpuId() throws Exception {
        String cpuId;
        // linux系统用Runtime.getRuntime().exec()执行 dmidecode -t processor 查询cpu序列
        // windows系统用 wmic cpu get ProcessorId 查看cpu序列
        if (SystemUtils.IS_OS_WINDOWS) {
            cpuId = getWindowsCpuId();
        } else if(SystemUtils.IS_OS_MAC){
            cpuId = getMacHardware().getString("HardwareUUID");
        } else {
            cpuId = getLinuxCpuId("dmidecode -t processor | grep 'ID'", "ID", ":");
        }
        return cpuId;
    }

    public static String getMac() throws Exception {
        return getLinuxCpuId("ifconfig -a", "ether"," ");
    }

    /**
     * {
     *     "ModelName": "MacBookPro",型号名称
     *     "L3Cache": "6MB",L3缓存
     *     "Memory": "16GB",内存
     *     "TotalNumberofCores": "4",核总数
     *     "Hyper-ThreadingTechnology": "Enabled",超线程技术
     *     "BootROMVersion": "427.140.8.0.0",Boot ROM版本
     *     "NumberofProcessors": "1",处理器数目
     *     "SMCVersion": "2.30f2",SMC版本（系统）
     *     "SerialNumber": "C02QV8FKG8WP",序列号（系统）
     *     "ProcessorSpeed": "2.5GHz", 处理器速度
     *     "L2Cache": "256KB", L2缓存（每个核）
     *     "ModelIdentifier": "MacBookPro11,5",型号标识符
     *     "HardwareUUID": "68C2D057-D1C9-5BD7-8DF9-460E0FEC47EC",硬件UUID
     *     "ProcessorName": "Quad-CoreIntelCorei7" 处理器名称
     * }
     * @return
     * @throws Exception
     */
    public static JSONObject getMacHardware() throws Exception {
        JSONObject jsonObject = new JSONObject();
        String execResult = executeLinuxCmd("system_profiler SPHardwareDataType");
        String[] infos = execResult.split("\n");
        for (String info : infos) {
            info = info.trim()
                    .replace(" ", "")
                    .replace("(perCore)","")
                    .replace("(system)","");
            String[] sn = info.split(":");
            if(sn.length > 1){
                jsonObject.put(sn[0],sn[1]);
            }
        }
        return jsonObject;
    }
    /**
     * 获取linux系统CPU序列
     */
    public static String getLinuxCpuId(String cmd, String record, String symbol) throws Exception {
        String execResult = executeLinuxCmd(cmd);
        String[] infos = execResult.split("\n");
        for (String info : infos) {
            info = info.trim().replace(" ", "");
            if (info.contains(record)) {
                String[] sn = info.split(symbol);
                return sn[1];
            }
        }
        return null;
    }

    public static String executeLinuxCmd(String cmd) throws Exception {
        Runtime run = Runtime.getRuntime();
        Process process;
        process = run.exec(cmd);
        InputStream in = process.getInputStream();
//        BufferedReader bs = new BufferedReader(new InputStreamReader(in));
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[8192];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        in.close();
        process.destroy();
        return out.toString();
    }

    /**
     * 获取windows系统CPU序列
     */
    public static String getWindowsCpuId() throws Exception {
        Process process = Runtime.getRuntime().exec(
                new String[]{"wmic", "cpu", "get", "ProcessorId"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        sc.next();
        return sc.next();
    }

}
