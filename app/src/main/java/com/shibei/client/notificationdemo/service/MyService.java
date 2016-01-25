package com.shibei.client.notificationdemo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shibei.client.notificationdemo.MainActivity;
import com.shibei.client.notificationdemo.R;

/**
 * Created by xielingyong on 16/1/18.
 */
public class MyService extends Service{

	private static final String TAG="MyService";
	private DownloadBinder downloadBinder=new DownloadBinder();

	public class DownloadBinder extends Binder{
		public void startService(){
			Log.d(TAG,"====start Download====启动下载");
		}

		public int getProgress(){

			Log.d(TAG,"====getProgress====当前进度");

			return 10;
		}
	}
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "===onBind===");
		return downloadBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("flag", "yes");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Notification builder = new Notification.Builder(this).setContentTitle("重要服务")
				.setContentText("这是一个重要的服务")
				.setContentIntent(pendingIntent)
				.setSmallIcon(R.mipmap.ic_launcher).build();
		startForeground(1,builder);
		Log.d(TAG, "===onCreate===");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "===onStartCommand===");
		return super.onStartCommand(intent, flags, startId);

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "===onDestroy===");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "===onUnbind===");
		return super.onUnbind(intent);
	}
}
