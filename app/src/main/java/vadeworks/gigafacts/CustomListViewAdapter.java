package vadeworks.gigafacts;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomListViewAdapter extends ArrayAdapter {

    Activity context;
    ImageView imageView;
    TextView textView;

    //to store the list of categories
    String[] nameArray;

    //to store the list of images
    Integer[] imageArray;

    public CustomListViewAdapter(Activity context,String[] nameArray, Integer[] imageArray) {
        super(context,R.layout.categories_list , nameArray);
        this.context = context;
        this.nameArray = nameArray;
        this.imageArray = imageArray;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.categories_list,null,true);

        TextView textView = view1.findViewById(R.id.category);
        ImageView imageView = view1.findViewById(R.id.category_image);

        textView.setText(nameArray[position]);
        imageView.setImageResource(imageArray[position]);

        return view1;

    }
}
