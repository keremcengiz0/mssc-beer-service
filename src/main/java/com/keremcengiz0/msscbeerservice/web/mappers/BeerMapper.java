package com.keremcengiz0.msscbeerservice.web.mappers;

import com.keremcengiz0.msscbeerservice.domain.Beer;
import com.keremcengiz0.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
}
