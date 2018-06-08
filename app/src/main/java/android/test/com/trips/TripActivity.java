package android.test.com.trips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.test.com.trips.model.Trip;

public class TripActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trip);
        String data;

        int keyId;

        Intent intent = getIntent();

        if (getIntent().hasExtra("key")) {
            data = getIntent().getStringExtra("key");
            if (data.equalsIgnoreCase("LOG")) {
                // if log btn clicked
                showLogTripScreen();
            }
        }

        if (getIntent().hasExtra("key")) {
            data = getIntent().getStringExtra("key");

            keyId = getIntent().getIntExtra("keyId", 1);


            if (data.equalsIgnoreCase("FROM_LIST")) {

                // if log btn clicked
                showLogTripScreen(getIntent().getIntExtra("keyId", keyId));
            }
        }

    }

    @Override
    protected void setUp() {
    }

    private void showLogTripScreen(int tripId) {
        Bundle args = new Bundle();
        LogTripFragment logTripFragment;
        logTripFragment = LogTripFragment.newInstance(tripId);
        logTripFragment.setArguments(args);
        attachFragment(logTripFragment, LogTripFragment.FRAGMENT_TAG);

    }


    private void showLogTripScreen() {
        Bundle args = new Bundle();
        args.putString("key ", "value");
        LogTripFragment logTripFragment;
        logTripFragment = LogTripFragment.newInstance();
        logTripFragment.setArguments(args);
        attachFragment(logTripFragment, LogTripFragment.FRAGMENT_TAG);

    }

}