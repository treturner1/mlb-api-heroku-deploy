package com.mlb.mlb_api.controllers;


import com.mlb.mlb_api.entities.Player;
import com.mlb.mlb_api.repositories.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {


    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping("/add")
    public Player createPlayer(@RequestBody Player newPlayer){
        return playerRepository.save(newPlayer);
    }

    @GetMapping
    public Iterable<Player> getPlayer(){
        return playerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable("id") Integer playerId){
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if(optionalPlayer.isEmpty()){
            return null;
        }
        Player player = optionalPlayer.get();
        return player;
    }

    @PutMapping
    public Player updatePlayer(@RequestBody Player incomingPlayer){
//        find the player to update
        Player playerFromDb = getPlayerById(incomingPlayer.getId());

//        update the players information
        if(incomingPlayer.getName() == null){
            playerFromDb.setName(playerFromDb.getName());
        } else if(incomingPlayer.getName().isEmpty()) {
            playerFromDb.setName(playerFromDb.getName());
        } else {
            playerFromDb.setName(incomingPlayer.getName());
        }

        playerFromDb.setAge(incomingPlayer.getAge() != null && incomingPlayer.getAge() > 18 ? incomingPlayer.getAge() : playerFromDb.getAge());
        playerFromDb.setRating(incomingPlayer.getRating() != null ? incomingPlayer.getRating() : playerFromDb.getRating());
        playerFromDb.setYearsOfExperience(incomingPlayer.getYearsOfExperience() != null ? incomingPlayer.getYearsOfExperience() : playerFromDb.getYearsOfExperience());
//        save the player back to the DB
//        return the player to the client
        return playerRepository.save(playerFromDb);
    }

}
