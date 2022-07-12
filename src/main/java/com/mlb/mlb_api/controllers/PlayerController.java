package com.mlb.mlb_api.controllers;


import com.mlb.mlb_api.controllers.dto.PlayerDTO;
import com.mlb.mlb_api.repositories.entities.Player;
import com.mlb.mlb_api.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {


    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/add")
    public Player createPlayer(@RequestBody PlayerDTO newPlayerDTO){
        return playerService.save(newPlayerDTO);
    }

    @GetMapping
    public Iterable<Player> getPlayer(){
        return playerService.findAll();
    }

    @GetMapping("/find")
    public Player findPlayerByName(@RequestParam String name){
        return playerService.findByName(name);
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable("id") Integer playerId){
       return playerService.findById(playerId);
    }

    @PutMapping("/{id}")
    public Player updatePlayer(@RequestBody PlayerDTO playerDTO, @PathVariable("id") Integer playerId){
        return playerService.update(playerDTO, playerId);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable("id") Integer playerId){
       playerService.delete(playerId);
    }

}










