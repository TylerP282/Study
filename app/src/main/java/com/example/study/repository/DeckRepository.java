package com.example.study.repository;

import com.example.study.Deck;
import com.example.study.db.DeckDAO;

import java.util.List;

public class DeckRepository {
    private final DeckDAO deckDAO;

    public DeckRepository(DeckDAO deckDAO){
        this.deckDAO = deckDAO;
    }

    public long insertDeck(Deck deck){
        return deckDAO.insertDeck(deck);
    }

    public List<Deck> getAllDecks(){
        return deckDAO.getAllDecks();
    }
}
