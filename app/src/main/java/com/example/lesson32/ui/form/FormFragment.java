package com.example.lesson32.ui.form;

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

import com.example.lesson32.R;
import com.example.lesson32.data.models.Post;
import com.example.lesson32.databinding.FragmentFormBinding;
import com.example.lesson32.utils.App;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    public static final int userId = 3;
    public static final int groupId = 5;
    private NavController controller;
    private Post post1;

    public FormFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(
                inflater,
                container,
                false
        );
        controller = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            post1 = (Post) getArguments().getSerializable("ttt");
            binding.edTitle.setText(post1.getTitle());
            binding.edContent.setText(post1.getContent());
        }
        binding.btnSend.setOnClickListener(view1 -> {
            if (post1 == null){
                String title = binding.edTitle.getText().toString();
                String content = binding.edContent.getText().toString();
                Post post = new Post(title, content, userId, groupId);

                App.api.createPost(post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                        if (response.isSuccessful()){
                            NavController navController =Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                            navController.popBackStack();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                    }
                });
            }else {
                String title = binding.edTitle.getText().toString();
                String content = binding.edContent.getText().toString();
                Post post = new Post(title, content, userId, groupId);

                App.api.update(post1.getId(), post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                        Toast.makeText(requireActivity(), "ОБНОВЛЕНО", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                        navController.navigate(R.id.postsFragment);

                    }

                    @Override
                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                    }
                });
            }
        });
        setupListeners();
    }

    private void setupListeners() {
        binding.btnSend.setOnClickListener(view -> createPost());
    }

    private void createPost() {
        String title = binding.edTitle.getText().toString();
        String content = binding.edContent.getText().toString();
        Post post = new Post(
                title,
                content,
                userId,
                groupId
        );
        App.api.createPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful() && response.body() != null){
                    controller.popBackStack();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

            }
        });
    }
        }