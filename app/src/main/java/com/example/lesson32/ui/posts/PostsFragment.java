package com.example.lesson32.ui.posts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lesson32.ItemOnClick;
import com.example.lesson32.R;
import com.example.lesson32.data.models.Post;
import com.example.lesson32.databinding.FragmentPostsBinding;
import com.example.lesson32.utils.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment {
    private FragmentPostsBinding binding;
    private PostsAdapter adapter;
    private NavController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostsAdapter();
        adapter.setItemOnClick(new ItemOnClick() {
            @Override
            public void onClick(int position) {
                if (adapter.getPost(position).getUserId() == 3)
                    openFragment(adapter.getPost(position));
                else
                    Toast.makeText(requireActivity(), "вы не можете редактировать чужие записи", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(int position) {
                Post post3 = adapter.getPost(position);
                if (post3.getUserId() == 3) {
                    App.api.deletePosts(post3.getId()).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                            adapter.removeItem(position);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(requireActivity(), "вы не можете удалять чужие записи", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(
                inflater,
                container,
                false
        );
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);

        App.api.getPosts(5).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPosts(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {

            }
        });
        setupListeners();
    }
    private void setupListeners() {
        binding.fab.setOnClickListener(view -> {
            NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            controller.navigate(R.id.formFragment);
        });
    }

    private void openFragment(Post post) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ttt", post);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment, bundle);
    }
}