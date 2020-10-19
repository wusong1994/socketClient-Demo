package com.hfocean.platform.vo;

import java.util.Arrays;

/**
 * @desc dcpr推送的数据包格式： 帧头（4Byte）| 长度（4Byte）| 数据（xxByte）| 帧尾（4Byte）
 * @time 2020/10/16
 * @author ws
 */
public class SocketMsgVo {

    private byte[] frameHeader;  //帧头-4个字节
    private int len; //长度
    private byte[] body; //数据部分
    private byte[] frameTail; //帧尾

    public byte[] getFrameHeader() {
        return frameHeader;
    }

    public void setFrameHeader(byte[] frameHeader) {
        this.frameHeader = frameHeader;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getFrameTail() {
        return frameTail;
    }

    public void setFrameTail(byte[] frameTail) {
        this.frameTail = frameTail;
    }

    @Override
    public String toString() {
        return "SocketMsgVo{" +
                "frameHeader=" + Arrays.toString(frameHeader) +
                ", len=" + len +
                ", body=" + Arrays.toString(body) +
                ", frameTail=" + Arrays.toString(frameTail) +
                '}';
    }
}
