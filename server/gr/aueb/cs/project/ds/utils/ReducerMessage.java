package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class ReducerMessage implements Serializable{
    private static final long serialVersionUID = -8190041287423718709L;
    Map<String, TopPlaceInformation> reducerResult = null;
    
    public ReducerMessage( Map<String, TopPlaceInformation> fResult) {
        reducerResult = new TreeMap<String, TopPlaceInformation>(fResult);
    }

    public Map<String, TopPlaceInformation> getReducerResult() {
        return reducerResult;
    }

    @Override
    public String toString() {
        return "ReducerMessage [finalResult=" + reducerResult + "]";
    }
    
}
