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
            R.drawable.cartoons,
            R.drawable.cities,
    R.drawable.countries,
    R.drawable.companies,
    R.drawable.fashion,
    R.drawable.fitness,
    R.drawable.football,
    R.drawable.food,
    R.drawable.geography,
    R.drawable.history,
    R.drawable.india,
    R.drawable.inventions,
    R.drawable.landmarks,
    R.drawable.movies,
    R.drawable.newyork,
    R.drawable.people,
    R.drawable.pets,
    R.drawable.politics,
    R.drawable.psychology,
    R.drawable.science,
    R.drawable.space,
    R.drawable.spies,
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
//                Toast.makeText(MainActivity.this, categories[position], Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

}
