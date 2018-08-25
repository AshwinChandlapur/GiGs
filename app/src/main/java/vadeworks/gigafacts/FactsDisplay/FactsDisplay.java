package vadeworks.gigafacts.FactsDisplay;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import vadeworks.gigafacts.Facts;
import vadeworks.gigafacts.R;

public class FactsDisplay extends AppCompatActivity {

    String category_name;
    Integer category_image;
    ImageView image_category;
    FirebaseFirestore db;
    ArrayList<Facts> factsArrayList = new ArrayList<>();
    ViewPager mViewPager;
    Context context;
    DocumentSnapshot lastResult;
    LinearLayout logo;
    CustomPagerAdapter mCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        android.support.v7.app.ActionBar AB = getSupportActionBar();
        AB.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_facts_display);

        context = this;
        logo = findViewById(R.id.logo);
        logo.animate().alpha(1.0f);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if(intent!=null){
            category_name = intent.getStringExtra("category_name");
            category_image = intent.getIntExtra("category_image",R.drawable.animals);
        }

        image_category = findViewById(R.id.category_image);
//        Picasso.with(getApplicationContext()).load(category_image).placeholder(category_image).into(image_category);





        final CollectionReference collectionReference = db.collection(category_name);
        Random r = new Random();
        int Low = 0;
        int High = 200;
        int Result = r.nextInt(High-Low) + Low;
        Query query;
        query =collectionReference
                    .limit(40);


        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("OKOK", document.getId() + " => " + document.getData());
                                String fact = document.get("fact").toString();
                                String imgUrl = document.get("imgUrl").toString();
                                Facts facts = new Facts(fact,imgUrl, Timestamp.now().toString());
                                factsArrayList.add(facts);
                                Collections.shuffle(factsArrayList);
                            }

                            if(factsArrayList.size()!=0){
                                try{
                                    lastResult = task.getResult().getDocuments().get(task.getResult().getDocuments().size()-1);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            mCustomPagerAdapter = new CustomPagerAdapter(context, factsArrayList, 0);
                            mViewPager = findViewById(R.id.pager);
                            mViewPager.setAdapter(mCustomPagerAdapter);
                            mViewPager.setOffscreenPageLimit(3);

                            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                        if(position==factsArrayList.size()-1){
                                            Query query1;
                                            query1 =collectionReference
                                                    .startAfter(lastResult)
                                                    .limit(40);

                                            query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Log.d("OKOK", document.getId() + " => " + document.getData());
                                                            String fact = document.get("fact").toString();
                                                            String imgUrl = document.get("imgUrl").toString();
                                                            Facts facts = new Facts(fact, imgUrl, Timestamp.now().toString());
                                                            factsArrayList.add(facts);
                                                            Collections.shuffle(factsArrayList);
                                                            mCustomPagerAdapter.notifyDataSetChanged();
                                                        }
                                                        if(factsArrayList.size()!=0) {
                                                            try {
                                                                lastResult = task.getResult().getDocuments().get(task.getResult().getDocuments().size() - 1);
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }


                                                }
                                            });
                                        }
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });


                        } else {
                            Log.d("OKOK", "Error getting documents: ", task.getException());
                        }
//                        Toast.makeText(FactsDisplay.this, "Size is"+factsArrayList.size(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void animalFacts_300(){

        final String url = "https://www.thefactsite.com/2010/09/300-random-animal-facts.html";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document d= Jsoup.connect(url).get();

                    Elements facts = d.select("ol").select("li");
                    for(Element ele:facts){
                        String fact = ele.text();
                        String url = "";
                        String uploadTime=Timestamp.now().toString();
                        Log.d("facts",fact);
                        Facts facts1 = new Facts(fact,url,uploadTime);
                        factsArrayList.add(facts1);
                        mCustomPagerAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
