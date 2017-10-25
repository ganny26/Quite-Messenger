package no.birkett.quietshare;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setUp();
        setUpTransactionAdapter();

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
        balancetv.setText(transactionObject.getBalance());
        sendMoney.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_money:
                sendMoney(amountToTransfer.getText().toString());
                break;
        }
    }

    private void sendMoney(String amt) {
        Float balance = Float.valueOf(transactionObject.getBalance()) - Float.valueOf(amt);
        Log.i(TAG,"Sending money"+balance);
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
