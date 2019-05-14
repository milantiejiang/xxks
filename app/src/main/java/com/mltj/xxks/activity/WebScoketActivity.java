package com.mltj.xxks.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mltj.xxks.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;

public class WebScoketActivity extends BasiceActivity {
    @BindView(R.id.btn_connect)
    Button btn_connect;

    Handler handler=new Handler()
    {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(WebScoketActivity.this, msg.getData().getString("info"),Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected int initLayout() {
        return R.layout.activity_web_scoket;
    }

    @Override
    protected void init() {
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URI uri=null;
                try {
                    uri = new URI("ws://192.168.1.180:8889");
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//                WebSocketWorker webSocketWorker=new WebSocketWorker(uri, new Draft_17());
                try {
                    int i=0;
                    while(true){
                        WebSocketWorker webSocketWorker=new WebSocketWorker(uri, new Draft_17());
                        if(!webSocketWorker.connectBlocking()){
                            Log.e("TAG","测试结束");
                            break;
                        }else {
                            Log.e("TAG",i+++"");
                        }
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//                webSocketWorker.send("text");
            }
        });
    }

    private class WebSocketWorker extends WebSocketClient {

        public WebSocketWorker(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }

        @Override
        public void onClose(int arg0, String arg1, boolean arg2) {
            // TODO Auto-generated method stub

        }
        @Override
        public void onError(Exception arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onMessage(String arg0) {
            // TODO Auto-generated method stub
            Bundle bundle=new Bundle();
            bundle.putString("info",arg0);
            Message message=new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }


        @Override
        public void onOpen(ServerHandshake arg0) {
            // TODO Auto-generated method stub

        }

    }
}
