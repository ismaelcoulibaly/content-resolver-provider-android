package com.example.inf1031_travail2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        switch(v.getId()) {

            case R.id.ajouter_btn:
                //Pour insérer des données dans le dictionnaire
                //Defines a new Uri object that receives the result of the insertion
                Uri newUri;

                ContentValues newValues = new ContentValues();

                newValues.put(UserDictionary.Words.APP_ID, appIdEditTxt.getText().toString());
                newValues.put(UserDictionary.Words.LOCALE, localeEditTxt.getText().toString());
                newValues.put(UserDictionary.Words.WORD, wordEditTxt.getText().toString());
                newValues.put(UserDictionary.Words.FREQUENCY, frequencyEditTxt.getText().toString());

                newUri = getContentResolver().insert(
                        UserDictionary.Words.CONTENT_URI,   // the user dictionary content URI
                        newValues                          // the values to insert
                );
                Toast.makeText(this, "Uri = " + newUri, Toast.LENGTH_LONG).show();

                //To reload the activity
                finish();
                startActivity(getIntent());
                break;


            case R.id.supprimer_ligne_btn:
                //Pour delete les données dans le dictionnaire

                String selection = UserDictionary.Words.FREQUENCY + " = ?";
                String[] selectionArgs = {"200"} ;

                int numberDeleted = 0;
                numberDeleted = getContentResolver().delete(
                        UserDictionary.Words.CONTENT_URI,selection, selectionArgs);

                Toast.makeText(this, "row matching deleted = " + numberDeleted, Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
                break;

        }


    }
}