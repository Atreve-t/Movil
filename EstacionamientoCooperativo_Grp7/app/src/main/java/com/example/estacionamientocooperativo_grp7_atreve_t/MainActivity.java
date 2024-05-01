package com.example.estacionamientocooperativo_grp7_atreve_t;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
    Button btnLogin, googleAuth, btnRegister;
    EditText email, pass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference usuariosRef;

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
        // Obtener referencia a la colección "usuarios"
        usuariosRef = firebaseDatabase.getReference().child("usuarios");
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    email = findViewById(R.id.etUsername);
                    pass = findViewById(R.id.etPass);

                    // Realizar una consulta para buscar el usuario con el correo electrónico proporcionado
                    usuariosRef.orderByChild("email").equalTo(email.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // El correo electrónico ya está registrado
                                Toast.makeText(MainActivity.this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                // El correo electrónico no está registrado, se puede crear el nuevo usuario
                                Usuario user = new Usuario(email.getText().toString(), pass.getText().toString(), "");

                                usuariosRef.child(usuariosRef.push().getKey()).setValue(user);
                                SignIn();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Manejar cualquier error de base de datos
                            Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                            Toast.makeText(MainActivity.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception ex) {
                    // Manejar cualquier excepción
                    Log.e("Registro", "Error al registrar usuario: " + ex.getMessage(), ex);
                    Toast.makeText(MainActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    email = findViewById(R.id.etUsername);
                    pass = findViewById(R.id.etPass);

                    // Realizar una consulta para buscar el usuario con el nombre de usuario proporcionado
                    usuariosRef.orderByChild("email").equalTo(email.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean userFound = false;
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                Usuario user = userSnapshot.getValue(Usuario.class);
                                if (user != null && user.getPassword().equals(pass.getText().toString())) {
                                    // El usuario existe y la contraseña es correcta
                                    userFound = true;
                                    SignIn();
                                    break;
                                }
                            }
                            if (!userFound) {
                                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Manejar cualquier error de base de datos
                            Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                            Toast.makeText(MainActivity.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception ex) {
                    // Manejar cualquier excepción
                    Log.e("Login", "Error al iniciar sesión: " + ex.getMessage(), ex);
                    Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
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

    public void SignIn(){
        // Guardar el correo electrónico en SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email.getText().toString());
        editor.apply();

        Intent intent = new Intent(MainActivity.this, InitPage.class);
        startActivity(intent);
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


                    firebaseDatabase.getReference().child("usuarios").child(user.getUid()).setValue(map);

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