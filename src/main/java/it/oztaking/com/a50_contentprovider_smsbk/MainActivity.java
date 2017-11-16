package it.oztaking.com.a50_contentprovider_smsbk;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

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
        try {
            //1.获取xmlSerializer实例
            XmlSerializer serializer = Xml.newSerializer();
            //2.设置序列化参数
            File file = new File(Environment.getExternalStorageDirectory().getPath(),"smsbackup.xml");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            serializer.setOutput(fileOutputStream,"utf-8");
            //3.写xml文档开头
            serializer.startDocument("utf-8",true);

            //4.写xml的根节点
            serializer.startTag(null,"smss");
            //5.构造uri
            Uri uri = Uri.parse("content://sms/");

            //6.点击之后获取数据
            Cursor cursor = getContentResolver().query(uri, new String[]{"address", "body", "date"}, null, null, null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    String address = cursor.getString(0);
                    String body = cursor.getString(1);
                    String date = cursor.getString(2);
                    //7.写sms节点
                    serializer.startTag(null,"sms");
                    //8.写address节点
                    serializer.startTag(null,"address");
                    serializer.text(address);
                    serializer.endTag(null,"address");
                    //9.写body节点
                    serializer.startTag(null,"body");
                    serializer.text(body);
                    serializer.endTag(null,"body");
                    //8.写date节点
                    serializer.startTag(null,"date");
                    serializer.text(date);
                    serializer.endTag(null,"date");

                    serializer.endTag(null,"sms");
                }

                serializer.endDocument();
                fileOutputStream.close();
                //System.out.println("address："+address+"--"+"body："+body+"--"+"date"+"--"+date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
