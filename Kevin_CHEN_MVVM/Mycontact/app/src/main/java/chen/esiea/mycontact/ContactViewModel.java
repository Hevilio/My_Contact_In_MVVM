package chen.esiea.mycontact;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepositary repositary;
    private LiveData<List<Contact>> allContact;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        repositary= new ContactRepositary(application);
        allContact=repositary.getAllContact();
    }

    public void insert(Contact contact){
        repositary.insert(contact);
    }

    public void update(Contact contact){
        repositary.update(contact);
    }

    public  void delete(Contact contact){
        repositary.delete(contact);
    }

    public void deleteAllNotes(){
        repositary.deleteAllcontact();
    }

    public LiveData<List<Contact>> getAllContact() {
        return allContact;
    }
}
