package com.dt.modbus.rtu_client;

import cn.hutool.core.util.HexUtil;
import com.dt.modbus.tool.core.CustomCommonAioHandler;
import com.dt.modbus.tool.core.SPPacket;
import com.dt.modbus.tool.hex.HexStringUtil;
import com.dt.modbus.tool.modbus_utlis.ModbusRtu;
import lombok.extern.slf4j.Slf4j;
import org.tio.client.intf.TioClientHandler;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * 客户端Handler处理
 *
 * @author CLS
 * @since 1.0.0
 */
@Slf4j
public class RtuClientTioHandler extends CustomCommonAioHandler implements TioClientHandler {
    private static final SPPacket heartbeatPacket = new SPPacket();

    /**
     * 处理消息
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        SPPacket helloPacket = (SPPacket) packet;
        byte[] msg = helloPacket.getBody();
        log.info("原始数据：{}，长度：{}", msg, msg.length);
        //去掉CRC
        byte[] removeCrc = new byte[msg.length - 2];
        System.arraycopy(msg, 0, removeCrc, 0, removeCrc.length);
        //效验crc
        String md = HexStringUtil.bytesToHexString(ModbusRtu.TO_CRC(removeCrc, removeCrc.length));
        if (HexUtil.encodeHexStr(msg).substring(HexUtil.encodeHexStr(msg).length() - 4).equals(md)) {
            //获取内容
            byte[] data = new byte[removeCrc.length - 3];
            System.arraycopy(removeCrc, 3, data, 0, data.length);
            log.info("校验码：{}，内容：{}，长度：{}\n", md, data, data.length);
        } else {
            log.warn("效验码错误：{}\n", md);
        }


    }

    /**
     * 心跳
     */
    @Override
    public SPPacket heartbeatPacket(ChannelContext channelContext) {
//        byte[] s = HexStringUtil.hexToByteArray(Const.HEARTBEAT_SEND_CONTENT);
//        heartbeatPacket.setBody(s);
        return heartbeatPacket;
    }

}
