package com.example.marwaadel.shopownertablet;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marwaadel.shopownertablet.mPicasso.PicassoClient;
import com.example.marwaadel.shopownertablet.model.OfferDataModel;
import com.example.marwaadel.shopownertablet.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShowOfferFragment extends Fragment {
    ListView listview;
    FirebaseListAdapter<OfferDataModel> mOfferAdapter;
    offerAdapter OfferAdapter;
    ImageView detailImg;
    ImageView FAB;
    // String mListId;
    TextView title;
    String mListId;
    LinearLayout linlaHeaderProgress;




    Toolbar toolbar;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private ImageView mTitleView;
    private String mInput;
    private Toolbar mToolbar;
    OfferDataModel offer;
   // offerAdapter OfferAdapter;
//    FragmentManager fragmentManager;

    ListView lv;
    public ShowOfferFragment() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        Intent myIntent = getIntent();
//        handleIntent(myIntent);
//        lv = (ListView) findViewById(R.id.mListView);
//        // Firebase refListName = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);
//
//
//        Firebase refListName = new Firebase(Constants.FIREBASE_URL).child("OfferList").child(Constants.My_UID);
//        Query query2 = refListName.orderByChild("Title");
//        OfferAdapter = new offerAdapter(this, OfferDataModel.class, R.layout.offeritem, query2);
//        lv.setAdapter(OfferAdapter);
        //  Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();//here imp afetr click search

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main_actions, menu);
   //     Toast.makeText(Sh.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManger = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManger.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doMySearch(newText);
                return false;
            }
        });


    }
    void doMySearch(String query) {
       // lv = (ListView) findViewById(R.id.mListView);
        // Firebase refListName = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);
        Firebase refListName = new Firebase(Constants.FIREBASE_URL).child("OfferList").child(Constants.My_UID);

        Query query2 = refListName.orderByChild("Title").startAt(query).endAt(query + "\uf8ff");

        OfferAdapter = new offerAdapter(getActivity(), OfferDataModel.class, R.layout.offeritem, query2);
        lv.setAdapter(OfferAdapter);
        Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();//here imp afetr click search


    }


//    @Override
//    public void onNewIntent(Intent intent) {
//        getActivity().setIntent(intent);
//        handleIntent(intent);
//    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_offer, container, false);
        setHasOptionsMenu(true);



        //*** search code
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        Intent myIntent = getActivity().getIntent();
//        handleIntent(myIntent);
        handleIntent(getActivity().getIntent());
        lv = (ListView) rootView.findViewById(R.id.mListView);
        // Firebase refListName = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);


        Firebase refListName = new Firebase(Constants.FIREBASE_URL).child("OfferList").child(Constants.My_UID);
        Query query2 = refListName.orderByChild("Title");
        OfferAdapter = new offerAdapter(getActivity(), OfferDataModel.class, R.layout.offeritem, query2);
        lv.setAdapter(OfferAdapter);
        //  Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();//here imp afetr click search





        //****
        if (isConnected(getActivity().getApplicationContext())) {
            linlaHeaderProgress = (LinearLayout) rootView.findViewById(R.id.linlaHeaderProgress);
            listview = (ListView) rootView.findViewById(R.id.mListView);
//          Firebase refListName = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);
            Firebase listsRef = new Firebase(Constants.FIREBASE_URL);
            AuthData authData = listsRef.getAuth();
            Log.i("uidbgrb: ", authData.getUid());

            mListId = authData.getUid();
            Log.i("uidbgrb2: ", mListId);
            listsRef = new Firebase(Constants.FIREBASE_URL).child("OfferList").child(Constants.My_UID);
            Query query = listsRef.orderByChild("status").equalTo("false");
            listsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.d("fief", "onDataChange: " + dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            Log.d("kmjk", "onCreateView: " + listsRef.getKey());
            mOfferAdapter = new FirebaseListAdapter<OfferDataModel>(getActivity(), OfferDataModel.class, R.layout.offeritem, query) {
                @Override
                protected void populateView(View v, final OfferDataModel model, final int position) {

                    ImageView imgOffer = (ImageView) v.findViewById(R.id.offerImg);
                    title = (TextView) v.findViewById(R.id.titlteoffer);
                    TextView description = (TextView) v.findViewById(R.id.descriptionoffer);
                    TextView price = (TextView) v.findViewById(R.id.priceoffer);
                    ImageView detailImg = (ImageView) v.findViewById(R.id.detailBtn);
                    ImageView analysisImg = (ImageView) v.findViewById(R.id.analysisBtn);

                    Log.d("data", "populateView " + model.getDescription());


                    detailImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.d("clicled", String.valueOf(position));
                            Bundle arguments = new Bundle();
                            arguments.putSerializable("DETAIL_OFFER", model);

                            DetailActivityFragment fragment = new DetailActivityFragment();
                            fragment.setArguments(arguments);


                            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.offer_detail_container, fragment, DetailActivityFragment.TAG)
                                    .commit();


                        }
                    });

                    analysisImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.d("clicled", String.valueOf(position));
                            Bundle arguments = new Bundle();
                            //     arguments.putSerializable("DETAIL_OFFER", model);

                            ReportActivityFragment fragment = new ReportActivityFragment();
                            fragment.setArguments(arguments);

//
//                            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.offer_detail_container, fragment, ReportActivityFragment.TAG)
//                                    .commit();


                        }
                    });

                    PicassoClient.downloadImg(getActivity(), model.getOfferImage(), imgOffer);
                    title.setText(model.getTitle());
                    description.setText(model.getDescription());


                    price.setText(model.getDiscountAfter() + " L.E");
                }
            };
            Log.e("ref", String.valueOf(listsRef));
            linlaHeaderProgress.setVisibility(View.VISIBLE);
            listview.setAdapter(mOfferAdapter);
            mOfferAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    linlaHeaderProgress.setVisibility(View.GONE);
                }
            });


            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {


                    OfferDataModel offer = mOfferAdapter.getItem(position);
                    Log.d("clicked", offer.getTitle());


                }
            });


            FAB = (ImageView) rootView.findViewById(R.id.fab);
            //FAB.setBackgroundColor(Color.parseColor("#7d3971"));
//            FAB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7d3971")));
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), addoffers.class);

                    startActivity(intent);
                    // getActivity().finish();


                }
            });


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

        return rootView;
    }


    /*search*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (OfferAdapter != null) {
            OfferAdapter.cleanup();
//           finish();
        }
    }



}
