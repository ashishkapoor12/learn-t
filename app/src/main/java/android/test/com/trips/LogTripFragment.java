package android.test.com.trips;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.test.com.trips.model.Trip;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


// The photo item allows the user to take a picture with the camera.

public class LogTripFragment extends BaseFragment {
    public static final String FRAGMENT_TAG = "LogTripFragment";


    Spinner spinner;
    AppCompatEditText titleEt;
    AppCompatEditText dateEt;
    AppCompatEditText durationEt;
    AppCompatEditText commentEt;
    ImageView photo_iv;
    AppCompatEditText destEt;
    AppCompatEditText locEt;

    private static final int CAMERA_REQUEST = 1;
    byte[] imageName;
    int imageId;
    Bitmap theImage;


    int tpId = 0;


    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int INITIAL_REQUEST = 1337;


    private static final int LOCATION_REQUEST = 1337;

    Location mLoc;

    public LogTripFragment() {
        //empty public constructor
    }


    @SuppressLint("ValidFragment")
    public LogTripFragment(int tpId) {

        this.tpId = tpId;
    }


    public static LogTripFragment newInstance() {
        return new LogTripFragment();
    }

    public static LogTripFragment newInstance(int tpId) {
        return new LogTripFragment(tpId);
    }


    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void setUp(View view) {

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttachToContext(activity);
    }

    private DBHelper db;


    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;

    private Button btnGetLocation = null;
    private ProgressBar pb = null;

    private static final String TAG = "Debug";
    private Boolean flag = false;


    private void getCurrentLocation() {

        // Get the location manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            Location location = null;


            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    locEt.setText("");


                    String longitude = "Longitude: " + location.getLongitude();
                    Log.v(TAG, longitude);
                    String latitude = "Latitude: " + location.getLatitude();
                    Log.v(TAG, latitude);

    /*----------to get City-Name from coordinates ------------- */
                    String cityName = null;

                    if (null != getActivity()) {
                        Geocoder gcd = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses.size() > 0)
                                System.out.println(addresses.get(0).getLocality());
                            cityName = addresses.get(0).getLocality();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String s = cityName;
                        locEt.setText(s);
                        mLoc = location;

                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };


            try {

                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200, 1, locationListener);


            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


            Geocoder gcd = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String locality = addresses.get(0).getLocality();
                    String subLocality = addresses.get(0).getSubLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    String currentLocation;
                    String current_locality;

                    if (subLocality != null) {

                        currentLocation = locality + "," + subLocality;
                    } else {

                        currentLocation = locality;
                    }
                    current_locality = locality;
                    Toast.makeText(getActivity(), "location - " + currentLocation, Toast.LENGTH_SHORT).show();
                    locEt.setText(currentLocation);

                    mLoc = location;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_log_trip, container, false);
        Button logBtn = v.findViewById(R.id.logBtn);
        Button cancelBtn = v.findViewById(R.id.cancelBtn);
        Button deleteBtn = v.findViewById(R.id.deleteBtn);
        Button photoBtn = v.findViewById(R.id.photoBtn);

        spinner = v.findViewById(R.id.type_et);
        titleEt = v.findViewById(R.id.title_et);
        dateEt = v.findViewById(R.id.date_et);
        durationEt = v.findViewById(R.id.duration_et);
        commentEt = v.findViewById(R.id.comment_et);
        photoBtn = v.findViewById(R.id.photoBtn);
        photo_iv = v.findViewById(R.id.photo_iv);
        destEt = v.findViewById(R.id.dest_et);
        locEt = v.findViewById(R.id.loc_et);


        Button mapButton = v.findViewById(R.id.mapButton);


        db = new DBHelper(getActivity());


        if (tpId != 0) {
            Trip trip1 = db.getTrip(tpId);

//            spinner.setSelection(Integer.parseInt(trip1.getType()));

            titleEt.setEnabled(false);

            dateEt.setEnabled(false);
            commentEt.setEnabled(false);
            durationEt.setEnabled(false);
            destEt.setEnabled(false);
            photo_iv.setEnabled(false);
            locEt.setEnabled(false);

            mapButton.setVisibility(View.VISIBLE);

            final String myLongitude = locEt.getText().toString();
            final String[] map = new String[1];

            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        map[0] = "http://maps.google.co.in/maps?q=" + myLongitude;
                        Intent maps = new Intent(Intent.ACTION_VIEW, Uri.parse(map[0]));

                        startActivity(maps);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });

            photoBtn.setVisibility(View.GONE);
            titleEt.setText(trip1.getTitle());
            dateEt.setText(trip1.getTitle());
            durationEt.setText(trip1.getDuration());

            Bitmap bmp = BitmapFactory.decodeByteArray(trip1.getPhoto(), 0, trip1.getPhoto().length);
            photo_iv.setImageBitmap(Bitmap.createScaledBitmap(bmp, 70, 70, false));
            commentEt.setText(trip1.getComment());
            destEt.setText(trip1.getDestination());
            locEt.setText(trip1.getLocation());


            logBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.VISIBLE);
            photoBtn.setVisibility(View.GONE);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Trip tripToDelete = db.getTrip(tpId);
                    db.deleteTrip(tripToDelete);
                    Toast.makeText(getActivity(), "Trip deleted", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            });


        } else {
            deleteBtn.setVisibility(View.GONE);
            mapButton.setVisibility(View.GONE);
            logBtn.setVisibility(View.VISIBLE);
            photoBtn.setVisibility(View.VISIBLE);
            titleEt.setEnabled(true);
            dateEt.setEnabled(true);
            commentEt.setEnabled(true);
            durationEt.setEnabled(true);
            destEt.setEnabled(true);
            locEt.setEnabled(false);

            if (isLocationEnabled(getActivity())) {
                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                } else {
                    getCurrentLocation();
                }

            } else {

                Toast.makeText(getActivity(), "gps is off", Toast.LENGTH_SHORT).show();
            }
        }


        final String[] option = new String[]{"Take from Camera"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    callCamera();
                }


            }
        });
        final AlertDialog dialog = builder.create();

        photoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });


        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tripType = spinner.getSelectedItem().toString();
                String title = titleEt.getText().toString();
                String date = dateEt.getText().toString();
                String duration = durationEt.getText().toString();
                String comment = commentEt.getText().toString();

                String dest = destEt.getText().toString();
                String loc = locEt.getText().toString();
                locEt.setEnabled(false);

                Trip test = new Trip(tripType, title, date, duration, comment, photo, dest, loc);
                storeInDB(test);

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != getActivity()) getActivity().finish();
            }
        });

        return v;
    }


    byte[] photo;

    void storeInDB(Trip trip) {
        long id = db.insertTrip(trip);
        Trip trip1 = db.getTrip(id);
        Toast.makeText(getActivity(), "trip saved", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    // handle back
    @Override
    public void onBackClick() {
        super.onBackClick();
        getActivity().finish();
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                getCurrentLocation();

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    Log.d("Insert: ", "Inserting ..");
                    photo = imageInByte;
                    photo_iv.setImageBitmap(yourImage);

                }
                break;
        }
    }

    public void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 200);
    }
}