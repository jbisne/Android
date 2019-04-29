package com.study.android.androidproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "project";
    private static final int RC_SIGN_IN = 1001;

    //Firebase - Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private static String userName;

    // Views
    private SignInButton mBtnGoogleSignIn; //로그인버튼
    private Button mBtnGoogleSignOut; //로그아웃 버튼
    private TextView mTxtProfileInfo;  //사용자 정보 표시

    //필요한 변수
    boolean back = false;
    String queryUrl;
    int choiceIndex=0;
    TextView textView1, textView2, textView3,textView10;
    ListView listView1;
    ImageView imageView,imageView1,imageView4;
    EditText editText1;
    Button button1, button2, button3; // 지역선택, 이전, 다음, 검색
    ImageButton imageButton1, imageButton2;

    ArrayList<TourDTOParcelable> mdtos =null;
    ArrayList<TourDTO> dtos=null;
    ListViewAdapter adapter;

    String city = "";
    int code = 0;
    String title;
    String addr1;
    String addr2;
    String mapx;
    String mapy;
    String overview;
    String image;
    String search;
    String contentTypeId;
    String contentId;
    double mymapx;
    double mymapy;
    static double mymapx1;
    static double mymapy1;
    static int type;

    int curPage; //현재페이지
    int totalPageNo; //전체 페이지수
    int totalCount;
    static String key = "93Z6Ex22px8HJLeS8Odm98EmEJBi6NC8tOx1DIohiebfpzqwWNfeTmjEgIdIU8jI8iDxd8VflMmYop9svQGquw%3D%3D";

    final int ctCode[] = {1,2,3,4,5,6,7,8,31,32,33,34,35,36,37,38,39};
    final String ctName[] = {"서울","인천","대전","대구","광주","부산","울산","세종","경기"
            ,"강원도","충청북도","충청남도","경상북도","경상남도","전라북도","전라남도","제주"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initFirebaseAuth();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView10 = findViewById(R.id.textView10);
        listView1= findViewById(R.id.listView1);
        imageView1 = findViewById(R.id.imageView1);
        imageView4 = findViewById(R.id.imageView4);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        editText1 = findViewById(R.id.editText1);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2= findViewById(R.id.imageButton2);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if (userName != null) {

                    contentTypeId = dtos.get(position).getContentTypeId();
                    contentId = dtos.get(position).getContentId();
                    image = dtos.get(position).getImage();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int type = 5;
                            dtos = getXmlData(city, code, type);
                        }
                    }).start();
                } else {
                    Alert.AlertShow(MainActivity.this, "구글로그인 후 이용 가능합니다.");
                }
            }
        });
    }

    private void initViews() {
        mBtnGoogleSignIn = findViewById(R.id.btn_google_signin);
        mBtnGoogleSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mTxtProfileInfo = findViewById(R.id.txt_profile_info);
        mBtnGoogleSignOut = findViewById(R.id.btn_google_signout);
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gsio = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gsio)
                .build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateProfile();
            }
        };
    }

    private void updateProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // 비 로그인 상태
            mBtnGoogleSignIn.setVisibility(View.VISIBLE);
            mBtnGoogleSignOut.setVisibility(View.GONE);
            mTxtProfileInfo.setVisibility(View.GONE);
        } else {
            // 로그인 상태
            mBtnGoogleSignIn.setVisibility(View.GONE);
            mBtnGoogleSignOut.setVisibility(View.VISIBLE);
            mTxtProfileInfo.setVisibility(View.VISIBLE);

            userName = user.getDisplayName(); // 채팅에 사용 될 닉네임 설정
            mTxtProfileInfo.setText(userName+"님");
            Log.d(TAG,userName);
        }
    }

    public void signIn() {
        Intent singIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(singIntent, RC_SIGN_IN);
    }

    public void signOut(View v){
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateProfile();
                        userName = null;
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                updateProfile();
            }
        }

        else if (requestCode == 100){
            if (resultCode == 200){

                mymapx = data.getDoubleExtra("myMapx",0);
                mymapy = data.getDoubleExtra("myMapy",0);

                Log.d(TAG, ""+mymapx);
                Log.d(TAG, ""+mymapy);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        markerXmlData1(mymapx,mymapy);
                    }
                }).start();

            }
        }
    }

    // Button을 클릭했을 때 자동으로 호출되는 callback method
    public void bOn1Click(View v){

        curPage = 1;
        totalPageNo = 1;
        totalCount = 0;

        dtos = new ArrayList<TourDTO>();
        type = 1;

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("지역 선택")
                .setSingleChoiceItems(ctName,choiceIndex, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choiceIndex = which;
                    }
                })
                .setPositiveButton("선택", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        image = "no Image";
                        city = ctName[choiceIndex];
                        code = ctCode[choiceIndex];

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dtos = getXmlData(city,code,type);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initAdapter(dtos);
                                    }
                                });
                            }
                        }).start();
                    }
                })
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this
                                ,"취소"
                                ,Toast.LENGTH_SHORT).show();
                    }
                });

        dialog.create();
        dialog.show();
    }

    public void bOn2Click(View v){  //이전페이지

        dtos = new ArrayList<>();

        Log.d(TAG,""+curPage);
        Log.d(TAG,""+totalPageNo);
        Log.d(TAG,""+totalCount);
        Log.d(TAG,""+type);
        if (type == 1) {
            if (curPage == 1) {
                Toast.makeText(MainActivity.this
                        , "첫 페이지입니다."
                        , Toast.LENGTH_SHORT).show();

            } else {
                curPage--;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dtos = getXmlData(city, code, type);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                initAdapter(dtos);
                            }
                        });
                    }
                }).start();
            }
        } else if (type == 2) {
            if (curPage == 1) {
                Toast.makeText(MainActivity.this
                        , "첫 페이지입니다."
                        , Toast.LENGTH_SHORT).show();

            } else {
                curPage--;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dtos = getXmlData(city, code, type);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                initAdapter(dtos);
                            }
                        });
                    }
                }).start();
            }
        }
    }

    public void bOn3Click(View v){ // 다음페이지

        dtos = new ArrayList<>();
        if (type == 1) {
            if (curPage == totalPageNo) {
                Toast.makeText(MainActivity.this
                        , "마지막 페이지입니다."
                        , Toast.LENGTH_SHORT).show();
            } else if (curPage < totalPageNo) {
                curPage++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dtos = getXmlData(city, code, type);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                initAdapter(dtos);
                            }
                        });
                    }
                }).start();
            }
        } else if (type == 2) {
            if (curPage == totalPageNo) {
                Toast.makeText(MainActivity.this
                        , "마지막 페이지입니다."
                        , Toast.LENGTH_SHORT).show();

            } else if (curPage < totalPageNo) {
                curPage++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dtos = getXmlData(city, code, type);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                initAdapter(dtos);
                            }
                        });
                    }
                }).start();
            }
        }
    }

    public void bOn4Click(View v){ //통합 검색

        type = 2;
        curPage = 1;
        search = editText1.getText().toString();
        if (search.length() <2 ){
            Alert.AlertShow(MainActivity.this, "2글자 이상 입력해주세요.");
        } else {
            image = "no Image";
            dtos = new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dtos = getXmlData(city, code, type);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            initAdapter(dtos);
                        }
                    });
                }
            }).start();
        }
        textView2.setText(search);
        editText1.setText("");
    }

    public void bOn5Click(View v){ // 내주변 관광지목록 마크 기능 15km로 설정

        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        int type = 9;
        intent.putExtra("type",type);
        startActivityForResult(intent,100); // 내위치 위도 경도 가져오기

    }

    public void bOn6Click(View v){ // 홈버튼 기능의 버튼

        button2.setVisibility(View.GONE);//
        button3.setVisibility(View.GONE);
        textView10.setVisibility(View.VISIBLE);
        imageButton1.setVisibility(View.VISIBLE);
        imageView4.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.GONE);//
        textView3.setVisibility(View.GONE);//
        imageButton2.setVisibility(View.GONE);
        listView1.setVisibility(View.GONE);
        back = false;
    }

    private void initAdapter(ArrayList<TourDTO> dtos){

        if (dtos.size() > 0) {
            adapter = new ListViewAdapter(this, dtos);
            listView1.setAdapter(adapter);

            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            if (type == 1) {
                textView2.setText("[" + city + "]");
            }
            textView10.setVisibility(View.GONE);
            imageButton1.setVisibility(View.GONE);
            imageView4.setVisibility(View.GONE);
            textView2.setVisibility(View.VISIBLE);
            textView3.setText("(현재페이지 :" + curPage + "/" + totalPageNo + ")");
            textView3.setVisibility(View.VISIBLE);
            imageButton2.setVisibility(View.VISIBLE);
            listView1.setVisibility(View.VISIBLE);
            back = true;
        } else {
            Alert.AlertShow(MainActivity.this, "해당하는 관광지가 없습니다.");
        }
    }

    @Override
    public void onBackPressed(){
        if (back == true){
            button2.setVisibility(View.GONE);//
            button3.setVisibility(View.GONE);
            textView10.setVisibility(View.VISIBLE);
            imageButton1.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.GONE);//
            textView3.setVisibility(View.GONE);//
            imageButton2.setVisibility(View.GONE);
            listView1.setVisibility(View.GONE);
            back = false;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("앱을 종료하시겠습니까?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("알림")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public ArrayList<TourDTO> getXmlData(String city, int code,int type) {

        try{

            if (type == 1) {
                queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=" + key + "&contentTypeId=12&areaCode=" + code +
                        "&sigunguCode=&cat1=A01&cat2=&cat3=&listYN=Y&MobileOS=AND&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo="+curPage;
            }  else if (type == 2){
                search = java.net.URLEncoder.encode(new String(search.getBytes("UTF-8")));
                queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?ServiceKey="+key+"&keyword="+search+"&areaCode=" +
                        "&sigunguCode=&cat1=A01&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo="+curPage;
            } else if (type == 5){
                queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+key +
                        "&contentTypeId="+contentTypeId+"&contentId="+contentId+"&MobileOS=AND&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y"+
                        "&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
            }

            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            int parserEvent = xpp.getEventType();

                outer:
                while (parserEvent != XmlPullParser.END_DOCUMENT) {

                    if (parserEvent == XmlPullParser.START_DOCUMENT) {
                        //XML 파싱 시작
                    } else if (parserEvent == XmlPullParser.START_TAG) {
                        String startTag = xpp.getName();

                        if (startTag.equals("body")) ;
                        else if (startTag.equals("totalCount")) {
                            if (type != 5) {
                                xpp.next();
                                String temp = xpp.getText();
                                totalCount = Integer.parseInt(temp);
                                float num = (float) totalCount / 12;
                                totalPageNo = (int) Math.ceil(num);
                                Log.d(TAG, "" + totalPageNo);

                                if (totalPageNo == 0) {
                                    totalPageNo = 1;
                                }
                            }
                        }

                        if (startTag.equals("item")) ;
                        else if (startTag.equals("addr1")) {
                            xpp.next();
                            addr1 = xpp.getText();
                        } else if (startTag.equals("addr2")) {
                            xpp.next();
                            addr2 = xpp.getText();
                        } else if (startTag.equals("firstimage")) {
                            xpp.next();
                            if (type != 5){
                                image = xpp.getText();
                            } else { }
                        } else if (startTag.equals("mapx")) {
                            xpp.next();
                            mapx = xpp.getText();
                        } else if (startTag.equals("mapy")) {
                            xpp.next();
                            mapy = xpp.getText();
                        } else if (startTag.equals("overview")) {
                            xpp.next();
                            overview = xpp.getText();
                        } else if (startTag.equals("title")) {
                            xpp.next();
                            title = xpp.getText();
                        } else if (startTag.equals("contentid")) {
                            xpp.next();
                            contentId = xpp.getText();
                        } else if (startTag.equals("contenttypeid")) {
                            xpp.next();
                            contentTypeId = xpp.getText();
                        }
                        else {

                        }

                    } else if (parserEvent == XmlPullParser.END_TAG) {
                        String endTag = xpp.getName();
                        if (endTag.equals("item")) {
                            if (type == 5) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getXmlData(contentTypeId,contentId);
                                    }
                                }).start();

                            } else {
                                TourDTO dto = new TourDTO(title, addr1, image, contentTypeId, contentId);
                                dtos.add(dto);
                                image = "no Image";
                            }

                        }
                    }

                    parserEvent = xpp.next();
                }

        } catch (Exception e){
            e.printStackTrace();
        }
        return dtos;
    }

    // 특정관광지 세부정보
    public void getXmlData(String contentTypeId, String contentId) {

        ArrayList<String> infoname = new ArrayList<>();
        ArrayList<String> infotext = new ArrayList<>();

        try{

            queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?ServiceKey=" + key + "&contentTypeId=" + contentTypeId + "&contentId=" + contentId + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";

            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            int parserEvent = xpp.getEventType();


            outer:
            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                if (parserEvent == XmlPullParser.START_DOCUMENT) {
                    //XML 파싱 시작
                } else if (parserEvent == XmlPullParser.START_TAG) {
                    String startTag = xpp.getName();

                    if (startTag.equals("item")) ;

                    else if (startTag.equals("infoname")) {
                        xpp.next();
                        String temp = xpp.getText();
                        infoname.add(temp);
                    }
                    else if (startTag.equals("infotext")) {
                        xpp.next();
                        String temp = xpp.getText();
                        infotext.add(temp);
                    }
                    else {

                    }

                } else if (parserEvent == XmlPullParser.END_TAG) {
                    String endTag = xpp.getName();
                    if (endTag.equals("item")) {

                    }
                }

                parserEvent = xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), TourSelectedActivity.class);

        intent.putExtra("title", title);
        intent.putExtra("addr1", addr1);
        intent.putExtra("addr2", addr2);
        intent.putExtra("mapx", mapx);
        intent.putExtra("mapy", mapy);
        intent.putExtra("overview", overview);
        intent.putExtra("image", image);
        intent.putExtra("contentTypeId", contentTypeId);
        intent.putExtra("contentId", contentId);
        intent.putStringArrayListExtra("infoname", infoname);
        intent.putStringArrayListExtra("infotext", infotext);
        startActivity(intent);
    }

    // customer의 위치에 따른 관광지's 좌표정보 // 총 데이터 구하기 위한 파싱
    public void markerXmlData1(double mymapx,double mymapy) {

        mymapx1 = mymapx;
        mymapy1 = mymapy;

        try{

            queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey="+key+"&contentTypeId=12" +
                    "&mapX="+mymapx1+"&mapY="+mymapy1+"&radius=10000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1";

            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            int parserEvent = xpp.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                if (parserEvent == XmlPullParser.START_DOCUMENT) {
                    //XML 파싱 시작
                } else if (parserEvent == XmlPullParser.START_TAG) {
                    String startTag = xpp.getName();

                    if (startTag.equals("body"));
                    else if (startTag.equals("totalCount")) {

                        xpp.next();
                        String temp = xpp.getText();
                        totalCount = Integer.parseInt(temp);
                    }
                }
                parserEvent = xpp.next();
            }


            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"totalcCOUNT" +totalCount);
                    markerXmlData2(totalCount,mymapx1,mymapy1);
                }
               }).start();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void markerXmlData2(int totalCount,double mymapx, double mymapy) {

        Log.d(TAG, "maker"+mymapx1);
        Log.d(TAG, "maker"+mymapy1);
        mdtos = new ArrayList<>();
        title = "";
        mapx = "";
        mapy = "";


        try{

            queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey="+key+"&contentTypeId=12" +
                    "&mapX="+mymapx1+"&mapY="+mymapy1+"&radius=10000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows="+totalCount+"&pageNo=1";

            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            int parserEvent = xpp.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                if (parserEvent == XmlPullParser.START_DOCUMENT) {
                    //XML 파싱 시작
                } else if (parserEvent == XmlPullParser.START_TAG) {
                    String startTag = xpp.getName();

                    if (startTag.equals("item")) ;
                    else if (startTag.equals("title")) {
                        xpp.next();
                        title = xpp.getText();
                    }  else if (startTag.equals("addr1")) {
                        xpp.next();
                        addr1 = xpp.getText();
                    } else if (startTag.equals("mapx")) {
                        xpp.next();
                        mapx = xpp.getText();
                    }else if (startTag.equals("mapy")) {
                        xpp.next();
                        mapy = xpp.getText();
                    }
                }

                else if (parserEvent == XmlPullParser.END_TAG) {
                    String endTag = xpp.getName();
                    if (endTag.equals("item")) {

                        TourDTOParcelable mdto = new TourDTOParcelable(title,addr1,mapx,mapy);
                        mdtos.add(mdto);

                    }
                }
                parserEvent = xpp.next();
            }
            Log.d(TAG,"mdto"+mdtos.size());

            Intent intent = new Intent(getApplicationContext(),MapActivity.class);
            intent.putExtra("type",10);
            intent.putExtra("mymapx",mymapx);
            intent.putExtra("mymapy",mymapy);
            intent.putParcelableArrayListExtra("mdtos",mdtos);
            startActivity(intent);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
