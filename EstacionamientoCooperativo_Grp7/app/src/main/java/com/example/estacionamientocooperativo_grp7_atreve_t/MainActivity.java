package com.example.estacionamientocooperativo_grp7_atreve_t;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, googleAuth;
    EditText username, pass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Para inicio Google
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    int RC_SING_IN  = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();

        btnLogin= findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    username = findViewById(R.id.etUsername);
                    pass = findViewById(R.id.etPass);
                    Usuario user = new Usuario(username.getText().toString(),pass.getText().toString(),"usuario");
                    //podemos guardar:
                    firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
                    databaseReference = firebaseDatabase.getReference().push();
                    databaseReference.setValue(user);

                    Intent intent = new Intent(MainActivity.this, InitPage.class);
                    startActivity(intent);
                }catch (Exception ex){

                }
            }
        });

        //Ingreso Google
        googleAuth = findViewById(R.id.btnGoogle);
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);
        googleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSingIn();
            }
        });
    }

    public void googleSingIn() {
        Intent intent =  googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SING_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SING_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firbaseAuth(account.getIdToken());

            }
            catch (Exception e){
                Log.e("GoogleSignIn", "Error desconocido: " + e.getMessage(), e);
                Toast.makeText(this,e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firbaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null  );

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();

                    HashMap<String,Object> map = new HashMap<>();
                    map.put("id",user.getUid());
                    map.put("name" , user.getDisplayName());
                    map.put("profile", user.getPhotoUrl().toString());


                    firebaseDatabase.getReference().child("users").child(user.getUid()).setValue(map);

                    Intent intent = new Intent(MainActivity.this,InitPage.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}