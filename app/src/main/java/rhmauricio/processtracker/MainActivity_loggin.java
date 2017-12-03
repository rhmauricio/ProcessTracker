package rhmauricio.processtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import rhmauricio.processtracker.Fragmentos.TabFragmentos;

public class MainActivity_loggin extends DrawerActivity {
    String correoR, contraseñaR;
    GoogleApiClient mGoogleApiClient;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loggin);

        //para recibir los datos



        if (findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null ){
                return;
            }

            TabFragmentos fragment = new TabFragmentos();
            Bundle args = new Bundle();
            args.putInt(Tags.TAG_TIPO, 1);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_container, fragment).commit();
        }
        MenuItem item = navigationView.getMenu().getItem(0);
        item.setChecked(true);
    }



    //AHORA EL QUE SE ENCARGA DE GENERAR LA CONEXION


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;
        switch (id) {

            case R.id.mcerrar:
                prefs = getSharedPreferences(Tags.TAG_PREFERENCES,MODE_PRIVATE);
                editor=prefs.edit();

                editor.putInt("optLog",0);
                editor.apply(); // cada que s evaya a subir informacion debe haber un editor.commint

                LoginManager.getInstance().logOut();
                signOut();
                intent = new Intent(MainActivity_loggin.this, loggin.class);

                startActivity(intent);
                finish();//Elimina de la pila el que lo llamo
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });

        FirebaseAuth.getInstance().signOut();
    }
}
