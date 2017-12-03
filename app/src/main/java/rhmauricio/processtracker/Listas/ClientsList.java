package rhmauricio.processtracker.Listas;

import java.util.ArrayList;

/**
 * Created by mauri on 16/10/2017.
 */

public class ClientsList {
    private int imageId;
    private int actionItem;
    private String title;
    private String detail;

    public ClientsList(int imageId, int actionItem, String title, String detail) {
        this.imageId = imageId;
        this.actionItem = actionItem;
        this.title = title;
        this.detail = detail;
    }

    public int getImageId() {
        return imageId;
    }

    public int getActionItem() {
        return actionItem;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public static ArrayList<ClientsList> addItem(int _imageId, int _actionItem, String _title, String _detail) {
        ArrayList<ClientsList> users = new ArrayList<>();
        users.add(new ClientsList(_imageId, _actionItem, _title, _detail));
        return users;
    }
}
