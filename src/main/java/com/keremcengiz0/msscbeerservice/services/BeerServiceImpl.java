package com.keremcengiz0.msscbeerservice.services;

import com.keremcengiz0.msscbeerservice.domain.Beer;
import com.keremcengiz0.msscbeerservice.repositories.BeerRepository;
import com.keremcengiz0.msscbeerservice.web.controller.NotFoundException;
import com.keremcengiz0.msscbeerservice.web.mappers.BeerMapper;
import com.keremcengiz0.brewery.model.BeerDto;
import com.keremcengiz0.brewery.model.BeerPagedList;
import com.keremcengiz0.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        System.out.println("I was called.");

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!ObjectUtils.isEmpty(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!ObjectUtils.isEmpty(beerName) && ObjectUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (ObjectUtils.isEmpty(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }
        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {
        Beer beer = this.beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        BeerDto beerDto;
        if (showInventoryOnHand) {
            beerDto = this.beerMapper.beerToBeerDtoWithInventory(beer);
        } else {
            beerDto = this.beerMapper.beerToBeerDto(beer);
        }
        return beerDto;
    }

    @Cacheable(cacheNames = "beerUpcCache", key = "#upc")
    @Override
    public BeerDto getBeerByUpc(String upc) {
        Beer beer = this.beerRepository.findByUpc(upc);

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
