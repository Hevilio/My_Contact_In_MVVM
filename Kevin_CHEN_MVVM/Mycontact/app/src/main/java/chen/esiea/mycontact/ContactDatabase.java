package chen.esiea.mycontact;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomMasterTable;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Contact.class},version=1)

public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;
    public  abstract ContactDao contactDao();

    public static synchronized ContactDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class,"contact_database")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Defaultdb(instance).execute();
        }
    };
    private static class Defaultdb extends AsyncTask<Void,Void,Void>{
        private  ContactDao contactDao;
        private Defaultdb(ContactDatabase db){
            contactDao=db.contactDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.insert(new Contact("CHEN","Kevin","chen@et.esiea.fr","000000000",
                    "Student in 4th year at ESIEA Chinese: Alone for the project"));
            contactDao.insert(new Contact("Switch","Right to delete","chen@et.esiea.fr","000000000",
                    "Student in 4th year at ESIEA Chinese: Alone for the project"));
            contactDao.insert(new Contact("Edit","click on me","chen@et.esiea.fr","000000000",
                    "Student in 4th year at ESIEA Chinese: Alone for the project"));
            contactDao.insert(new Contact("Add","click on the plus","chen@et.esiea.fr","000000000",
                    "Student in 4th year at ESIEA Chinese: Alone for the project"));
            return null;
        }
    }
}
