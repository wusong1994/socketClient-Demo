package com.hfocean.platform.socketClient;

import com.hfocean.platform.core.util.ByteTransformUtil;
import com.hfocean.platform.core.util.SocketUtil;
import com.hfocean.platform.core.util.StreamUtil;
import com.hfocean.platform.vo.SocketMsgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 客户端发送，服务端消息接收线程
 */
public class ClientRecvThread implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(ClientRecvThread.class);

    private Socket socket;

    private volatile boolean isStop = false;

    public ClientRecvThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //线程终止条件： 设置标志位为 true or socket 已关闭
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;
        try {
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            while (!isStop && !socket.isClosed()) {
                SocketMsgVo msgDataVo = SocketUtil.readMsgData(dataInputStream);
                log.info("客户端收到消息:{}",msgDataVo.toString());
                //相对耗时，可以开线程来处理消息，否则影响后续消息接收处理速率
                //方便测试：接收到的数据转换成16进制字符串表示法，好对比服务端发送的原始数据
                byte[] revByteArr = ByteTransformUtil.byteMerger(msgDataVo.getFrameHeader(), ByteTransformUtil.toHH(msgDataVo.getLen()));
                revByteArr = ByteTransformUtil.byteMerger(revByteArr, msgDataVo.getBody());
                revByteArr = ByteTransformUtil.byteMerger(revByteArr, msgDataVo.getFrameTail());
                String hexStr = ByteTransformUtil.bytesToHex(revByteArr);
                log.info("客户端收到服务端原样16进制字符串:{}", hexStr.toUpperCase());
            }
        } catch (IOException e) {
            log.error("客户端接收消息发生异常");
            e.printStackTrace();
        } finally {
            this.isStop = true;
            log.info("客户端旧接收线程已摧毁");
            StreamUtil.closeInputStream(dataInputStream);
            StreamUtil.closeInputStream(inputStream);
            SocketUtil.closeSocket(socket);
        }

    }

    public boolean getStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
