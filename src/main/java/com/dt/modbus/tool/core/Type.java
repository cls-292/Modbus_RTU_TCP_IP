package com.dt.modbus.tool.core;

/**
 * 消息类型长度定义
 *
 * @author CLS
 * @since 1.0.0
 */
public interface Type {

    /**
     * 其他
     */
    byte REST_REQ = 3;

    /**
     * #
     */
    byte HINT_REQ = 2;
    /**
     * @
     */
    byte SYMBOL_REQ = 1;

}
