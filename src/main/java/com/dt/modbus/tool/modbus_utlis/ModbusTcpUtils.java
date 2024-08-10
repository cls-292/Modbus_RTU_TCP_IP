package com.dt.modbus.tool.modbus_utlis;

import com.dt.modbus.tool.core.Const;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * modbus工具类
 *
 * @author CLS
 * @since 1.0.0
 */
public class ModbusTcpUtils {

    /**
     * 解析开关量
     *
     * @param response
     * @param number
     * @return
     */
    public static boolean[] dataDispose(byte[] response, Integer number) {
        boolean[] result = new boolean[number];
        for (int i = 0; i < response[8]; i++) { //循环解析结果，共有response[8]个数据，循环9次
            result[i * 8] = (response[9 + i] & 0x01) == 0x01;//将结果从解析结果[0]解析结果[71]个开关量
            result[i * 8 + 1] = (response[9 + i] & 0x02) == 0x02;
            result[i * 8 + 2] = (response[9 + i] & 0x04) == 0x04;
            result[i * 8 + 3] = (response[9 + i] & 0x08) == 0x08;
            result[i * 8 + 4] = (response[9 + i] & 0x10) == 0x10;
            result[i * 8 + 5] = (response[9 + i] & 0x20) == 0x20;
            result[i * 8 + 6] = (response[9 + i] & 0x40) == 0x40;
            result[i * 8 + 7] = (response[9 + i] & 0x80) == 0x80;
        }
        return result;
    }

    /**
     * 解析数据字符和16位无符号二进制
     */
    public static Object instFloatStringData(byte[] response, String type) {
        switch (type) {
            case Const.CHARATER -> {       //字符
                byte[] stringData = new byte[response.length];
                int char_len = 0; //定义字符长度变量，该数值为字符的长度。
                for (int i = 0; response[9 + i] > 0; i++) { //循环判断每个字符，遇到00则说明字符已完，后面为空，同时计算字符长度。
                    if (response[9 + i] != 0) {
                        stringData[i] = response[9 + i];
                        char_len = i + 1;
                    }
                }

                //按照刚才循环的结果，将临时数组里的字符转至新数组temp1，新数组的长度刚好是字符的长度
                byte[] temp1 = new byte[char_len];
                System.arraycopy(stringData, 0, temp1, 0, char_len);
                return new String(temp1);
            }
            case Const.INTEGER -> {     //16位无符号 二进制
                int[] result = new int[Const.INTEGERCAPACITY(response.length)];
                for (int i = 0; i < response[8] / 2; i++) {
                    result[i] = ((response[i * 2 + 9] << 8) + response[i * 2 + 10]);
                }
                return result;
            }
        }
        return null;
    }

    /**
     * 保留四五小数
     * DecimalFormat对数值格式化的舍入RoundingModeF
     */
    public static String roundoutFloat(Float data) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(data);
    }

    public static String sortingAnalysis(String msg) {
        //按位截取存到list中
        List<String> list = ModbusTcpUtils.subStr(msg.substring(18), 8);
        for (String s : list) {
            if (s != null) {
                //每八个截取解析
                float floatData = Float.intBitsToFloat(Integer.parseInt(s, 16));
                //DecimalFormat对数值格式化的舍入RoundingMode
                String resultFloat = ModbusTcpUtils.roundoutFloat(floatData);
                if (!(floatData == 0.0)) {
                    return resultFloat;
                }
                break;
            }
        }
        return null;
    }

    /**
     * 截取字符串存到List中
     *
     * @param str 原字符串
     * @param sub 要截取的长度
     * @return list返回集合包括截取的部分与剩余的部分
     */
    public static List<String> subStr(String str, int sub) {
        List<String> list = new ArrayList<>();
        String streee;
        int len = str.length();
        if (len % sub != 0) {
            for (int i = 0; i < str.length() - (len % sub); i += sub) {
                streee = str.substring(i, i + sub);
                list.add(streee);
            }
            String tpm = str.substring(str.length() - (len % sub));
            list.add(tpm);
        } else {
            for (int i = 0; i < str.length(); i += sub) {
                streee = str.substring(i, i + sub);
                list.add(streee);
            }
        }
        return list;
    }
}
