package com.example.inf1031_travail2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ajouterBtn, supprimerLigneBtn;
    private EditText appIdEditTxt, localeEditTxt, wordEditTxt, frequencyEditTxt;
    private TextView donneesTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ajouterBtn = findViewById(R.id.ajouter_btn);
        supprimerLigneBtn = findViewById(R.id.supprimer_ligne_btn);
        appIdEditTxt = findViewById(R.id.app_id_edittxt);
        localeEditTxt = findViewById(R.id.local_edittxt);
        wordEditTxt = findViewById(R.id.word_edittxt);
        frequencyEditTxt = findViewById(R.id.frequency_edittxt);
        donneesTxtView = findViewById(R.id.donnees_txtview);

        ajouterBtn.setOnClickListener(this);
        supprimerLigneBtn.setOnClickListener(this);

        getDonnees();
    }


    private void getDonnees() {

        String[] Projection =
                {
                        UserDictionary.Words._ID,
                        UserDictionary.Words.WORD,
                        UserDictionary.Words.LOCALE,
                        UserDictionary.Words.FREQUENCY
                };
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = UserDictionary.Words._ID;
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(UserDictionary.Words.CONTENT_URI,
                Projection, selection, selectionArgs, orderBy);

        if(cursor != null && cursor.getCount()> 0){

            StringBuilder stringBuilder = new StringBuilder("");
            while (cursor.moveToNext()){

                stringBuilder.append(cursor.getString(0)+" , "
                        +cursor.getString(1)+" , "+cursor.getString(2)+
                        " , " + cursor.getString(3) + "\n");
            }
            donneesTxtView.setText(stringBuilder.toString());
        }else{
            donneesTxtView.setText("Impossible d'acceder aux donnees");
        }

    }

    @Override
    public void onClick(View v) {

    }
}