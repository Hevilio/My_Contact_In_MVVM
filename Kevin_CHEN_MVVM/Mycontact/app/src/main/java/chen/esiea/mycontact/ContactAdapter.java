package chen.esiea.mycontact;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contact> contacts = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = contacts.get(position);
        holder.textViewNom.setText(currentContact.getNom());
        holder.textViewPrenom.setText(currentContact.getPrenom());
        holder.textViewEmail.setText(currentContact.getEmail());
        holder.textViewTel.setText(currentContact.getTel());
        holder.textViewDescription.setText(currentContact.getDescription());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public Contact getContactAt(int position) {
        return contacts.get(position);
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView textViewNom;
        private TextView textViewPrenom;
        private TextView textViewEmail;
        private TextView textViewTel;
        private TextView textViewDescription;

        public ContactHolder(View itemView) {
            super(itemView);
            textViewNom = itemView.findViewById(R.id.nom_view);
            textViewPrenom = itemView.findViewById(R.id.prenom_view);
            textViewEmail = itemView.findViewById(R.id.mail_view);
            textViewTel = itemView.findViewById(R.id.tel_view);
            textViewDescription = itemView.findViewById(R.id.description_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(contacts.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
