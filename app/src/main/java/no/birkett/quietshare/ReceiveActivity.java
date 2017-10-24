package no.birkett.quietshare;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class ReceiveActivity extends AppCompatActivity {
    private static final String TAG="Receive Activity";
    private TextView receivedContent;
    private Spinner profileSpinner;

    private ArrayList<MessageObject> mDataList = new ArrayList<MessageObject>();
    private ArrayAdapter<String> spinnerArrayAdapter;
    private TextView receiveStatus;
    private Subscription frameSubscription = Subscriptions.empty();
    private RecyclerView recyclerView;
    private TransmitAdapter transmitAdapter;
    private LinearLayoutManager linearLayoutManager;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        profileSpinner = (Spinner) findViewById(R.id.profile);
        findViewById(R.id.receive_button).setOnClickListener(v -> {
            Log.i(TAG,"Button clicked");
            startActivity(new Intent(this, TransmitActivity.class));
        });
        setupProfileSpinner();
        setupadapter();
    }

    public void setupadapter(){
        ArrayList<MessageObject> arrlist = new ArrayList<MessageObject>();
        MessageObject messageObject = new MessageObject("Hello","123123");
        arrlist.add(messageObject);
        linearLayoutManager = new LinearLayoutManager(this);
        transmitAdapter = new TransmitAdapter(arrlist);
        recyclerView = (RecyclerView) findViewById(R.id.receive_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(transmitAdapter);


    }
    @Override
    protected void onResume() {
        super.onResume();
        setupReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        frameSubscription.unsubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    subscribeToFrames();
                } else {
                    showMissingAudioPermissionToast();
                    finish();
                }
            }
        }
    }


    private void setupReceiver() {
        if (hasRecordAudioPersmission()) {
            subscribeToFrames();
        } else {
            requestPermission();
        };
    }



    private void subscribeToFrames() {
        frameSubscription.unsubscribe();
        frameSubscription = FrameReceiverObservable.create(this, getProfile()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(buf -> {

            recyclerView = (RecyclerView) findViewById(R.id.receive_rv);
            String receivedContent = new String(buf, Charset.forName("UTF-8"));
            String timestamp = AppUtils.getTime();
            MessageObject ms = new MessageObject(receivedContent,timestamp);
            transmitAdapter.add(ms);
            linearLayoutManager = new LinearLayoutManager(this);
            transmitAdapter = new TransmitAdapter(mDataList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(transmitAdapter);
            Log.i(TAG,"Received Successfully "+"--"+ receivedContent + "--" + timestamp);
        }, error-> {
            Log.i(TAG,"Received Error");
        });
    }

    boolean hasRecordAudioPersmission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }



    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
    }

    private void showMissingAudioPermissionToast() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, R.string.missing_audio_permission, duration);
        toast.show();
    }

    private void setupProfileSpinner() {
        spinnerArrayAdapter = ProfilesHelper.createArrayAdapter(this);
        profileSpinner.setAdapter(spinnerArrayAdapter);
        profileSpinner.setSelection(0, false);
        profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupReceiver();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private String getProfile() {
        String profile = spinnerArrayAdapter.getItem(profileSpinner.getSelectedItemPosition());
        return profile;
    }
}
