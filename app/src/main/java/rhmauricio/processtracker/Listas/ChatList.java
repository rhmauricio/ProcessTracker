package rhmauricio.processtracker.Listas;

import java.util.ArrayList;

/**
 * Created by mauri on 17/10/2017.
 */

public class ChatList {
    private int imageId;
    private String nombre;
    private String ultimosms;
    private String ulthora;

    public ChatList(int imageId, String nombre, String ultimosms, String ulthora) {
        this.imageId = imageId;
        this.nombre = nombre;
        this.ultimosms = ultimosms;
        this.ulthora  = ulthora;

    }

    public int getImageId() {
        return imageId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUltimosms() {
        return ultimosms;
    }

    public String getUlthora() {
        return ulthora;
    }



    public static ArrayList<ChatList> addItem(int _imageId, String _nombre, String _ultimosms, String _hora) {
        ArrayList<ChatList> users = new ArrayList<>();
        users.add(new ChatList(_imageId, _nombre, _ultimosms, _hora));
        return users;
    }
}
