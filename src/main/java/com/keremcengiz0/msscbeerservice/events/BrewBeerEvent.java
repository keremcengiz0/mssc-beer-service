package com.keremcengiz0.msscbeerservice.events;

import com.keremcengiz0.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent{
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
