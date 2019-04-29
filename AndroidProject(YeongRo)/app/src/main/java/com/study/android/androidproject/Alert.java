package com.study.android.androidproject;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class Alert {

	public static void AlertShow(Context context, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("\n"+msg+"\n");
        builder.setPositiveButton("확인", null);

        AlertDialog alert = builder.create();
        alert.show();

		// AlertDialog 의  가운데 정렬을 위한.. setting
		// Must call show() prior to fetching text view
		TextView messageView = alert.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
    }

}
