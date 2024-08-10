package com.dt.modbus.tcp_client;

import com.dt.modbus.tool.core.CustomCommonAioHandler;
import com.dt.modbus.tool.core.SPPacket;
import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * @author CLS
 * @since 1.0.0
 */
@Slf4j
public class TcpClientTioHandler extends CustomCommonAioHandler {
    /**
     * 处理消息
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) {
        SPPacket helloPacket = (SPPacket) packet;
        byte[] msg = helloPacket.getBody();
        log.info("接收的数据为：{}", msg);
    }

    /**
     * 心跳
     */
    @Override
    public SPPacket heartbeatPacket(ChannelContext channelContext) {
        return null;
    }

}
