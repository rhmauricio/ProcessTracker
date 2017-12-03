package rhmauricio.processtracker;

import android.os.Bundle;

public class InformacionActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        getSupportActionBar().setTitle("Informacion");
    }
}
