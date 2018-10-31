package com.alfarabi.alfalibs.socket;

public interface SocketListener {


    public void onConnect(SocketTransceiver transceiver);

    public void onConnectFailed();

    public void onReceive(SocketTransceiver transceiver, String s);

    public void onDisconnect(SocketTransceiver transceiver);

}
