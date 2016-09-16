package com.phonegap.wordpowerswahili;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemListActivity extends AppCompatActivity {
    private static final String TAG_ITEMS = "items";
    private static final String TAG_WORDID = "WordID";
    private static final String TAG_ENGLISH = "English";
    private static final String TAG_SWAHILI = "Swahili";
    private static final String TAG_CATEGORYID = "CategoryID";
    private static final String TAG_SOUND = "Sound";
    private static final String TAG_HASMP3 = "HasMP3";
    static String categoryID;
    static MediaPlayer mediaPlayer;
    Bundle bundle = new Bundle();
    SqliteController controller;

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        bundle = getIntent().getExtras();
        categoryID = bundle.getString("categoryID");
        controller = new SqliteController(ItemListActivity.this);
        DataSearch();
    }

    private void DataSearch() {
        try {

            ListView list = (ListView) findViewById(R.id.itemData);
            ArrayList<HashMap<String, String>> wordList = controller.getWordsByCategory(categoryID);

            SimpleAdapter dataAdapter = new SimpleAdapter(ItemListActivity.this, wordList, R.layout.activity_item, new String[]{"rowsec"},
                    new int[]{R.id.secListRow});
            list.setAdapter(new DataListAdapter(ItemListActivity.this, dataAdapter));
            setListViewHeightBasedOnItems(list);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ItemListActivity.this, CategoryListActivity.class));
    }

    public class DataListAdapter extends BaseAdapter {
        private Context context;
        private SimpleAdapter dataAdap;

        public DataListAdapter(Context c, SimpleAdapter da) {
            context = c;
            dataAdap = da;
        }

        public int getCount() {
            return dataAdap.getCount();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                //convertView = inflater.inflate(R.layout.list_item_row, null);
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_row, parent, false);
            }
            // LinearLayout secListRow = (LinearLayout) convertView.findViewById(R.id.secListRow);

            final TextView mEnglish = (TextView) convertView.findViewById(R.id.txtEnglish);
            final TextView mSwahili = (TextView) convertView.findViewById(R.id.txtSwahili);
            final Button buttonPlay = (Button) convertView.findViewById(R.id.buttonPlaySwahili);


            final HashMap<String, String> o = (HashMap<String, String>) dataAdap.getItem(position);
            mEnglish.setText(o.get("English"));
            mSwahili.setText(o.get("Swahili"));

            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap map = controller.getWordInfo(o.get("WordID"));
                    String mCategory = map.get(TAG_CATEGORYID).toString();
                    String mFile = map.get(TAG_SOUND).toString();
                    String filePath = Environment.getExternalStorageDirectory() + "/SWAHILI/SOUND/" + mCategory + "/" + mFile + ".mp3";
                    AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, AudioManager.FLAG_PLAY_SOUND);

                    try {

                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                            }
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(filePath);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });


            return convertView;
        }
    }


}
