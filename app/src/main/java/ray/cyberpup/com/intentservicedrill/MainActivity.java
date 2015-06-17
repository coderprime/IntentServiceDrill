package ray.cyberpup.com.intentservicedrill;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created on 6/17/15
 *
 * @author Raymond Tong
 */
public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_ACTION = "ray.cyberpup.com.BROADCAST";
    public static final String STATUS = "ray.cyberpup.com.STATUS";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serviceIntent = new Intent(this, MyIntentService.class);
        startService(serviceIntent);



    }
    ResponseReceiver mResponseReceiver;
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter mStatusIntentFilter = new IntentFilter(BROADCAST_ACTION);
        try {
            mStatusIntentFilter.addDataType(STATUS);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }

        mResponseReceiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mResponseReceiver,
                mStatusIntentFilter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mResponseReceiver);

    }

    PendingIntent resultPendingIntent;
    private class ResponseReceiver extends BroadcastReceiver {

        private ResponseReceiver() {

        }
        String msg;
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra(STATUS, -1);
            System.out.println("result received "+result);
            if(result!=-1){
                // Issue Notification result
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                 resultPendingIntent= PendingIntent
                        .getActivity(getApplicationContext(), 0,
                                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                msg = "# of primes found: "+result;

            }

            else{
                msg = "Error No primes found.";
            }


            NotificationCompat.Builder n = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle("Primes")
                    .setContentText(msg)
                    .setContentIntent(resultPendingIntent)
                    .setSmallIcon(R.drawable.notification_template_icon_bg);


            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, n.build());

        }
    }
}

