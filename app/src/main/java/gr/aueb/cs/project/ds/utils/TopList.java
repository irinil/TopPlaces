package gr.aueb.cs.project.ds.utils;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


/**
 * Created by Irini on 26/5/2016.
 */
public class TopList extends ListFragment {

    ArrayList<TopPlaceInformation> places =  new ArrayList<TopPlaceInformation>();
    AdapterPlaceInformation adapterPlaceInformation;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {

        ViewGroup rootview =(ViewGroup) inflater.inflate(R.layout.top_list, container, false);


         adapterPlaceInformation = new AdapterPlaceInformation(getContext(),0,places);



             setListAdapter(adapterPlaceInformation);

             setRetainInstance(true);


        return rootview;
    }



    public void setPlaces(ArrayList<TopPlaceInformation> places){
        this.places=places;
    }




    @Override
    public void onListItemClick(ListView list,View view,int position,long id){
        ViewGroup viewGroup = (ViewGroup) view;
        TextView text = (TextView) viewGroup.findViewById(R.id.listviewla);
        Toast.makeText(getActivity(),text.getText().toString(),Toast.LENGTH_LONG).show();


    }



}