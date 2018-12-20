package chen.esiea.mycontact;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//Run time will call all nececity librairiesfor the entity

@Entity(tableName="contact_table")

public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String description;
    //private int priority; Don't forget the getter of priority if you add it
    public Contact(String nom, String prenom, String email, String tel, String description/*,*int priority*/) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.description = description;
        //this.priority=priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getDescription() {
        return description;
    }
}
