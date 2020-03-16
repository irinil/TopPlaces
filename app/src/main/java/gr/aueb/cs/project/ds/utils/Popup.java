package gr.aueb.cs.project.ds.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import gr.aueb.cs.project.ds.utils.R;


/**
 * Created by Irini on 26/5/2016.
 */
public class Popup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_popup);

        TextView info = (TextView) findViewById(R.id.show);

        info.setText("Creators:\nEirhnh Lygerou");

        DisplayMetrics dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);

        int width = dimensions.widthPixels;
        int height = dimensions.heightPixels;

        getWindow().setLayout((int)(width*.5),(int)(height*.4));



    }

}