package com.shibei.client.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shibei.client.notificationdemo.service.MyService;

public class MainActivity extends AppCompatActivity {

	private NotificationManager notificationManager;
	private MyService.DownloadBinder downloadBinder;


	private ServiceConnection serviceConnection=new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downloadBinder= (MyService.DownloadBinder) service;
			downloadBinder.startService();
			int progress = downloadBinder.getProgress();
			Toast.makeText(MainActivity.this,"当前进度"+progress,Toast.LENGTH_LONG).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(MainActivity.this,"==已经解绑==",Toast.LENGTH_LONG).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_main);
		Intent intent = getIntent();

		notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

		String flag = intent.getStringExtra("flag");
		if (flag != null) {
			notificationManager.cancel(1);
		}
	}

	public void sendNotification(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("flag", "yes");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Notification builder = new Notification.Builder(MainActivity.this).setContentTitle("重要通知")
				.setContentTitle("重要通知")
				.setContentText("这是一个重要的通知")
				.setContentIntent(pendingIntent)
				.setSmallIcon(R.mipmap.ic_launcher).build();
		notificationManager.notify(1, builder);
	}

	public void startService(View v){
		Intent startIntent=new Intent(MainActivity.this, MyService.class);
		startService(startIntent);

	}

	public void stopService(View v){
		Intent startTop=new Intent(MainActivity.this, MyService.class);
		stopService(startTop);

	}

	public void binderService(View v){
		Intent intent=new Intent(MainActivity.this,MyService.class);
		bindService(intent,serviceConnection,BIND_AUTO_CREATE);
	}

	public void unBinderService(View v){
		if(serviceConnection!=null) {
			unbindService(serviceConnection);
		}
	}

}
