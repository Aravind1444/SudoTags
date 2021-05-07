package me.aravind.sudotags;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CirclePageIndicator;
import com.synnapps.carouselview.ImageListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button button;
    private TextView buttonMyProducts;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.lens, R.drawable.house, R.drawable.simple, R.drawable.added};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //navigate to add products page
        button = v.findViewById(R.id.addproduct);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), AddProducts.class);
                startActivity(intent);
            }
        });

        //navigate to my products page
        buttonMyProducts = v.findViewById(R.id.myproducttext);
        buttonMyProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), MyProducts.class);
                startActivity(intent);
            }
        });





        //carousel view
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        //hiding the carousel stroke
        CarouselView customCarouselView = (CarouselView) v.findViewById(R.id.carouselView);
        CirclePageIndicator indicator = (CirclePageIndicator) customCarouselView.findViewById(R.id.indicator);
        if (indicator != null) {
            indicator.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return v;
    }

    //remaining carousel function
    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

}