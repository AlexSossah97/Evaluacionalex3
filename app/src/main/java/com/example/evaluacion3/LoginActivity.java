package com.example.evaluacion3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // Declaración de variables para los elementos de la interfaz de usuario
    Button btn_login;
    EditText email, password;
    FirebaseAuth mAuth;  // Objeto  para manejar la autenticación con Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");  // Configura el título de la actividad

        // Inicialización del objeto FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Enlace de variables con elementos de la interfaz de usuario
        email = findViewById(R.id.correo);
        password = findViewById(R.id.contrasena);
        btn_login = findViewById(R.id.btn_ingresar);

        // Configuración del evento de clic para el botón de inicio de sesión
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtención del correo electrónico y la contraseña ingresados por el usuario
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                // Verificación de que se hayan ingresado los datos
                if (emailUser.isEmpty() && passUser.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    // Llamada al método para iniciar sesión
                    loginUser(emailUser, passUser);
                }
            }
        });
    }

    // Método para iniciar sesión en Firebase
    private void loginUser(String emailUser, String passUser) {
        // Intento de inicio de sesión con Firebase
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Verificación si el inicio de sesión fue exitoso
                if (task.isSuccessful()){
                    finish();  // Cierra la actividad actual
                    // Inicia la actividad principal (MainActivity)
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                } else {
                    // Muestra un mensaje en caso de error en el inicio de sesión
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Muestra un mensaje en caso de fallo en el inicio de sesión
                Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        // Verificación si ya hay un usuario autenticado
        if (user != null){
            // Si hay un usuario autenticado, inicia directamente la actividad principal
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();  // Cierra la actividad actual
        }
    }
}
