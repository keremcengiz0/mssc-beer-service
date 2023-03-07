package com.keremcengiz0.msscbeerservice.web.controller;

import com.keremcengiz0.msscbeerservice.services.BeerService;
import com.keremcengiz0.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable(name = "beerId") UUID beerId) {
        return new ResponseEntity<>(this.beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(this.beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable(name = "beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(this.beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}
