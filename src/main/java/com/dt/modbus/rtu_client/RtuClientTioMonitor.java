package com.dt.modbus.rtu_client;

import cn.hutool.core.util.HexUtil;
import com.dt.modbus.tool.modbus_utlis.ModbusRtu;
import lombok.extern.slf4j.Slf4j;
import org.tio.client.intf.TioClientListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * Tio客户端监听器
 *
 * @author CLS
 * @since 1.0.0
 */
@Slf4j
public class RtuClientTioMonitor implements TioClientListener {

    public static boolean state;
    /*
        报文(不携带CRC)16位              01 03 00 00 00 80    01 03 00 81 00 80    01 03 00 01 01 80
        每一组读取的个数10位                    128                  128                  128
        每一组读取的起始位置10位                 0                   129                  257
        每一组读取的起始位置地址16位           00 00                00 81                01 01
    */
    String[] command = new String[]{"010300000080", "010300810080", "010300010180"};

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        if (isConnected) {
            state = true;
        }
        //报文
        while (state) {
            String msg = "01 03 00 00 01 00";
            msg = msg.replaceAll(" ", "");
            //报文转byte
            byte[] data = HexUtil.decodeHex(msg);
            //拼接：报文+CRC
            msg = msg + HexUtil.encodeHexStr(ModbusRtu.TO_CRC(data, data.length));
            RtuClientTioConfig.send(msg);//发送数据
            log.info("发送的数据：{}", msg);
            Thread.sleep(2000);
            if (!state)
                break;
        }
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {

    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {

    }

    //连接中断处理方法
    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        state = false;
    }
}
