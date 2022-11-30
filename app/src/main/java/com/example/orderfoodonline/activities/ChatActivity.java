package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;

import java.util.HashMap;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    EditText editMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initControl();
    }

    private void initControl() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtoFB();
            }
        });
    }

    private void sendtoFB() {
        String str_noidung = editMess.getText().toString().trim();
        if (TextUtils.isEmpty(str_noidung)){

        }else{
            HashMap<String, Objects> manage = new HashMap<>();
//            manage.put(Utils.ID_SEND, Utils.user_current.getEmail());
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.rcv_chat);
        imageView = findViewById(R.id.igSend);
        editMess = findViewById(R.id.edtnoidungchat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}