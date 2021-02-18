package com.alfarabi.alfalibs.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.alfarabi.alfalibs.socket.SocketInstance;
import com.alfarabi.alfalibs.socket.SocketListener;

import lombok.Getter;
import lombok.Setter;


public abstract class SocketService extends Service {

    @Getter@Setter protected SocketInstance socketInstance ;

    public void onCreate(String host, int port) {
        super.onCreate();
        socketInstance = SocketInstance.create(host, port);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        return Service.START_NOT_STICKY;
    }

    public void listen(SocketListener socketListener){
        if(socketInstance!=null){
            socketInstance.listen(socketListener);
            socketInstance.connect();
        }
    }

}
