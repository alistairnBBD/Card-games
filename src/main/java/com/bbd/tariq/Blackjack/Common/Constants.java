package com.bbd.tariq.Blackjack.Common;

import java.util.HashMap;

public class Constants {


    public static class PileTypes {

        public static final String SOLITAIRE = "Solitaire";
        public static final String GO_FISH = "GoFish";
        public static final String RUMMY = "Rummy";
        public static final String BLACKJACK = "Blackjack";
        public static final String HILO = "Hilo";
    }

    public static class SolitairePileNames {

        public static final String DIAMONDS_PILE_NAME = "Diamonds";
        public static final String HEARTS_PILE_NAME = "Hearts";
        public static final String SPADES_PILE_NAME = "Spades";
        public static final String CLUBS_PILE_NAME = "Clubs";

    }

    public static class GoFishPileNames {

        public static final String PLAYER_1_PILE_NAME = "Player1Pile";
    }

    public static class GameStates {
        public static final String COMPLETE = "Complete";
        public static final String RUNNING = "Running";
        public static final String INCOMPLETE = "Incomplete"; //Game abandoned?
    }

    public static class Blackjack {

        public final static HashMap<String,Integer> CARD_VALUE_MAP = new HashMap<String,Integer>()
        {
            {
                put("ACE",11);
                put("JACK_KING_QUEEN",10);
                put("2",2);
                put("3",3);
                put("4",4);
                put("5",5);
                put("6",6);
                put("7",7);
                put("8",8);
                put("9",9);
                put("10",10);
            }
        };

        public static class BlackJackCardValues {

            public static final int ACE = 11;
            public static final int JACK_QUEEN_KING = 10;
            public static final int _2 = 2;
            public static final int _3 = 3;
            public static final int _4 = 4;
            public static final int _5 = 5;
            public static final int _6 = 6;
            public static final int _7 = 7;
            public static final int _8 = 8;
            public static final int _9 = 9;
            public static final int _10 = 10;

        }

        public static class BlackJackActions {

            public static final String HIT = "Hit";
            public static final String STAND = "Stand";
            public static final String SPLIT = "Split";
        }

        public static final int MAX_PLAYERS = 4;

        public static final String REPO_NAME = "Blackjack";
    }

    public static class GoFish {
        
        public static class GoFishActions {
            public static final String HIT = "Ask";
    }
        public static final int MAX_PLAYERS = 5;
        public static final int MIN_PLAYERS = 2;
        public static final String REPO_NAME = "GoFish";

}
    public static class Hilo {
        public static final String REPO_NAME = "Hilo";

        public final static HashMap<String,Integer> CARD_VALUE_MAP = new HashMap<String,Integer>()
        {
            {
                put("ACE",1);
                put("2",2);
                put("3",3);
                put("4",4);
                put("5",5);
                put("6",6);
                put("7",7);
                put("8",8);
                put("9",9);
                put("10",10);
                put("JACK",11);
                put("QUEEN",12);
                put("KING",13);
            }
        };

        public static class Guesses {
            public static final String HIGH = "high";
            public static final String LOW = "low";
            public static final String SAME = "same";
        }

        public static class CardValues {
            public static final int ACE = 1;
            public static final int _2 = 2;
            public static final int _3 = 3;
            public static final int _4 = 4;
            public static final int _5 = 5;
            public static final int _6 = 6;
            public static final int _7 = 7;
            public static final int _8 = 8;
            public static final int _9 = 9;
            public static final int _10 = 10;
            public static final int JACK = 11;
            public static final int QUEEN = 12;
            public static final int KING = 13;
        }
    }
}
