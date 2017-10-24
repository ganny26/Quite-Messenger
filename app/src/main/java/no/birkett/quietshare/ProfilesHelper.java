package no.birkett.quietshare;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;
import org.quietmodem.Quiet.FrameTransmitterConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ProfilesHelper {

    private static final String TAG = "Profile Helper";

    public static ArrayList<String> getProfiles(Context context) {
        ArrayList<String> profiles = new ArrayList<>();
        ArrayList<String> mProfiles = new ArrayList<>();
        try {
            String json = FrameTransmitterConfig.getDefaultProfiles(context);
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> iterator = jsonObject.keys();

            while(iterator.hasNext()) {
                //Log.i(TAG,"Iterator"+iterator.next().toString());
                profiles.add(iterator.next());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Log.i(TAG,"profiles..."+profiles.toString());
        mProfiles.add(profiles.get(5));
        mProfiles.add(profiles.get(0));
        return mProfiles;
    }

    public static ArrayAdapter createArrayAdapter(Context context) {
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, getProfiles(context));
        Log.i(TAG,"Sound options "+arrayAdapter.toString());
        return arrayAdapter;
    }

}
