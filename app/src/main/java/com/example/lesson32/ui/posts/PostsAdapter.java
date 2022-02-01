package com.example.lesson32.ui.posts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson32.ItemOnClick;
import com.example.lesson32.data.models.Post;
import com.example.lesson32.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder>{

    private final HashMap<Integer, String> name = new HashMap<>();
    private ItemOnClick itemOnClick;
    private List<Post> posts = new ArrayList<>();

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        name.put(1,"Аделя");
        name.put(2,"Адема");
        name.put(3,"Аделя");
        name.put(4,"Ринат");
        name.put(5,"Алым");
        name.put(6,"Алиаскар");
        name.put(7,"Баястан");
        name.put(8,"Рустам");
        name.put(9,"Алишер");
        name.put(10,"Алымбек");
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public Post getPost(int position) {
        return posts.get(position);
    }

    public void removeItem(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(view -> itemOnClick.onClick(getAdapterPosition()));
            itemView.setOnLongClickListener(view -> {
                itemOnClick.onLongClick(getAdapterPosition());
                return true;
            });
        }
        public void onBind(Post post) {
            binding.tvUserId.setText(name.get(post.getUserId()));
            binding.tvContent.setText(post.getContent());
            binding.tvTitle.setText(post.getTitle());
            binding.tvGroup.setText(Integer.toString(post.getGroupId()));
        }
    }
}
