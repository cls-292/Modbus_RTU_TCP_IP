package com.dt.modbus.tool.core;

import org.tio.core.intf.Packet;

import java.io.Serial;

/**
 * 重写Packet
 *
 * @author CLS
 * @since 1.0.0
 */
public class SPPacket extends Packet {
    @Serial
    private static final long serialVersionUID = -5481926483435771100L;
    //消息头的长度 4
    public static final int HEADER_LENGTH = 4;
    public static final String CHARSET = "GBK";

    private byte[] body;

    public SPPacket() {
        super();
    }

    /**
     * @author tanyaowu
     */
    public SPPacket(byte[] body) {
        super();
        this.body = body;
    }

    /**
     * @return the body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

}
