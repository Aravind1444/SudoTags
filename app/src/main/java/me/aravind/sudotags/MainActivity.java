package me.aravind.sudotags;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CirclePageIndicator;
import com.synnapps.carouselview.ImageListener;

public class MainActivity extends AppCompatActivity {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.lens, R.drawable.house, R.drawable.simple, R.drawable.added};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //carousel view
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        //hiding the carousel stroke
        CarouselView customCarouselView = (CarouselView) findViewById(R.id.carouselView);
        CirclePageIndicator indicator = (CirclePageIndicator) customCarouselView.findViewById(R.id.indicator);
        if (indicator != null) {
            indicator.setVisibility(View.GONE);
        }

    }

    //remaining carousel function
    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


}