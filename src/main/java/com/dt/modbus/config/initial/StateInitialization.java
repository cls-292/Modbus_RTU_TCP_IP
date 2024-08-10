package com.dt.modbus.config.initial;

import com.dt.modbus.rtu_client.RtuClientTioConfig;
import com.dt.modbus.tcp_client.TcpClientTioConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.dt.modbus.config.components.GlobalCall.modbusYml;

/**
 * 初始化
 *
 * @author CLS
 * @since 1.0.0
 */
@Slf4j
@Component
public class StateInitialization implements CommandLineRunner {

    @Override
    public void run(String... args) {
        try {
            /*初始化RTU客户端*/
            RtuClientTioConfig.start(modbusYml.getDevice_1(), modbusYml.getDevice_1_port());
            /*初始化TCP客户端*/
            TcpClientTioConfig.start(modbusYml.getDevice_2(), modbusYml.getDevice_2_port());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
