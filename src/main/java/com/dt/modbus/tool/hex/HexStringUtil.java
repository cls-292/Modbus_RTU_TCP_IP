package com.dt.modbus.tool.hex;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 十六进制常用工具
 *
 * @author CLS
 * @since 1.0.0
 */
public class HexStringUtil {

    /**
     * 将 英文，日文，中文转成十六进制
     */
    public static String changeStringToHex(final String inputString) {
        int changeLine = 1;
        String s = "Convert a string to HEX/こんにちは/你好";
        if (inputString != null) {
            s = inputString;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            byte[] ba = s.substring(i, i + 1).getBytes();
            // & 0xFF for preventing minus
            String tmpHex = Integer.toHexString(ba[0] & 0xFF);

            changeLine++;
            // Multiply byte according
            if (ba.length == 2) {
                tmpHex = Integer.toHexString(ba[1] & 0xff);
//                System.out.print("0x" + tmpHex.toUpperCase());
//                System.out.print(" ");
                changeLine++;
            }

            sb.append(tmpHex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转化为字节流数
     *
     * @param inHex 要转16进制字节流数组的16进制字符
     * @return 16进制字节流数
     */
    public static byte[] hexToByteArray(String inHex) {

        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    private static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    /**
     * 16进制字节数组返回16进制字符
     *
     * @param bytes 要被转为16进制字符串的16进制字节流数组
     * @return 返回16进制字符
     */
    public static String bytesToHexString(byte[] bytes) {
        char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // �?个byte�?8位，可用两个十六进制位标�?
        char[] buf = new char[bytes.length * 2];
        int a;
        int index = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

    // 16进制转普通字符串
    public static String sixTeenHexToString(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }


    //数组copy【接收返回的16进制字节流，用于16进制字符串

    /**
     * 数组copy 【接收返回的16进制字节流，用于16进制字符串
     *
     * @param src   服务端返回的源数
     * @param begin 起始位置
     * @param count 写入长度
     * @return 返回接收服务端返回数16进制字节流数
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    /**
     * 过滤字符
     */
    public static String replaceWrongUnicode(String source, String replace) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        if (StringUtils.isBlank(replace)) {
            replace = "";
        }
        Pattern CRLF = Pattern.compile("([&#xa0;➤▶★■◆■◆♧♡◓◒♠♣♥❤☜☞☎☏⊙◎◚◛◜▧▨♨◐◑↔↕▪ ▒ ◊◦▣▤▥▦▩◘◈◇♬♪♩♭♪の◐◑→あぃ￡Ю〒§♤♥▶¤๑⊹⊱⋛⋌⋚⊰⊹≈๑۩۩.. ..۩۩๑๑۩۞۩๑✱❇✾➹ ~.~‿☀☂☁┱┲❣✚✩✣✤✥✦❈❥❦❧❂❁❀✿✄☪☣☢☠☭ღღღ▶▷◀◁☀☁☂☃☄◐◑☇☈⊙☊☋☌☍ⓛⓞⓥⓔ╬『』∴☀ . ｡◕‿◕｡♨♬♩♭♧◑∷﹌の◐◎▶☺◛►◄▧▨♨◐◑ ↔ ↕↘◜▀▄█▌░▒▬♦◊ ☜☞▐░▒▬♦◊◦ ◜♧の◑→♧ぃ￡❤｡◕‿◕｡✌✟ஐ♧♬๑•ิ.•ิ๑♠♣✖♥►◄↔↕▪▫◘◙の◑→あぃ￡❤｡◕‿�▲×●]|(&hellip;)|(&middot;)|(&nbsp;)|[\\u0000-\\u0019]|[\\u001A-\\u001F]|[\\u001a-\\u001f]|[\\u007f-\\u009f]|\\u00ad|[\\u0483-\\u0489]|[\\u0559-\\u055a]|\\u058a|[\\u0591-\\u05bd]|\\u05bf|[\\u05c1-\\u05c2]|[\\u05c4-\\u05c7]|[\\u0606-\\u060a]|[\\u063b-\\u063f]|\\u0674|[\\u06e5-\\u06e6]|\\u070f|[\\u076e-\\u077f]|\\u0a51|\\u0a75|\\u0b44|[\\u0b62-\\u0b63]|[\\u0c62-\\u0c63]|[\\u0ce2-\\u0ce3]|[\\u0d62-\\u0d63]|\\u135f|[\\u200b-\\u200f]|[\\u2028-\\u202e]|\\u2044|\\u2071|[\\uf701-\\uf70e]|[\\uf710-\\uf71a]|\\ufb1e|[\\ufc5e-\\ufc62]|\\ufeff|\\ufffc)");
        Matcher m = CRLF.matcher(source);
        if (m.find()) {
            return m.replaceAll(replace);
        }
        return source;
    }


}
