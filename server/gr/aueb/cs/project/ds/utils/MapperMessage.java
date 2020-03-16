package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapperMessage implements Serializable {
    private static final long serialVersionUID = 5L;
    private Map<String, List<CheckInInformation>> mapperResults = null;

    public MapperMessage(Map<String, List<CheckInInformation>> map) {
        this.mapperResults = new  TreeMap<String, List<CheckInInformation>>(map);
    }

    public Map<String, List<CheckInInformation>> getMapperResults() {
        return this.mapperResults;
    }

    @Override
    public String toString() {
        return "MapperMessage [mapperResults=" + mapperResults + "]";
    }

}
