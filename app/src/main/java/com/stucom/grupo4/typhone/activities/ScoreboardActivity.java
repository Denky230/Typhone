package com.stucom.grupo4.typhone.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.model.User;

import java.util.Arrays;
import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        initRecyclerView();
        fillRanking();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    /* TEST */
    private void fillRanking() {
        List<User> ranking = Arrays.asList(
                new User("Jose", ""),
                new User("Alejandro", ""),
                new User("Manolo", ""),
                new User("Carlos", ""),
                new User("Juan", ""),
                new User("Peter", ""),
                new User("Almondiga", ""),
                new User("Alejandro", ""),
                new User("Manolo", ""),
                new User("Carlos", ""),
                new User("Juan", ""),
                new User("Peter", ""),
                new User("Almondiga", ""),
                new User("Jose", ""),
                new User("Alejandro", ""),
                new User("Manolo", ""),
                new User("Carlos", ""),
                new User("Juan", ""),
                new User("Peter", ""),
                new User("Almondiga", ""),
                new User("Jose", ""),
                new User("Alejandro", ""),
                new User("Manolo", ""),
                new User("Carlos", ""),
                new User("Juan", ""),
                new User("Peter", ""),
                new User("Almondiga", ""),
                new User("Jose", ""),
                new User("Alejandro", ""),
                new User("Manolo", "")
        );

        // Set UsersAdapter with Users ranking to recyclerView
        UsersAdapter adapter = new UsersAdapter(ranking);
        recyclerView.setAdapter(adapter);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        final TextView rank;
        final TextView name;
        final TextView score;

        UserViewHolder(View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.lblRank);
            score = itemView.findViewById(R.id.lblScore);
            name = itemView.findViewById(R.id.lblUsername);
        }
    }
    class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {

        private final List<User> users;

        UsersAdapter(List<User> users) {
            super();
            this.users = users;
        }

        @NonNull
        @Override public UserViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int position) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.ranking_item, parent, false);
            return new UserViewHolder(view);
        }
        @Override public void onBindViewHolder(@NonNull final UserViewHolder viewHolder, final int position) {
            User user = users.get(position);

            // Set User values in ranking layout
            viewHolder.rank.setText(String.valueOf(position + 1));
            viewHolder.name.setText(user.getName());
            int offset = 1548 * position;
            viewHolder.score.setText(String.valueOf(184510 - offset));
//            Picasso.get().load(user.getImage()).into(viewHolder.imageView);

            // Define OnClick() for whole ranking layout
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int listPos = viewHolder.getAdapterPosition();
//                    User user = users.get(listPos);
//
//                    // Show User details activity
//                    Intent intent = new Intent(RankingActivity.this, UserDetailsActivity.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
//                }
//            });
        }
        @Override public int getItemCount() { return users.size(); }
    }
}