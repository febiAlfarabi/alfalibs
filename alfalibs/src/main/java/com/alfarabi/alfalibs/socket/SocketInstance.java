package com.alfarabi.alfalibs.socket;

import com.alfarabi.alfalibs.service.SocketService;

public class SocketInstance {

    static SocketInstance socketInstance ;

    public static String HOST ;
    public static int PORT ;
    private TcpClient tcpClient ;

    public static SocketInstance create(String hostIP, int port){
        SocketInstance.HOST = hostIP ;
        SocketInstance.PORT = port ;
        if(socketInstance==null){
            socketInstance = new SocketInstance();
        }

        return socketInstance ;
    }


    SocketListener socketListener ;
    public void listen(SocketListener socketListener){
        this.socketListener = socketListener ;
    }

    public void connect(){
        tcpClient = new TcpClient() {
            @Override
            public void onConnect(SocketTransceiver transceiver) {
                if(socketListener!=null){
                    socketListener.onConnect(transceiver);
                }
            }

            @Override
            public void onConnectFailed() {
                if(socketListener!=null){
                    socketListener.onConnectFailed();
                }
            }

            @Override
            public void onReceive(SocketTransceiver transceiver, String s) {
                if(socketListener!=null){
                    socketListener.onReceive(transceiver, s);
                }
            }

            @Override
            public void onDisconnect(SocketTransceiver transceiver) {
                if(socketListener!=null){
                    socketListener.onDisconnect(transceiver);
                }
            }
        };
        tcpClient.connect(SocketInstance.HOST, SocketInstance.PORT);
    }


    public void disconnect(){
        if(tcpClient!=null){
            tcpClient.disconnect();
            tcpClient = null ;
        }
    }



}
