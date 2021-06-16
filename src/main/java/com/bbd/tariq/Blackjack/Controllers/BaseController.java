package com.bbd.tariq.Blackjack.Controllers;

import com.bbd.tariq.Blackjack.Interfaces.ICardsApi;
import com.bbd.tariq.Blackjack.Interfaces.IRepoFactory;
import com.bbd.tariq.Blackjack.Repos.Repo;
import org.modelmapper.ModelMapper;

public class BaseController {

    protected final ICardsApi _cardsApi;
    protected final ModelMapper _mapper;
    protected final IRepoFactory _repoFactory;
    protected final Repo _repo;

    public BaseController(ICardsApi cardsApi, ModelMapper mapper, IRepoFactory repoFactory, String repoName) {

        _cardsApi = cardsApi;
        _mapper = mapper;
        _repoFactory = repoFactory;
        _repo = repoFactory.getRepo(repoName);
    }
}
