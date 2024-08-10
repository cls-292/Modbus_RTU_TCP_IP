package com.dt.modbus.tcp_client;

import lombok.extern.slf4j.Slf4j;
import org.tio.client.intf.TioClientListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * Tio监听器
 *
 * @author CLS
 * @since 1.0.0
 */
@Slf4j
public class TcpClientTioMonitor implements TioClientListener {
    private boolean state = true;

    /**
     * 连接后开始采集数据
     *
     * @param isConnected 是否连接成功,true:表示连接成功，false:表示连接失败
     * @param isReconnect 是否是重连, true: 表示这是重新连接，false: 表示这是第一次连接
     */
    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        if (isConnected) {
            while (state) {
                TcpClientTioConfig.send(channelContext, "00 0A 00 00 00 06 01 03 00 00 00 64");
                Thread.sleep(1000);
            }
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

    /**
     * 连接中断处理
     *
     * @param channelContext the channelcontext
     * @param throwable      the throwable 有可能为空
     * @param remark         the remark 有可能为空
     */
    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        state = false;
    }
}
