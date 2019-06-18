package com.stucom.grupo4.typhone.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.tools.DatabaseHelper;
import com.stucom.grupo4.typhone.tools.Tools;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> scores = new ArrayList<>();
    private DatabaseHelper mDatabaseHelper;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        mDatabaseHelper = new DatabaseHelper(this);

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

    private void fillRanking(){

        /*Cursor data = mDatabaseHelper.getData();
        if(data != null){
            while(data.moveToNext()){
                scores.add(data.getString(1));
            }
        }*/

        try {
/*            SQLiteDatabase sdb = mDatabaseHelper.getReadableDatabase();
            Cursor data = sdb.rawQuery("SELECT score FROM scoreboard ORDER BY CAST (score AS INTEGER);", null);
*/
            Cursor data = mDatabaseHelper.getData();
            if(data.moveToFirst()){
                do{
                    scores.add(data.getString(data.getColumnIndex("score")));
                }while(data.moveToNext());
            }
          //  sdb.close();

        }catch(SQLiteException e){
            Log.e(getClass().getSimpleName(), "Could not open database");
        }finally{
            if(mDatabaseHelper != null){
                mDatabaseHelper.close();
            }
        }
        ScoreAdapter adapter = new ScoreAdapter(scores);
        recyclerView.setAdapter(adapter);
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        final TextView rank;
        final TextView score;

        ScoreViewHolder(View itemView){
            super(itemView);
            rank = itemView.findViewById(R.id.lblRank);
            score = itemView.findViewById(R.id.lblScoreStat);
        }
    }

    class ScoreAdapter extends RecyclerView.Adapter<ScoreViewHolder>{
        private ArrayList<String> score;
        ScoreAdapter(ArrayList<String> score){
            super();
            this.score = score;
        }

        @NonNull
        @Override public ScoreViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int position){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
            return new ScoreViewHolder(view);
        }

        @Override public void onBindViewHolder(@NonNull final ScoreViewHolder viewHolder, final int position){
            // Set User values in ranking layout
            viewHolder.rank.setText(String.valueOf(position + 1));

            viewHolder.score.setText(scores.get(position) + "  points");
        }

        @Override public int getItemCount() { return score.size(); }
    }



    /*
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
            score = itemView.findViewById(R.id.lblScoreStat);
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
    }*/
}