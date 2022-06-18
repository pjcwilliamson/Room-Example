package org.williamsonministry.roomexample;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//So in using ListAdapter, there's a bunch of changes in here which are explained in video 10. This is to use DiffUtil which compares two lists to figure out if something's been added or taken away or updated so we can use the appropriate notify
//The arraylist of Notes is not explicitly stated, but managed by ListAdapter
public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder>{
    private Context mContext;
    private OnItemClickListener listener;

    public NoteAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    //This compares Notes so it can see what's changed in the list. Runs in background due to ListAdapter properties
    private static /*Needs to be static cos we're accessing it in the constructor before the instance is created*/final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    };

    public Note getNoteFromPosition(int position)   {
        return getItem(position);
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
        holder.txtTitle.setText(getItem(position).getTitle());
        holder.txtDescription.setText(getItem(position).getDescription());
        holder.txtPriority.setText(String.valueOf(getItem(position).getPriority()));
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
                        listener.onItemClick(getItem(position));
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
