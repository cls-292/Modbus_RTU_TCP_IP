package com.dt.modbus.rtu_client;

import com.dt.modbus.tool.core.Const;
import com.dt.modbus.tool.core.SPPacket;
import com.dt.modbus.tool.hex.HexStringUtil;
import org.tio.client.ClientChannelContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.TioClientConfig;
import org.tio.client.intf.TioClientHandler;
import org.tio.core.Node;
import org.tio.core.Tio;

/**
 * 客户端配置
 *
 * @author CLS
 * @since 1.0.0
 */
public class RtuClientTioConfig {


    private static ClientChannelContext clientChannelContext = null;

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
        TioClientHandler tioClientHandler = new RtuClientTioHandler();

        //事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
        RtuClientTioMonitor tedisClientAioListeneri = new RtuClientTioMonitor();

        //断链后自动连接的，不想自动连接请设为null
        ReconnConf reconnConf = new ReconnConf(5000L);

        //一组连接共用的上下文对象
        TioClientConfig clientTioConfig = new TioClientConfig(tioClientHandler, tedisClientAioListeneri, reconnConf);

        clientTioConfig.setHeartbeatTimeout(Const.TIMEOUT);
        TioClient tioClient = new TioClient(clientTioConfig);
        clientChannelContext = tioClient.connect(serverNode);
    }


    public static void send(String msg) {
        SPPacket packet = new SPPacket();
        packet.setBody(HexStringUtil.hexToByteArray(msg));
        Tio.send(clientChannelContext, packet);
    }
}
