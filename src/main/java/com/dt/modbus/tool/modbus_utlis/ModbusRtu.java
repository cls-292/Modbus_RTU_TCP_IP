package com.dt.modbus.tool.modbus_utlis;

/**
 * modbus rtu工具
 *
 * @author CLS
 * @since 1.0.0
 */
public class ModbusRtu {

    /**
     * 用来效验CRC
     *
     * @param byteData 去掉CRC的数据
     * @param Length   数据长度
     */
    public static byte[] TO_CRC(byte[] byteData, int Length) {
        byte[] CRC = new byte[2];
        char wCrc = 0xFFFF;
        for (int i = 0; i < Length; i++) {
            wCrc ^= byteData[i];
            for (int j = 0; j < 8; j++) {
                if ((wCrc & 0x0001) == 1) {
                    wCrc >>= 1;
                    wCrc ^= 0xA001;//异或多项式
                } else {
                    wCrc >>= 1;
                }
            }
        }
        CRC[1] = (byte) ((wCrc & 0xFF00) >> 8);//高位在后,低位在前,我这里面已经转过来了
        CRC[0] = (byte) (wCrc & 0x00FF);
        return CRC;
    }

    public static void main(String[] args) {
//        byte[] data = HexUtil.decodeHex("010300000003");
//        byte[] crc_result = ModbusRtu.TO_CRC(data, data.length);
//        System.err.println(HexUtil.encodeHexStr(crc_result));
    }
}
