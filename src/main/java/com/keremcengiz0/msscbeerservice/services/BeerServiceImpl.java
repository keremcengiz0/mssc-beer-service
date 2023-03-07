package com.keremcengiz0.msscbeerservice.services;

import com.keremcengiz0.msscbeerservice.domain.Beer;
import com.keremcengiz0.msscbeerservice.repositories.BeerRepository;
import com.keremcengiz0.msscbeerservice.web.controller.NotFoundException;
import com.keremcengiz0.msscbeerservice.web.mappers.BeerMapper;
import com.keremcengiz0.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public BeerDto getBeerById(UUID beerId) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        BeerDto beerDto = this.beerMapper.beerToBeerDto(beer);
        return beerDto;
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer beer = this.beerMapper.beerDtoToBeer(beerDto);
        this.beerRepository.save(beer);
        return this.beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = this.beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        BeerDto toUpdateBeer = this.beerMapper.beerToBeerDto(beer);
        this.beerRepository.save(beer);

        return toUpdateBeer;
    }
}
