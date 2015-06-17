package ray.cyberpup.com.intentservicedrill;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created on 6/17/15
 *
 * @author Raymond Tong
 */
public class MyIntentService extends IntentService {

    public static final String BROADCAST_ACTION = "ray.cyberpup.com.BROADCAST";
    public static final String STATUS = "ray.cyberpup.com.STATUS";


    public MyIntentService() {
        super("Ray");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        System.out.println("handling intent in service");
        int count = 0;
        //Perform Prime Calculation
        for (int i = 2; i < 5000000; i++)
            if (isPrime(i))
                count++;


        sendStatus(count);


    }

    private void sendStatus(int result) {
        Intent localIntent = new Intent(BROADCAST_ACTION).putExtra(STATUS, result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        System.out.println("sending result");
    }

    boolean isPrime(int N) {
        int i = 2;
        while (i * i < N) {
            if (N % i == 0)
                return false;

            i++;
        }
        return true;
    }

}
