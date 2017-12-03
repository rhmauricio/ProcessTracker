package rhmauricio.processtracker.Fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import rhmauricio.processtracker.DrawerActivity;
import rhmauricio.processtracker.Listas.ChatList;
import rhmauricio.processtracker.Listas.ClientsList;
import rhmauricio.processtracker.Listas.ListChatsAdapter;
import rhmauricio.processtracker.Listas.ListClientsAdapter;
import rhmauricio.processtracker.R;

/**
 * Created by mauri on 16/10/2017.
 */

public class GeneralFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private int posit;
    @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        posit = getArguments().getInt("tipo", 0);
        switch (posit){
            case 1: //Clientes
                ((DrawerActivity) getActivity()).getSupportActionBar().setTitle("Procesos Generales");
                break;
            case 2:
                ((DrawerActivity) getActivity()).getSupportActionBar().setTitle("Lista empleados");
                break;
            case 3:
                ((DrawerActivity) getActivity()).getSupportActionBar().setTitle("Chats");
                break;
            default:
        }
        return view;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<ClientsList> list = new ArrayList<>();
        ArrayList<ChatList> list1 = new ArrayList<>();

        switch(posit){
            case 0:
                list = ClientsList.addItem(R.drawable.ic_account_circle_black_24dp,R.drawable.ic_menu_manage,"Mauricio","Contrato:1052");
                list.add(new ClientsList(R.drawable.ic_account_circle_black_24dp,R.drawable.ic_menu_manage,"Alberto","Contrato:1055"));
                ListClientsAdapter adapter  = new ListClientsAdapter((getActivity()).getApplicationContext(), list);
                setListAdapter(adapter);
                getListView().setOnItemClickListener(this);
                break;

            case 1:
                list =ClientsList.addItem(R.drawable.ic_account_circle_black_24dp,R.drawable.ic_menu_manage,"Mauricio","Contrato:1053");
                list.add(new ClientsList(R.drawable.ic_account_circle_black_24dp,R.drawable.ic_menu_manage,"ALberto","Contrato:1052"));
                ListClientsAdapter adapter1  = new ListClientsAdapter((getActivity()).getApplicationContext(), list);
                setListAdapter(adapter1);
                getListView().setOnItemClickListener(this);
                break;
            case 2:
                list1 = ChatList.addItem(0,"Mauricio","Hola","20:30");
                list1.add(new ChatList(0,"Alberto","","20:50"));
                ListChatsAdapter adapter2  = new ListChatsAdapter((getActivity()).getApplicationContext(), list1);
                setListAdapter(adapter2);
                getListView().setOnItemClickListener(this);
                break;
            case 3:
                list1 = ChatList.addItem(0,"Mauricio","","");
                list1.add(new ChatList(0,"Alberto","",""));
                ListChatsAdapter adapter3  = new ListChatsAdapter((getActivity()).getApplicationContext(), list1);
                setListAdapter(adapter3);
                getListView().setOnItemClickListener(this);
                break;

        }

        /*ListClientsAdapter adapter  = new ListClientsAdapter((getActivity()).getApplicationContext(), list);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);*/
    }

    protected Intent intent;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

    }
    protected Runnable delay = new Runnable() {
        @Override
        public void run() {
            startActivity(intent);
        }
    };
}
