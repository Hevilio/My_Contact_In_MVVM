package chen.esiea.mycontact;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepositary {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContact;

    public ContactRepositary(Application app){
        ContactDatabase data=ContactDatabase.getInstance(app);
        contactDao=data.contactDao();
        allContact=contactDao.getAllContact();
    }
    public void insert(Contact contact){
        new InsertContactAsyncTask(contactDao).execute(contact);
    }
    public void update(Contact contact){
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }
    public void delete(Contact contact){
        new DeleteContactAsyncTask(contactDao).execute(contact);
    }
    public void deleteAllcontact(){
        new DeleteAllContactAsyncTask(contactDao).execute();
    }
    public LiveData<List<Contact>> getAllContact(){
        return allContact;
    }


    private  static class InsertContactAsyncTask extends AsyncTask<Contact,Void,Void>{
        private ContactDao contactDao;
        private InsertContactAsyncTask(ContactDao contactDao){
            this.contactDao=contactDao;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }

    }
    private  static class DeleteContactAsyncTask extends AsyncTask<Contact,Void,Void>{
        private ContactDao contactDao;
        private DeleteContactAsyncTask(ContactDao contactDao){
            this.contactDao=contactDao;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.delete(contacts[0]);
            return null;
        }

    }
    private  static class UpdateContactAsyncTask extends AsyncTask<Contact,Void,Void>{
        private ContactDao contactDao;
        private UpdateContactAsyncTask(ContactDao contactDao){
            this.contactDao=contactDao;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.update(contacts[0]);
            return null;
        }

    }
    private  static class DeleteAllContactAsyncTask extends AsyncTask<Void,Void,Void>{
        private ContactDao contactDao;
        private DeleteAllContactAsyncTask(ContactDao contactDao){
            this.contactDao=contactDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.deleteAllContact();
            return null;
        }

    }
}
