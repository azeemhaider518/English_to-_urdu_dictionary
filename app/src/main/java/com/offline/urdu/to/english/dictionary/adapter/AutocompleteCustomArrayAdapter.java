package com.offline.urdu.to.english.dictionary.adapter;

/**
 * Created by SST on 7/4/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.offline.urdu.to.english.dictionary.MainActivity;
import com.offline.urdu.to.english.dictionary.R;
import com.offline.urdu.to.english.dictionary.model.WordInfo;

import java.util.List;


public class AutocompleteCustomArrayAdapter extends ArrayAdapter<WordInfo> {

    final String TAG = "AutocompleteCustomArrayAdapter.java";

    Context mContext;
    int layoutResourceId;
    static List<WordInfo> infoList = null;
    static WordInfo info;

    public AutocompleteCustomArrayAdapter(Context mContext, int layoutResourceId, List<WordInfo> items) {

        super(mContext, layoutResourceId, items);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.infoList = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try{

            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            info=infoList.get(position);

            TextView txt_word = (TextView) convertView.findViewById(R.id.txt_word);
            txt_word.setText(info.getEnglish().toString()+" - "+info.getUrdu().toString());

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }


}