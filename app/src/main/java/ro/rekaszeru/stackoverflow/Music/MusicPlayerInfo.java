package ro.rekaszeru.stackoverflow.Music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.rekaszeru.stackoverflow.Utils.DL;

/**
 * Created by ananth on 5/1/2016.
 */
public class MusicPlayerInfo {

    Context context;
    public     MusicPlayerInfo(Context context){
        this.context = context;
    }

    HashMap<String, List<String>> playListAndSongsInfo = new HashMap<String, List<String>>();


    public HashMap<String, List<String>>   getdefaultMusicPlayList(){
        int index = 0 ;
        ArrayList<String> defaultPlayList;
        final MediaPlayer player = new MediaPlayer();
        List<String> paths = new ArrayList<String>();

        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        final String idKey = MediaStore.Audio.Playlists._ID;
        final String nameKey = MediaStore.Audio.Playlists.NAME;
        final String[] proj = { idKey, nameKey };

        Cursor playListCursor= context.getContentResolver().query(tempPlaylistURI, proj, null, null, null);

        if(playListCursor == null){
            DL.p("Not having any Playlist on phone --------------");
            return null;//don't have list on phone
        }
        String playListName = null;
/*
        DL.p(">>>>>>>  CREATING AND DISPLAYING LIST OF ALL CREATED PLAYLIST  <<<<<<");
        defaultPlayList = new ArrayList<String>();

        for(int i = 0; i <playListCursor.getCount() ; i++)
        {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            DL.p("> " + i + "  playListName : " + playListName);
            defaultPlayList.add(playListName);
        }
*/

        playListCursor.moveToFirst();
        for(int i = 0; i <playListCursor.getCount() ; i++) {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            this.playTrackFromPlaylist(playListCursor.getLong(playListCursor.getColumnIndex(idKey)),playListName);
        }

        if(playListCursor != null)
            playListCursor.close();

        return playListAndSongsInfo;

    }

    public void  playTrackFromPlaylist(final long playListID, String playListName) {
        final ContentResolver resolver = context.getContentResolver();
        final Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playListID);
        final String dataKey = MediaStore.Audio.Media.DATA;
        final String titleKey = android.provider.MediaStore.Audio.Media.TITLE;
        final String duriationKey =  MediaStore.Audio.Media.DURATION;

//  android.provider.MediaStore.Audio.Media.DATA,android.provider.MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DURATION
        String projection[]={dataKey,titleKey,duriationKey};

        Cursor tracks = resolver.query(uri, projection, null, null, null);
//        HashMap<String, List<String>> child_list =new HashMap<String,List<String>>();

        if (tracks != null) {
            int count = tracks.getCount();
            DL.t(context, "Numeber of Songs in playList " + count);
            DL.p("Numeber of Songs in playList " + count);
            tracks.moveToFirst();
            String songInfo ;
            List<String> songsListOfPlayList = new ArrayList<String>();

            do {

                int  duration=(Integer.parseInt(tracks.getString(tracks.getColumnIndex(duriationKey))))/1000;
                int min = duration / 60 ;
                int sec = duration % 60 ;
                final String dataPath = tracks.getString(tracks.getColumnIndex(dataKey));
                songInfo = (" >> \n " + tracks.getString(tracks.getColumnIndex(titleKey)) +
                        "  [ " + min + " : " + sec + " ]\n path : " + dataPath);

                songsListOfPlayList.add(songInfo);
            }while (tracks.moveToNext());

            DL.p("adding into HashMap : "+playListName);
            playListAndSongsInfo.put(playListName, songsListOfPlayList);
            tracks.close();
        }
      //  return child_list;
    }

}
