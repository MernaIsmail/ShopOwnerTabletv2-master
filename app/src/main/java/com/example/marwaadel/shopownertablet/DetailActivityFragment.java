package com.example.marwaadel.shopownertablet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marwaadel.shopownertablet.mPicasso.PicassoClient;
import com.example.marwaadel.shopownertablet.model.OfferDataModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
   TextView tvTitle, tvDescription, tvBefore, tvAfter,tvAmount ,tvStartOffer,tvEndOffer;
    OfferDataModel offer;
    public static final String TAG = DetailActivityFragment.class.getSimpleName();
    static final String DETAIL_OFFER = "DETAIL_OFFER";
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
        Bundle arguments = getArguments();
 if (arguments != null) {
             offer = (OfferDataModel) arguments.getSerializable(DetailActivityFragment.DETAIL_OFFER);


            // 3. get reference to offer textView
            ImageView Img=(ImageView)rootView.findViewById(R.id.btndetail);
            tvTitle = (TextView) rootView.findViewById(R.id.titlte_detail);
            tvDescription = (TextView) rootView.findViewById(R.id.description_detail);
            tvBefore = (TextView) rootView.findViewById(R.id.before_detail);
            tvAfter = (TextView) rootView.findViewById(R.id.after_detail);
            tvAmount=(TextView) rootView.findViewById(R.id.amount_detail);
            tvStartOffer=(TextView) rootView.findViewById(R.id.fromdate_detail);
            tvEndOffer=(TextView) rootView.findViewById(R.id.todate_detail);
            // 4. display details on textView
             PicassoClient.downloadImg(getActivity(),offer.getOfferImage(),Img);
        tvTitle.setText(offer.getTitle());
            tvDescription.setText(offer.getDescription());
             tvBefore.setText(offer.getDiscountBefore());
               tvAfter.setText(offer.getDiscountAfter());
tvAmount.setText(offer.getAmount());
            tvStartOffer.setText(offer.getDayStartOffer());
            tvEndOffer.setText(offer.getDayEndOffer());
        }


return  rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent in = new Intent(getContext(), addoffers.class);
                //  in.putExtra("object",offer);
                //in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);

                return true;
            case R.id.action_delete:
                if (isConnected(getActivity().getApplicationContext())) {


                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                    //  Firebase shopRef = new Firebase(Constants.FIREBASE_URL).child("OfferList")
                    //        .child(getRef(position).getKey()).child("status");
                    //shopRef.setValue("true");
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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


}
