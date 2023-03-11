package com.keremcengiz0.msscbeerservice.services;

import com.keremcengiz0.msscbeerservice.web.model.BeerDto;
import com.keremcengiz0.msscbeerservice.web.model.BeerPagedList;
import com.keremcengiz0.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, Boolean showInventoryOnHand);
}
