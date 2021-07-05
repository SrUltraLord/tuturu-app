package com.example.clift;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.zip.Inflater;

public class chatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


    }// fin del oncreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_cerrar:

               /* AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>({
                                @Override
                                public void OnComplete(@NonNull Task<Void> task) {
                                    finish();
                                Toast.makeText(chatActivity.this, "Cerrando Sesión", Toast.LENGTH_LONG).show();
                                //aki va redirigirse al login.
                                }
                        }));*/

                Toast.makeText(this , "Cerrando Sesión", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}