package com.stucom.grupo4.typhone.activities;

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

import java.util.ArrayList;

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

        try {
            SQLiteDatabase sdb = mDatabaseHelper.getReadableDatabase();
            Cursor data = sdb.rawQuery("SELECT score FROM scoreboard ORDER BY CAST (score AS INTEGER) DESC;", null);

            if(data.moveToFirst()){
                do{
                    scores.add(data.getString(data.getColumnIndex("score")));
                }while(data.moveToNext());
            }

            data.close();
            sdb.close();

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
    class ScoreAdapter extends RecyclerView.Adapter<ScoreViewHolder> {
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
}