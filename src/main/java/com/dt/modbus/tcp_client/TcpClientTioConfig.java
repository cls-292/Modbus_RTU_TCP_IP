package com.dt.modbus.tcp_client;

import com.dt.modbus.tool.core.Const;
import com.dt.modbus.tool.core.SPPacket;
import com.dt.modbus.tool.hex.HexStringUtil;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.TioClientConfig;
import org.tio.client.intf.TioClientHandler;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.Tio;

/**
 * TCP客户端配置
 *
 * @author CLS
 * @since 1.0.0
 */
public class TcpClientTioConfig {

    /**
     * Modbus TCP/IP客户端启动
     *
     * @param service
     * @param port
     */
    public static void start(String service, Integer port) throws Exception {
        //服务器节点
        Node serverNode = new Node(service, port);

        //handler, 包括编码、解码、消息处理
        TioClientHandler tioClientHandler = new TcpClientTioHandler();

        //事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
        TcpClientTioMonitor tedisClientAioListeneri = new TcpClientTioMonitor();

        //断链后自动连接的，不想自动连接请设为null
        ReconnConf reconnConf = new ReconnConf(5000L);

        //一组连接共用的上下文对象
        TioClientConfig clientTioConfig = new TioClientConfig(tioClientHandler, tedisClientAioListeneri, reconnConf);

        clientTioConfig.setHeartbeatTimeout(Const.HEARTBEAT_TIMEOUT);
        TioClient tioClient = new TioClient(clientTioConfig);
        tioClient.connect(serverNode);
    }

    /**
     * 发送信息
     *
     * @param msg
     */
    public static void send(ChannelContext clientChannelContext, String msg) {
        msg = msg.replaceAll(" ", "");
        SPPacket packet = new SPPacket();
        packet.setBody(HexStringUtil.hexToByteArray(msg));
        Tio.send(clientChannelContext, packet);
    }

}
