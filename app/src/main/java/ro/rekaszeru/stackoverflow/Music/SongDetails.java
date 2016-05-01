package ro.rekaszeru.stackoverflow.Music;

/**
 * Created by ananth on 5/1/2016.
 */
public class SongDetails {
    public String trackName;
    public String dataSrc;
    public String trackDuriation;


    public SongDetails(String trackName, String dataSrc, String trackDuriation) {
        this.trackName = trackName;
        this.dataSrc = dataSrc;
        this.trackDuriation = trackDuriation;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getDataSrc() {
        return dataSrc;
    }

    public String getTrackDuriation() {
        return trackDuriation;
    }


}
