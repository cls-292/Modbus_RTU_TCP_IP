package com.dt.modbus.config.components;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 客户端认证信息
 *
 * @author CLS
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "modbus")
public class ModbusYml {
    //========================RTU_设备信息===========================
    private String device_1;
    private Integer device_1_port;
    //========================TCP_设备信息===========================
    private String device_2;
    private Integer device_2_port;
}
