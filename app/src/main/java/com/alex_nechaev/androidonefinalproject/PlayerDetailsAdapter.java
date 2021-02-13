package com.alex_nechaev.androidonefinalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class PlayerDetailsAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private List<PlayerDetails> playerDetailsList;

    public PlayerDetailsAdapter(List<PlayerDetails> playerDetails) {
        this.playerDetailsList = playerDetails;
    }


    @Override
    public int getCount() {
        return playerDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return playerDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_details_layout,parent,false);
        }

        TextView playerNameTextView = convertView.findViewById(R.id.player_name_text_view);
        TextView playerScoreTextView = convertView.findViewById(R.id.player_score_text_vew);


        PlayerDetails playerDetails = playerDetailsList.get(position);

        playerNameTextView.setText(playerDetails.getName());
        playerScoreTextView.setText(playerDetails.getScore()+"");

        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
