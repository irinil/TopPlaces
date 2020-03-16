package gr.aueb.cs.project.ds.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.analytics.ecommerce.Product;
import java.util.ArrayList;

/**
 * Created by Irini on 18/6/2016.
 */

/*

* @startuml

* testdot

* @enduml

*/
public class AdapterPlaceInformation extends ArrayAdapter<TopPlaceInformation> {

    private ArrayList<TopPlaceInformation> place;
    private static LayoutInflater inflater = null;


    public AdapterPlaceInformation(Context contextl, int textViewResourceId, ArrayList<TopPlaceInformation> place) {
        super(contextl, textViewResourceId, place);
        this.place = place;

    }

    public int getCount() {
        return place.size();
    }

    public Product getItem(Product position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }




    public View getView(int position, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.column_list, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        TopPlaceInformation i = place.get(position);

        if (i != null) {

            // reference to the TextViews.

            TextView display_totalNumberOfCheckInsToThisPlace = (TextView) v.findViewById(R.id.column1);
            TextView display_placename = (TextView) v.findViewById(R.id.column2);

            // check to see if each individual textview is null.

            if (display_totalNumberOfCheckInsToThisPlace != null) {
                display_totalNumberOfCheckInsToThisPlace.setText(Integer.toString(i.getTotalNumberOfCheckInsToThisPlace()));
            }
            if (display_placename != null) {
                display_placename.setText(i.getPlaceName());
            }

        }

        return v;

    }


        }



