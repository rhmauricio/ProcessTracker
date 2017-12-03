package rhmauricio.processtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

public class loggin extends AppCompatActivity {
    String correoR="", contraseñaR="",correo="", contraseña="", picture="";
    EditText eCorreo, eContraseña;
    LoginButton loginButton;
    CallbackManager callbackManager;
    public static int opLog=0;
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        mAuth = FirebaseAuth.getInstance();

        prefs = getSharedPreferences(Tags.TAG_PREFERENCES, MODE_PRIVATE);
        loginButton=(LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile","email");
        callbackManager=CallbackManager.Factory.create(); //fuerza a que se ejeucte el metodo de respuesta
        eCorreo=(EditText)findViewById(R.id.eCorreo);
        eContraseña=(EditText)findViewById(R.id.eContraseña);


        //LOGIN GOOGLE
        //GSO ME DA LOS ACCESOS A LOS SERVICIOS DE GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //AHORA EL QUE SE ENCARGA DE GENERAR LA CONEXION
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                //.enableAutoManage(this /* FragmentActivity */ (contexto), this /* OnConnectionFailedListener */)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "Error en login",
                        Toast.LENGTH_SHORT).show();

                    }
                }  /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // el listener del boton

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        //registro con face

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Login Exitoso",
                        Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
                Bundle parameters = new Bundle();

                parameters.putString("fields", "id,name,link,gender,birthday,email,picture");
                /*GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    // get email and id of the user
                                    try{
                                        String uriPicture = "";
                                        if (me.getString("email") != null)
                                            prefs.edit().putString(Tags.TAG_CORREO, me.getString("email")).apply();

                                        if(me.getString("name") != null)
                                            prefs.edit()
                                                    .putString(Tags.TAG_NOMBRE,me.getString("name"))
                                                    .apply();

                                        if (me.getString("picture") != null){
                                            uriPicture = new JSONObject(
                                                    new JSONObject(me.getString("picture")).getString("data")).getString("url");
                                            prefs.edit()
                                                    .putString(Tags.TAG_URLIMAGEN,uriPicture)
                                                    .apply();
                                        }


                                    }catch (Exception e){
                                        Log.d("FB Error", e.toString());
                                    }
                                    opLog=2;
                                    goMainActivity();
                                }
                            }
                        });
                request.setParameters(parameters);
                request.executeAsync();*/
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login Cancelado",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Error de Login",
                        Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void regitrarse(View view) {
        //cuando se de click en registrar se llama a la actividad registro y debemos esperar datos

        Intent intent = new Intent(loggin.this, registro.class); // de donde vengo y a donde voy
        startActivityForResult(intent,1234); // el codigo es para que el registro activity responda con ese codigo

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1234 && resultCode==RESULT_OK) {
            opLog = 1;

        }else if(requestCode==5678){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);

            try {
                if(account.getEmail()!=null)
                    prefs.edit().putString(Tags.TAG_CORREO, account.getEmail()).apply();
                else
                    prefs.edit().putString(Tags.TAG_CORREO, "No tiene correo").apply();
                if(account.getDisplayName() != null)
                    prefs.edit().putString(Tags.TAG_NOMBRE,account.getDisplayName()).apply();
                else
                    prefs.edit().putString(Tags.TAG_NOMBRE,"No tiene nombre").apply();
                if(account.getPhotoUrl() != null)
                    prefs.edit().putString(Tags.TAG_URLIMAGEN,account.getPhotoUrl().toString()).apply();
                else
                    prefs.edit().putString(Tags.TAG_URLIMAGEN,null).apply();

            }catch (Exception e){
                Log.d("Google Error", e.toString());

            }

            //handleSignInResult(result);


        }else{
            callbackManager.onActivityResult(requestCode,resultCode,data);

            opLog=2;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void inciar(View view) {

        correo=eCorreo.getText().toString();
        contraseña=eContraseña.getText().toString();

        contraseñaR=prefs.getString(Tags.TAG_CONTRASEÑA,"");
        correoR=prefs.getString(Tags.TAG_CORREO,"");

        if(correo.isEmpty() || contraseña.isEmpty()){
            Toast t= Toast.makeText(getApplication().getApplicationContext(),"Espacios Vacios",Toast.LENGTH_SHORT);
            t.show();

        }else if(correoR.isEmpty() || contraseñaR.isEmpty()) {
            Toast t= Toast.makeText(getApplication().getApplicationContext(),"Debe registrar los datos",Toast.LENGTH_SHORT);
            t.show();

        }else if(contraseña.equals(contraseñaR) && correo.equals(correoR)){
           goMainActivity();
        }else{
            Toast t= Toast.makeText(getApplication().getApplicationContext(),"Datos invalidos",Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void goMainActivity(){

        editor=prefs.edit();editor.putInt("optLog",opLog);

        editor.putInt("optLog",opLog);
        editor.commit();

        Intent intent = new Intent(loggin.this, MainActivity_loggin.class);
        startActivity(intent);
        finish();
    }
    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 5678);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());

                            //Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                              //      Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String username= user.getDisplayName();
                            String corre =user.getEmail();
                            goMainActivity();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Error", "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }
}
