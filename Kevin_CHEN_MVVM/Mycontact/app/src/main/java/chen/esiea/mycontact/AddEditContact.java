package chen.esiea.mycontact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditContact extends AppCompatActivity {

    public static final String EXTRA_ID="chen.esiea.mycontact.EXTRA_ID";
    public static final String EXTRA_NOM="chen.esiea.mycontact.EXTRA_NOM";
    public static final String EXTRA_PRENOM="chen.esiea.mycontact.EXTRA_PRENOM";
    public static final String EXTRA_MAIL="chen.esiea.mycontact.EXTRA_MAIL";
    public static final String EXTRA_TEL="chen.esiea.mycontact.EXTRA_TEL";
    public static final String EXTRA_DESCRI="chen.esiea.mycontact.EXTRA_DESCRI";

    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextEmail;
    private EditText editTextTel;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editTextNom = findViewById(R.id.edit_nom);
        editTextPrenom = findViewById(R.id.edit_prenom);
        editTextEmail = findViewById(R.id.edit_email);
        editTextTel = findViewById(R.id.edit_tel);
        editTextDescription = findViewById(R.id.edit_description);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextNom.setText(intent.getStringExtra(EXTRA_NOM));
            editTextPrenom.setText(intent.getStringExtra(EXTRA_PRENOM));
            editTextEmail.setText(intent.getStringExtra(EXTRA_MAIL));
            editTextTel.setText(intent.getStringExtra(EXTRA_TEL));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRI));
        }
        else {
            setTitle("Add Note");
        }

    }
    private void saveContact(){
        String nom=editTextNom.getText().toString();
        String prenom=editTextPrenom.getText().toString();
        String mail=editTextEmail.getText().toString();
        String tel=editTextTel.getText().toString();
        String description=editTextDescription.getText().toString();

        if(nom.trim().isEmpty()||prenom.trim().isEmpty()||mail.trim().isEmpty()||tel.trim().isEmpty()||description.trim().isEmpty()){
            Toast.makeText(this,"Please complte all fields",Toast.LENGTH_LONG).show();
            return;
        }
        Intent data=new Intent();
        data.putExtra(EXTRA_NOM,nom);
        data.putExtra(EXTRA_PRENOM,prenom);
        data.putExtra(EXTRA_MAIL,mail);
        data.putExtra(EXTRA_TEL,tel);
        data.putExtra(EXTRA_DESCRI,description);

        int id=getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
