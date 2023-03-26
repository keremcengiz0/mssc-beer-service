package com.keremcengiz0.msscbeerservice.events;

import com.keremcengiz0.msscbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
