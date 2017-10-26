package no.birkett.quietshare;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class ReceiveMoney extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ReceiveMoney";
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private Subscription frameSubscription = Subscriptions.empty();
    private Spinner profileSpinner;
    private ArrayAdapter<String> spinnerArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // profileSpinner = (Spinner) findViewById(R.id.sound_profile);
       //setupProfileSpinner();
       // setupReceiver();
        setContentView(R.layout.activity_receive_money);
       Button btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
//        String x = ProfilesHelper.getSoundOption(this);
//        Log.i(TAG,"Audio >>>>"+x);

    }



//    private void setupProfileSpinner() {
//        spinnerArrayAdapter = ProfilesHelper.createArrayAdapter(this);
//        profileSpinner.setAdapter(spinnerArrayAdapter);
//        profileSpinner.setSelection(0, false);
//        profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//             //   setupReceiver();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }

//
    boolean hasRecordAudioPersmission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }
//
    private void subscribeToFrames() {
        frameSubscription.unsubscribe();
        frameSubscription = FrameReceiverObservable.create(this,getProfile()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(buf -> {
            String receivedContent = new String(buf, Charset.forName("UTF-8"));
            String timestamp = AppUtils.getTime();
            Log.i(TAG,"Received Successfully "+"--"+ receivedContent + "--" + timestamp);
            TextView tvstatus = (TextView) findViewById(R.id.receive_status);
            tvstatus.setText(receivedContent + " " + timestamp);


        }, error-> {
            Log.i(TAG,"Received Error");
        });
    }
//
//
    private void setupReceiver() {
        Log.i(TAG,"Receiver started");
        if (hasRecordAudioPersmission()) {
            subscribeToFrames();
        } else {
            requestPermission();
        };
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setupReceiver();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        frameSubscription.unsubscribe();
//    }
//
//
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    subscribeToFrames();
//                } else {
//                    showMissingAudioPermissionToast();
//                    finish();
//                }
//            }
//        }
//    }
//    private void showMissingAudioPermissionToast() {
//        Context context = getApplicationContext();
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, R.string.missing_audio_permission, duration);
//        toast.show();
//    }


    private String getProfile() {
        String profile = "";
        return profile;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                setupReceiver();
                break;
        }
    }
}
