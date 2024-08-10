package com.dt.modbus.tool.core;

import lombok.SneakyThrows;
import org.tio.client.intf.TioClientHandler;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

/**
 * 解码器
 *
 * @author CLS
 * @since 1.0.0
 */
public abstract class CustomCommonAioHandler implements TioClientHandler {
    /**
     * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
     * 总的消息结构：消息头 + 消息体
     * 消息头结构：    4个字节，存储消息体的长度
     * 消息体结构：   对象的json串的byte[]
     */
    @SneakyThrows
    @Override
    public SPPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) {
        //读取消息体的长度
        int bodyLength = limit - position;
        //数据不正确，则抛出AioDecodeException异常
        if (bodyLength < 0) {
            throw new Exception("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
        }
        //收到的数据是否足够组包
        int isDataEnough = readableLength - bodyLength;
        // 不够消息体长度(剩下的buffe组不了消息体)
        if (isDataEnough < 0) {
            return null;
        } else { //组包成功
            SPPacket imPacket = new SPPacket();
            if (bodyLength > 0) {
                byte[] dst = new byte[bodyLength];
                buffer.get(dst);
                imPacket.setBody(dst);
            }
            return imPacket;
        }
    }

    /**
     * 编码：把业务消息包编码为可以发送的ByteBuffer
     * 总的消息结构：消息头 + 消息体
     * 消息头结构：    4个字节，存储消息体的长度
     * 消息体结构：   对象的json串的byte[]
     */
    @Override
    public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext) {
        SPPacket helloPacket = (SPPacket) packet;
        byte[] body = helloPacket.getBody();
        int bodyLen = 0;
        if (body != null) {
            bodyLen = body.length;
        }

        //创建一个新的bytebuffer
        ByteBuffer buffer = ByteBuffer.allocate(bodyLen);
        //设置字节序
        buffer.order(tioConfig.getByteOrder());

        //写入消息体
        if (body != null) {
            buffer.put(body);
        }
        return buffer;
    }

}
