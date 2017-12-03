package rhmauricio.processtracker.Listas;

/**
 * Created by mauri on 17/10/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rhmauricio.processtracker.R;


public class ListChatsAdapter extends ArrayAdapter<ChatList> {

    public ListChatsAdapter(@NonNull Context context, ArrayList<ChatList> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ChatList itemCustomList = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_chats, parent, false);
        }

        // Lookup view for data population
        ImageView tvImage=(ImageView) convertView.findViewById(R.id.list_time) ;
        TextView tvTitle = (TextView) convertView.findViewById(R.id.list_title);
        TextView tvDetail = (TextView) convertView.findViewById(R.id.list_detail);
        TextView tvHora = (TextView) convertView.findViewById(R.id.list_action);



        // Populate the data into the template view using the data object
        tvTitle.setText(itemCustomList.getNombre());
        //tvDetail.setText(itemCustomList.getUltimosms());
        //tvImage.setImageResource(itemCustomList.getImageId());

         if (itemCustomList.getUltimosms() != ""){
             tvDetail.setText(itemCustomList.getUltimosms());
        }
        if (itemCustomList.getImageId() != 0){
            tvImage.setImageResource(itemCustomList.getImageId());
        }
        if (itemCustomList.getUlthora() != ""){
            tvHora.setText(itemCustomList.getUlthora());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}