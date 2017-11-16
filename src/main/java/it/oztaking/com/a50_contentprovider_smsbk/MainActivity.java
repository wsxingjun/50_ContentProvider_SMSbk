package it.oztaking.com.a50_contentprovider_smsbk;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_SMSBk = (Button) findViewById(R.id.bt_SMSBk);
        bt_SMSBk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("content://sms/");
        //点击之后获取数据
        Cursor cursor = getContentResolver().query(uri, new String[]{"address", "body", "date"}, null, null, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String address = cursor.getString(0);
                String body = cursor.getString(1);
                String date = cursor.getString(2);
                System.out.println("address："+address+"--"+"body："+body+"--"+"date"+"--"+date);
            }
        }
    }
}
