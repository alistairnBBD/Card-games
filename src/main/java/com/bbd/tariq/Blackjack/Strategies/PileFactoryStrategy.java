package com.bbd.tariq.Blackjack.Strategies;

import com.bbd.tariq.Blackjack.Common.Constants;
import com.bbd.tariq.Blackjack.Interfaces.IPileFactory;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.Rummy.RummyPilesResponseModel;
import com.bbd.tariq.Blackjack.Models.GoFishGame.GoFishPilesResponseModel;
import com.bbd.tariq.Blackjack.Models.CardsApiModels.Piles.PilesBaseResponseModel;
import com.bbd.tariq.Blackjack.Models.SolitaireGame.SolitairePilesResponseModel;

public class PileFactoryStrategy implements IPileFactory {
    @Override
    public PilesBaseResponseModel getPile(String pileType) {

        switch(pileType) {

            case Constants.PileTypes.SOLITAIRE:
                var solitaire = new SolitairePilesResponseModel();
                solitaire.type = Constants.PileTypes.SOLITAIRE;
                return solitaire;

            case Constants.PileTypes.GO_FISH:
                var goFish = new GoFishPilesResponseModel();
                goFish.type = Constants.PileTypes.GO_FISH;
                return goFish;

            case Constants.PileTypes.RUMMY:
                var rummy = new RummyPilesResponseModel();
                rummy.type = Constants.PileTypes.GO_FISH;
                return rummy;

            default:
                return null;
        }
    }
}
