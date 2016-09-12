package com.phonegap.wordpowerswahili;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class ItemListActivity extends AppCompatActivity {
    Bundle bundle = new Bundle();
    SqliteController controller;
    static String categoryID;
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
            ArrayList<HashMap<String, String>> wordList = controller.getWordCategory(categoryID);

            SimpleAdapter dataAdapter = new SimpleAdapter(ItemListActivity.this, wordList, R.layout.activity_item, new String[]{"rowsec"},
                    new int[]{R.id.secListRow});
            list.setAdapter(new DataListAdapter(ItemListActivity.this, dataAdapter));
        } catch (Exception e) {
           return;
        }
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
                convertView = inflater.inflate(R.layout.list_item_row, null);
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
                    controller.getWordInfo(o.get("WordID"));
                }
            });


            return convertView;
        }
    }


}
