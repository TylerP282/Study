package com.example.study;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_DECK = 1;
    private static final int VIEW_TYPE_CARD = 2;
    private OnDeckClickListener onDeckClickListener;
    private List<Object> items;

    public DeckAdapter(List<Object> itemList,OnDeckClickListener onDeckClickListener){
        this.items = itemList;
        this.onDeckClickListener = onDeckClickListener;
    }

    public Object getItem(int position){
        return items.get(position);
    }

    public void setOnDeckClickListener(OnDeckClickListener listener){
        this.onDeckClickListener = listener;
    }

    public void setDecks(List<Deck> decks){
        List<Object> newItems = new ArrayList<>();

        Log.d("DeckAdapter", "Size before update: " + items.size());

        newItems.addAll(decks);
        newItems.addAll(items);

        this.items.clear();
        this.items.addAll(newItems);

        Log.d("DeckAdapter", "Size after update: " + items.size());
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemViewType(int position){
        Object item = items.get(position);
        if(item instanceof Deck){
            return VIEW_TYPE_DECK;
        }else if(item instanceof Flashcard){
            return VIEW_TYPE_CARD;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case VIEW_TYPE_DECK:
                View deckView = inflater.inflate(R.layout.deck_item_layout,parent,false);
                return new DeckViewHolder(deckView);
            case VIEW_TYPE_CARD:
                View cardView = inflater.inflate(R.layout.card_item_layout,parent,false);
                return new CardViewHolder(cardView);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        switch(getItemViewType(position)){
            case VIEW_TYPE_DECK:
                DeckViewHolder deckViewHolder = (DeckViewHolder) holder;
                Deck deck = (Deck) items.get(position);
                deckViewHolder.deckNameTextView.setText(deck.getDeckName());
                break;

            case VIEW_TYPE_CARD:
                CardViewHolder cardViewHolder = (CardViewHolder) holder;
                Flashcard Flashcard = (Flashcard) items.get(position);
                cardViewHolder.frontTextView.setText(Flashcard.getFront());
                cardViewHolder.backTextView.setText(Flashcard.getBack());
                break;

            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public class DeckViewHolder extends RecyclerView.ViewHolder{
        public TextView deckNameTextView;
        private OnDeckClickListener deckClickListener;

        public DeckViewHolder(@NonNull View itemView){
            super(itemView);
            deckNameTextView = itemView.findViewById(R.id.deckNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onDeckClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            Deck clickedDeck = (Deck) items.get(position);
                            onDeckClickListener.onDeckClick(clickedDeck);
                        }

                    }
                }
            });
        }

        public void bind(Deck deck){
            deckNameTextView.setText(deck.getDeckName());
        }
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        //public CardView cardView;
        public TextView frontTextView;
        public TextView backTextView;
        public Button btnFlipCard;
        public Button btnPreviousCard;
        public Button btnNextCard;

        private OnDeckClickListener onDeckClickListener;

        public void setOnDeckClickListener(OnDeckClickListener listener){
            this.onDeckClickListener = listener;
        }

        public CardViewHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onDeckClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            Deck clickedDeck = (Deck) items.get(position);
                            onDeckClickListener.onDeckClick(clickedDeck);
                        }
                    }
                }
            });
            //cardView = itemView.findViewById(R.id.cardView);
            frontTextView = itemView.findViewById(R.id.frontTextView);
            backTextView = itemView.findViewById(R.id.backTextView);
            btnFlipCard = itemView.findViewById(R.id.btnFlipCard);
            btnPreviousCard = itemView.findViewById(R.id.btnPreviousCard);
            btnNextCard = itemView.findViewById(R.id.btnNextCard);

        }
    }
}
