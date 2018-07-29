package vadeworks.gigafacts;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vadeworks.gigafacts.FactsDisplay.FactsDisplay;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Integer[] imageArray = {R.drawable.animals,
            R.drawable.art,
            R.drawable.books,
            R.drawable.cars,
            R.drawable.cities,
    R.drawable.countries,
    R.drawable.companies,
    R.drawable.economy,
    R.drawable.environment,
    R.drawable.food,
    R.drawable.geography,
    R.drawable.history,
    R.drawable.india,
    R.drawable.inventions,
    R.drawable.movies,
    R.drawable.people,
    R.drawable.politics,
    R.drawable.science,
    R.drawable.space,
    R.drawable.sports,
    R.drawable.technology,
    R.drawable.traditions,
    R.drawable.worldrecords,
    R.drawable.worldwars};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        android.support.v7.app.ActionBar AB = getSupportActionBar();
        AB.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        final String[] categories = getResources().getStringArray(R.array.categories);
        listView = findViewById(R.id.categories_list);

        CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(this,categories,imageArray);
        listView.setAdapter(customListViewAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FactsDisplay.class);
                intent.putExtra("category_name",categories[position]);
                intent.putExtra("category_image",imageArray[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(MainActivity.this, categories[position], Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    animalFacts_300();
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
                        String uploadTime= Timestamp.now().toString();
                        Log.d("facts",fact);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
