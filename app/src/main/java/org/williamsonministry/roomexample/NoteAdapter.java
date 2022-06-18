package org.williamsonministry.roomexample;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    private List<Note> notes = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener listener;

    public NoteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteFromPosition(int position)   {
        return notes.get(position);
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.txtTitle.setText(notes.get(position).getTitle());
        holder.txtDescription.setText(notes.get(position).getDescription());
        holder.txtPriority.setText(String.valueOf(notes.get(position).getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder    {
        private TextView txtTitle, txtDescription, txtPriority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtPriority = itemView.findViewById(R.id.txtPriority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION /*This NO_POSITION may occur if you click a note while it's being deleted*/) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    //This is a good interface lesson here. Getting a callback to mainactivity
    public interface OnItemClickListener    {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener)    {
        this.listener = listener;
    }
}
