package com.hfocean.platform.core.util;

import com.hfocean.platform.core.constant.ReceiveDataFrameConstant;
import com.hfocean.platform.vo.SocketMsgVo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketUtil {

    public static Socket createClientSocket(String host, int port) throws IOException {
        Socket socket = new Socket(host,port);
        return socket;
    }

    public static void closeSocket(Socket socket) {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeMsgData(DataOutputStream dataOutputStream, SocketMsgVo msgDataVo) throws IOException {
        /*msgDataVo.setFrameHeader(SocketMsgVo.RECEIVE_FRAME_HEADER);
        msgDataVo.setLen(0);
        msgDataVo.setBody(null);
        msgDataVo.setFrameTail(SocketMsgVo.RECEIVE_FRAME_TAIL);*/
        dataOutputStream.write(msgDataVo.getFrameHeader());
        dataOutputStream.writeInt(msgDataVo.getLen());
        if (msgDataVo.getBody() != null) {
            dataOutputStream.write(msgDataVo.getBody());
        }
        dataOutputStream.write(msgDataVo.getFrameTail());
        dataOutputStream.flush();
    }

    public static SocketMsgVo readMsgData(DataInputStream dataInputStream) throws IOException {
        byte[] frameHeader = new byte[ReceiveDataFrameConstant.RECEIVE_FRAME_HEADER_LENGTH];
        dataInputStream.read(frameHeader);
        int len = dataInputStream.readInt();
        byte[] body = new byte[len];
        dataInputStream.read(body);
        byte[] frameTail = new byte[ReceiveDataFrameConstant.RECEIVE_FRAME_TAIL_LENGTH];
        dataInputStream.read(frameTail);
        System.out.println("获取的数据长度为：" + len);
        SocketMsgVo msgDataVo = new SocketMsgVo();
        msgDataVo.setFrameHeader(frameHeader);
        msgDataVo.setBody(body);
        msgDataVo.setLen(len);
        msgDataVo.setFrameTail(frameTail);
        return msgDataVo;
    }
}
