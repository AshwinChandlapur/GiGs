package vadeworks.gigafacts.FactsDisplay;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import vadeworks.gigafacts.Facts;
import vadeworks.gigafacts.R;


/**
 * Created by ashwinchandlapur on 27/02/18.
 */

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    CardView fact_parent;
    ArrayList<Facts> mFactsList = new ArrayList<>();
    Facts facts;
    int mPos;
    TextView facts_textView;
    ImageView imageView;
    ImageView dissapear,share;



    public CustomPagerAdapter(Context context, ArrayList<Facts> factsArrayList, int position) {
        mContext = context;
        mFactsList = factsArrayList;
        mPos = position;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mFactsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.horizontal_pager_item, container, false);
        mPos = position;

        facts = new Facts(mFactsList.get(mPos).getFact(),mFactsList.get(mPos).getImgUrl(),mFactsList.get(mPos).getUploadTime());
        display_news(facts, itemView, mPos);
        container.addView(itemView);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fact = facts_textView.getText().toString();


            }
        });





        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    private void display_news(Facts facts, final View itemView, final int mPos) {


        facts_textView = itemView.findViewById(R.id.facts);
        imageView = itemView.findViewById(R.id.imageView);
        fact_parent = itemView.findViewById(R.id.fact_parent);

        share = itemView.findViewById(R.id.share);
        dissapear = itemView.findViewById(R.id.dissapear);


        if (!facts.getFact().isEmpty()) {
            facts_textView.setText(facts.getFact());
        }
        if (!facts.getImgUrl().isEmpty()) {
            Picasso.with(mContext)
                    .load(facts.getImgUrl())
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }
    }



}