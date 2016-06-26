package com.example.marwaadel.shopownertablet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.app.AlertDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cloudinary.Cloudinary;
import com.example.marwaadel.shopownertablet.model.OfferDataModel;
import com.example.marwaadel.shopownertablet.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class addoffersFragment extends Fragment implements View.OnClickListener {


    //UI References
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    Firebase listsRef;
    EditText mTitle, mDescription, mBefore, mAfter,mAmount;
    ImageButton btn1;
   OfferDataModel toEdit = null;
    String uuidRef;
    String mListId;
    Cloudinary cloudinary;
    Uri uri;
    private int PICK_IMAGE_REQUEST = 1;
    String Generated_Id;


    public addoffersFragment() {
    }
    public String generatePIN() {
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(randomPIN);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                // Toast.makeText(getActivity(), "msg msg", Toast.LENGTH_SHORT).show();
              // listsRef = new Firebase(Constants.FIREBASE_URL).child("OfferList");
                listsRef = new Firebase(Constants.FIREBASE_URL);
                AuthData authData = listsRef.getAuth();
                Log.i("uidbgrb: " , authData.getUid());

                mListId = authData.getUid();
                Log.i("uidbgrb2: " , mListId);
                Log.d("constant",Constants.My_UID);
               Firebase Ref = new Firebase(Constants.FIREBASE_URL).child("OfferList").child(Constants.My_UID);


                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();
                String before = mBefore.getText().toString();
                String after = mAfter.getText().toString();
                String day1 = fromDateEtxt.getText().toString();
                String day2 = toDateEtxt.getText().toString();
                String amount = mAmount.getText().toString();




                if( mTitle.getText().toString().length() == 0 )
                    mTitle.setError( "Title is required!" );
                if( mDescription.getText().toString().length() == 0 )
                    mDescription.setError( "Description is required!" );
                if( mAfter.getText().toString().length() == 0 )
                    mAfter.setError( "this is required!" );

                if( mBefore.getText().toString().length() == 0 )
                    mBefore.setError( "this is required!" );

                if( mAmount.getText().toString().length() == 0 )
                    mAmount.setError( "amount is required!" );
                if( fromDateEtxt.getText().toString().length() == 0 )
                    fromDateEtxt.setError( "Start Offer is required!" );
                if( toDateEtxt.getText().toString().length() == 0 )
                    toDateEtxt.setError( "End Offer is required!" );
                else {
                    if (isConnected(getContext())) {

                        if (uri != null) {
                            try {

                                Generated_Id = generatePIN();
                                final InputStream in = ((addoffers) getActivity()).getContentResolver().openInputStream(uri);
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            cloudinary.uploader().upload(in, Cloudinary.asMap("public_id", Generated_Id));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                new Thread(runnable).start();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (toEdit != null) { //update
                            Firebase eRef = new Firebase(Constants.FIREBASE_URL).child("OfferList").child(uuidRef);
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("Title", title);
                            updates.put("Description", description);
                            updates.put("DiscountBefore", before);
                            updates.put("DiscountAfter", after);
                            updates.put("DayStartOffer", day1);
                            updates.put("DayEndOffer", day2);
                            if (Generated_Id != null) {
                                updates.put("offerImage", cloudinary.url().generate(Generated_Id + ".jpg"));
                            }
                            eRef.updateChildren(updates);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Map<String, Object> values = new HashMap<>();
                            values.put("Title", title);
                            values.put("Description", description);
                            values.put("DiscountBefore", before);
                            values.put("DiscountAfter", after);
                            values.put("DayStartOffer", day1);
                            values.put("DayEndOffer", day2);
                            values.put("status", "false");
                            values.put("Amount", amount);
                            values.put("offerImage", cloudinary.url().generate(Generated_Id + ".jpg"));
                            Ref.push().setValue(values);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    } else {

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("No Internet connection.");
                        alertDialog.setMessage("You have no internet connection");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_addoffers, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        //----------
        Map config = new HashMap();
        config.put("cloud_name", "gp");
        config.put("api_key", "667862958976234");
        config.put("api_secret", "zAQ9orjld73mDil8fFsdDNXUQrg");
        cloudinary = new Cloudinary(config);
        //----------

        Intent intent = getActivity().getIntent();
        // String shopUUID = intent.getStringExtra("e
        // ditobj");
        toEdit = (OfferDataModel) intent.getSerializableExtra("editobj");
        uuidRef = intent.getStringExtra("uuid");




        toolbar.setNavigationIcon(R.drawable.close_icon);
        ((addoffers) getActivity()).setSupportActionBar(toolbar);
        ((addoffers) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), MainActivity.class);
                startActivity(in);
                //getActivity().finish();
            }
        });
        mTitle = (EditText) rootView.findViewById(R.id.editTilte);
        mDescription = (EditText) rootView.findViewById(R.id.editDescription);
        mBefore = (EditText) rootView.findViewById(R.id.editTilte2);
        mAfter = (EditText) rootView.findViewById(R.id.editTilte4);
        fromDateEtxt = (EditText) rootView.findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) rootView.findViewById(R.id.etxt_todate);
        mAmount=(EditText) rootView.findViewById(R.id.editAmount);
        findViewsById();
        setDateTimeField();
//        mDay1 = (EditText) rootView.findViewById(R.id.editDay);
//        mMonth1 = (EditText) rootView.findViewById(R.id.editMonth);
//        mYear1 = (EditText) rootView.findViewById(R.id.editYear);
//        mDay2 = (EditText) rootView.findViewById(R.id.editDay2);
//        mMonth2 = (EditText) rootView.findViewById(R.id.editMonth2);
//        mYear2 = (EditText) rootView.findViewById(R.id.editYear2);
        btn1 = (ImageButton) rootView.findViewById(R.id.btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        return  rootView;
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    private void findViewsById() {
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bYtE);
                btn1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (v == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }
}
