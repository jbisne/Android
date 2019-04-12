package com.study.android.ex56.firebasecloud;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.study.android.ex56.firebasecloud.R;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private static  final String TAG = "lecture";

    int nMax = 0;

    FirebaseFirestore db;

    EditText etName;
    EditText etAge;
    EditText etNum;
    TextView tvContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etNum = findViewById(R.id.etNum);
        tvContents = findViewById(R.id.tvContents);

        db = FirebaseFirestore.getInstance();

        dataQuery();
    }

    public void onBtn1Clicked(View v) {
        dataInsert();
    }

    public void onBtn2Clicked(View v) {
        String sDelete = etNum.getText().toString();
        dataDelete(sDelete);
    }

    public void dataQuery() {
        // 서버에서 일단 가져온 후에도 서버에서 데이터가 변경되면
        // 폰의 화면에서도 실시간으로 자동 갱신 된다.
        int LIMIT = 30;
        db.collection("MyFirestoreDB")


                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.d(TAG, "Listen failed.", e);
                            return;
                        }

                        String sMax = "0";
                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("name") != null) {

                                sMax = doc.getId();
                                sb.append(sMax + " / " +
                                        doc.getString("name") + " / " +
                                        doc.getString("age") + "\n");
                            }
                        }
                        nMax = Integer.parseInt(sMax);

                        tvContents.setText(sb.toString());
                    }
                });
    }

    public void dataInsert() {
        // 데이터준비
        Map<String ,Object> quote = new HashMap<>();
        quote.put("name", etName.getText().toString());
        quote.put("age", etAge.getText().toString());

        String newCount = String.format("%03d" , nMax +1);

        db.collection("MyFirestoreDB")

                .document(newCount)
                .set(quote)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, " Error writing document", e);
                    }
                });
    }

    public void dataDelete(String sDelete) {
        // 삭제를 위해서는 해당 아이템의 문서명이 필요하다.
        db.collection("MyFirestoreDB")
                .document(sDelete)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}