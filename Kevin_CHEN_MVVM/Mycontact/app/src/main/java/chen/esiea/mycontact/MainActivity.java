package chen.esiea.mycontact;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import chen.esiea.mycontact.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private ContactViewModel contactViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        FloatingActionButton button_add = findViewById(R.id.button_add_contact);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditContact.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ContactAdapter adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContact().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                adapter.setContacts(contacts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent=new Intent(MainActivity.this,AddEditContact.class);
                intent.putExtra(AddEditContact.EXTRA_ID,contact.getId());
                intent.putExtra(AddEditContact.EXTRA_NOM,contact.getNom());
                intent.putExtra(AddEditContact.EXTRA_PRENOM,contact.getPrenom());
                intent.putExtra(AddEditContact.EXTRA_MAIL,contact.getEmail());
                intent.putExtra(AddEditContact.EXTRA_TEL,contact.getTel());
                intent.putExtra(AddEditContact.EXTRA_DESCRI,contact.getDescription());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String nom = data.getStringExtra(AddEditContact.EXTRA_NOM);
            String prenom = data.getStringExtra(AddEditContact.EXTRA_PRENOM);
            String mail = data.getStringExtra(AddEditContact.EXTRA_MAIL);
            String tel = data.getStringExtra(AddEditContact.EXTRA_TEL);
            String description = data.getStringExtra(AddEditContact.EXTRA_DESCRI);

            Contact contact = new Contact(nom, prenom, mail, tel, description);
            contactViewModel.insert(contact);

            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id=data.getIntExtra(AddEditContact.EXTRA_ID,-1);
            if(id==-1){
                Toast.makeText(this, "Contact can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String nom = data.getStringExtra(AddEditContact.EXTRA_NOM);
            String prenom = data.getStringExtra(AddEditContact.EXTRA_PRENOM);
            String mail = data.getStringExtra(AddEditContact.EXTRA_MAIL);
            String tel = data.getStringExtra(AddEditContact.EXTRA_TEL);
            String description = data.getStringExtra(AddEditContact.EXTRA_DESCRI);

            Contact contact = new Contact(nom, prenom, mail, tel, description);
            contact.setId(id);
            contactViewModel.update(contact);
            Toast.makeText(this, "Contact Uptdated", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Contact unsaved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                contactViewModel.deleteAllNotes();
                Toast.makeText(this, "Delete all", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
