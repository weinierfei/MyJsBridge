package com.dragon.jsbridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Description:
 *
 * @author: guoyongping
 * @date: 2017/3/22 21:13
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String value = intent.getStringExtra("param");
        String userName = intent.getStringExtra("userName");
        String password = intent.getStringExtra("password");
        Toast.makeText(this, "h5说: userName="+userName +"    password="+password +  "       中文测试="+value  , Toast.LENGTH_LONG).show();
    }
}
