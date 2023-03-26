package com.keremcengiz0.msscbeerservice.events;

import com.keremcengiz0.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    static final long serialVersionUID = -5781516597148163111L;
    private final BeerDto beerDto;
}
