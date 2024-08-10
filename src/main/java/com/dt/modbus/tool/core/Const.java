package com.dt.modbus.tool.core;

import org.tio.utils.time.Time;

/**
 * 通用常量工具
 *
 * @author CLS
 * @since 1.0.0
 */
public interface Const {
    String CHARSET = "utf-8";

    /**
     * 协议名字(可以随便取，主要用于开发人员辨识)
     */
    String PROTOCOL_NAME = "showcase";

    /**
     * 群组
     */
    String GROUP = "CLS_";

    /**
     * 心跳超时时间，单位：毫秒
     */
    int HEARTBEAT_TIMEOUT = -1;

    /**
     * ip数据监控统计，时间段
     *
     * @author cls
     */
    interface IpStatDuration {
        Long DURATION_1 = Time.MINUTE_1 * 5;
        Long[] IPSTAT_DURATIONS = new Long[]{DURATION_1};
    }

    /**
     * 心跳超时时间
     */
    int TIMEOUT = 0;

    String MESSAGE_ONE = "00010000007F0110";//报文(功能码10写保持寄存器)
    String NUMBER_LENGTH = "003C78";//寄存器的个数和长度
    String MESSAGE_TWO = "0001000000060105";//报文(功能码05写单个线圈)
    String DOUBLE = "DOUBLE"; //浮点数标识
    String STRING = "STRING"; //字符串数标识
    String INTEGER = "Integer,";//无符号二进制，也就是整型
    String CHARATER = "character,";//字符

    static Integer INTEGERCAPACITY(Integer number) {//长度两倍
        return number * 2;
    }
}
