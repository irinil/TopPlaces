package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;
import java.util.List;

public class ServerMessage implements Serializable {
    private static final long serialVersionUID = 2174770808410757173L;
    List<TopPlaceInformation> places = null;

    ServerMessage( List<TopPlaceInformation> places){
        this.places = places;
    }


   public List<TopPlaceInformation> getPlaces(){
       return places;
   }


}
