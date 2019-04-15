// {...}등 되있는거 카메라에서 전부 코드 그대로 갖다쓴거니 참조.

// 단점이 리스트가 제공이안됨. 파이어베이스랑 연동을 해주어야함.
// 그럼 별도의 서버없이 이미지 보고 로드하고 할수있는 장점.


package com.study.android.ex57_firebasestorage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";

    private String[] permission = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA };
    private static final int MULTIPLE_PERMISSIONS = 101;

    private RadioGroup radioGroup;
    private ImageView imageView;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        initProgram();
    }

    private boolean checkPermissions()
    {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permission)
        {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED)
            {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty())
        {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MULTIPLE_PERMISSIONS:
            {
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < permissions.length; i++)
                    {
                        if (permissions[i].equals(this.permission[0]))
                        {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                            {
                                showNoPermissionToastAndFinish();
                            }
                        }
                        else if (permissions[i].equals(this.permission[1]))
                        {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                            {
                                showNoPermissionToastAndFinish();
                            }
                        }
                        else if (permissions[i].equals(this.permission[2]))
                        {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                            {
                                showNoPermissionToastAndFinish();
                            }
                        }
                    }
                }
                else
                {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish()
    {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다.",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    public void initProgram() {
        radioGroup = findViewById(R.id.radioGroup);
        imageView = findViewById(R.id.imageView);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int imageNum;
                switch (checkedId) {
                    default:
                    case R.id.radio0:
                        imageNum = 1;
                        break;
                    case R.id.radio1:
                        imageNum = 2;
                        break;
                    case R.id.radio2:
                        imageNum = 3;
                        break;
                    case R.id.radio3:
                        imageNum = 4;
                        break;
                }

                printImageList();
                downloadImage(imageNum);
            }
        });
    }

    public void downloadImage(int imageNum) {
        String folderName = "images";
        String imageName = String.format("image%d.png", imageNum);
        // "" 안에 대소문자 주의, 한번 Image로해놔서 오류났었음.

        // Storage 이미지 다운로드 경로
        String storagePath = folderName + "/" + imageName;

        StorageReference imageRef = mStorageRef.child(storagePath);

        try {
            // Storage 에서 다운받아 저장시킬 임시파일
            final File imageFile = File.createTempFile("images", "jpg");
            imageRef.getFile(imageFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Success Case
                            Bitmap bitmapImage = BitmapFactory.decodeFile(imageFile.getPath());
                            imageView.setImageBitmap(bitmapImage);
                            Toast.makeText(getApplicationContext(), "Success !!", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail Case
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Fail !!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printImageList()
    {
        // 별도의 메서드가 없다.
        // 리얼타임 데이터베이스에 저장하고 불러오는 방법이 있다.
    }

    public void onButtonClicked(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);
        finish();
    }
}
