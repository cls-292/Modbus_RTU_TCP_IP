package com.dt.modbus.config.components;


import cn.hutool.extra.spring.SpringUtil;


/**
 * 全局方法调用类
 *
 * @author CLS
 * @since 1.0.0
 */
public class GlobalCall {
    public static final ModbusYml modbusYml = SpringUtil.getBean(ModbusYml.class);

}
