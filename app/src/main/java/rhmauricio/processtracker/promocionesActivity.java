package rhmauricio.processtracker;

import android.os.Bundle;

public class promocionesActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);
        getSupportActionBar().setTitle("Promociones");
    }
}
