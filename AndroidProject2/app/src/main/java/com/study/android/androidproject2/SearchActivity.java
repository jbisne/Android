package com.study.android.androidproject2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class SearchActivity extends AppCompatActivity
{

    EditText editText;
    TextView textView;

    XmlPullParser xpp;
    String key="707a45486e6a6973373541554f4365";
    // 서울데이터광장 인증키

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.result);
    }

    public void onBtnClicked(View v)
    {
        switch (v.getId())
        {
            case R.id.button:

            // Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    data = getXmlData();
                    //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                    //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                    //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            textView.setText(data);
                            //TextView에 문자열  data 출력
                        }
                    });
                }
            }).start();
        break;
        }
    }
/////////////////// 버튼클릭 메서드 /////////////////\

    //XmlPullParser를 이용하여 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    String getXmlData()
    {
        StringBuffer buffer = new StringBuffer();

        String str = editText.getText().toString(); //EditText에 작성된 Text얻어오기
        String location = URLEncoder.encode(str); //한글의 경우 인식이 안되기에 utf-8로 encoding

        String queryUrl="http://openapi.seoul.go.kr:8088/707a45486e6a6973373541554f4365/" +
                "xml/GeoInfoShelterWGS/1/694" // 요청 URL
                +"ADR_NAM=" +location
                +"707a45486e6a6973373541554f4365=" + key;

        try
        {
            URL url = new URL(queryUrl);  //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 테그 이름 얻어오기

                        if(tag.equals("row")); // 첫번째 검색결과
                        else if(tag.equals("OBJECTID"))
                        {
                            buffer.append("고유번호 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("SHUNT_NAM"))
                        {
                            buffer.append("대피소명칭 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("ADR_NAM"))
                        {
                            buffer.append("소재지 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("HOU_CNT_M"))
                        {
                            buffer.append("최대수용인원 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("HOU_CNT_C"))
                        {
                            buffer.append("현재수용인원 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("OPR_YN"))
                        {
                            buffer.append("n현재운영여부 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("TEL_NO_CN"))
                        {
                            buffer.append("전화번호 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("HJD_CDE"))
                        {
                            buffer.append("행정동코드 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("HJD_NAM"))
                        {
                            buffer.append("행정동명칭 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("SHUNT_LVL"))
                        {
                            buffer.append("대피단계 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("REMARK"))
                        {
                            buffer.append("비고 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("LNG"))
                        {
                            buffer.append("경도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("LAT"))
                        {
                            buffer.append("위도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 테그 이름 얻어오기

                        if(tag.equals("row")) buffer.append("\n");
                        // 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType = xpp.next();
            }
        }
        catch (Exception e)
        {

        }

        buffer.append("파싱 끝\n");
        return buffer.toString(); //StringBuffer 문자열 객체 반환

    }
/////////////// getXmlData 메서드 ////////////////////
}
