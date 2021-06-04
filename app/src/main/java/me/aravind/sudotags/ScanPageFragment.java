package me.aravind.sudotags;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanPageFragment extends Fragment {

    Button btScan;
    Button buttonScanPage, buttonHomePage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScanPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanPageFragment newInstance(String param1, String param2) {
        ScanPageFragment fragment = new ScanPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_scan_page, container, false);


        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //navigate to add scanning page
        buttonScanPage = (Button) v.findViewById(R.id.scanbutton);
        buttonScanPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanPageFragment.this.getActivity(), TrialPage.class);
                startActivity(intent);
            }
        });

        //navigate to home page
        buttonHomePage = (Button) v.findViewById(R.id.scanhomebutton);
        buttonHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanPageFragment.this.getActivity(), HomePage.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}