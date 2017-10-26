package no.birkett.quietshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.quietmodem.Quiet.FrameTransmitter;
import org.quietmodem.Quiet.FrameTransmitterConfig;
import org.quietmodem.Quiet.ModemException;

import java.io.IOException;
import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener{
   // private TransactionObject transactionObject ;
   TransactionObject transactionObject = new TransactionObject("5431","AZ122","121",AppUtils.getTime());
    private EditText amountToTransfer;
    private RecyclerView transactionRecycleView;
    private  TransactionAdapter transactionAdapter;
    private static final String TAG = "LandingActivity";
    private TextView balancetv ;
    private  LinearLayoutManager linearLayoutManager;
    private ArrayList<TransactionObject> mTransactionList = new ArrayList<TransactionObject>();
    private FrameTransmitter transmitter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setUp();
        setUpTransactionAdapter();
        handleDataFromIntent();

    }

    private void setUpTransactionAdapter() {
        linearLayoutManager = new LinearLayoutManager(this);
        transactionAdapter = new TransactionAdapter(mTransactionList);
        transactionRecycleView = (RecyclerView) findViewById(R.id.transaction_rv);
        transactionRecycleView.setHasFixedSize(true);
        transactionRecycleView.setLayoutManager(linearLayoutManager);
        transactionRecycleView.setAdapter(transactionAdapter);

    }

    public void setUp(){
       // transactionObject = new TransactionObject();


        mTransactionList.add(transactionObject);
        balancetv = (TextView) findViewById(R.id.balance_info);
        amountToTransfer = (EditText) findViewById(R.id.amount);
        Button sendMoney = (Button) findViewById(R.id.send_money);
        Button receiveMoney = (Button) findViewById(R.id.receive_money);
        balancetv.setText(transactionObject.getBalance());
        sendMoney.setOnClickListener(this);
        receiveMoney.setOnClickListener(this);

    }


    private void receiveMoney() {
        startActivity(new Intent(this, ReceiveMoney.class));
//        Intent intent = new Intent(LandingActivity.this,ReceiveMoney.class);
//        LandingActivity.this.startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_money:
                sendMoney(amountToTransfer.getText().toString());
                break;
            case R.id.receive_money:
                receiveMoney();
                break;
        }
    }

//    private void setupTransmitter() {
//        FrameTransmitterConfig transmitterConfig;
//        try {
//            transmitterConfig = new FrameTransmitterConfig(this,ProfilesHelper.getSoundOption(this));
//            transmitter = new FrameTransmitter(transmitterConfig);
//            Log.i(TAG,"Transmitted Data >>>>>"+transmitter.toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ModemException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void handleDataFromIntent() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                amountToTransfer.setText(sharedText);
            }
        }
    }



    private void handleSendClick() {
        if (transmitter == null) {
           //setupTransmitter();
        }
        send();
    }


    private void send() {
        String payload = amountToTransfer.getText().toString();
        try {
            transmitter.send(payload.getBytes());
        } catch (IOException e) {

        }
    }
    private void sendMoney(String amt) {
        Float balance = Float.valueOf(transactionObject.getBalance()) - Float.valueOf(amt);
        Log.i(TAG,"Sending money"+balance);
        //handleSendClick();
        transactionObject.setBalance(balance.toString());
        TransactionObject sObj = new TransactionObject(balance.toString(),"TR1231",amt,AppUtils.getTime());
        balancetv.setText(balance.toString());
        transactionAdapter.add(sObj);
        linearLayoutManager = new LinearLayoutManager(this);
        transactionAdapter = new TransactionAdapter(mTransactionList);
        transactionRecycleView.setHasFixedSize(true);
        transactionRecycleView.setLayoutManager(linearLayoutManager);
        transactionRecycleView.setAdapter(transactionAdapter);

    }
}
