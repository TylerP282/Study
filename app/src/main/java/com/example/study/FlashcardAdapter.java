package com.example.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>{
    private List<Flashcard> flashcards;

    public FlashcardAdapter(List<Flashcard> flashcards){
        this.flashcards = flashcards;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout,parent,false);
        return new FlashcardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder,int position){
        Flashcard flashcard = flashcards.get(position);
        holder.textViewFront.setText(flashcard.getFront());
        holder.textViewBack.setText(flashcard.getBack());

        holder.btnFlip.setOnClickListener(view ->{
            holder.textViewFront.setVisibility(View.GONE);
            holder.textViewBack.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount(){
        return flashcards.size();
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewFront;
        public TextView textViewBack;
        public Button btnFlip;

        public FlashcardViewHolder(@NonNull View itemview){
            super(itemview);
            textViewFront = itemView.findViewById(R.id.frontTextView);
            textViewBack = itemView.findViewById(R.id.backTextView);
            btnFlip = itemView.findViewById(R.id.btnFlipCard);
        }
    }
}

