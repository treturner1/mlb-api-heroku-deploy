package com.mlb.mlb_api.service;

import com.mlb.mlb_api.controllers.dto.PlayerDTO;
import com.mlb.mlb_api.repositories.PlayerRepository;
import com.mlb.mlb_api.repositories.entities.Player;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService{

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player save(PlayerDTO playerDTO) {
//        converting a PlayerDTO to a Player entity
        Player player = new Player(playerDTO);
        return playerRepository.save(player);
    }

    @Override
    public Player update(PlayerDTO playerDTO, Integer playerId) {
        //        find the player to update
        Player playerFromDb = findById(playerId);

//        update the players information
        if(playerDTO.getName() == null){
            playerFromDb.setName(playerFromDb.getName());
        } else if(playerDTO.getName().isEmpty()) {
            playerFromDb.setName(playerFromDb.getName());
        } else {
            playerFromDb.setName(playerDTO.getName());
        }

        playerFromDb.setAge(playerDTO.getAge() != null ? playerDTO.getAge() : playerFromDb.getAge());
        playerFromDb.setRating(playerDTO.getRating() != null ? playerDTO.getRating() : playerFromDb.getRating());
        playerFromDb.setYearsOfExperience(playerDTO.getYearsOfExperience() != null ? playerDTO.getYearsOfExperience() : playerFromDb.getYearsOfExperience());
//        save the player back to the DB
//        return the player to the client
        return playerRepository.save(playerFromDb);
    }

    @Override
    public void delete(Integer playerId) {
        Optional<Player> playerToDelete = playerRepository.findById(playerId);

        if(playerToDelete.isPresent()){
            playerRepository.deleteById(playerId);
            throw new ResponseStatusException(HttpStatus.OK, "The player has been successfully deleted");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The player was not found.");
        }
    }

    @Override
    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(Integer playerId) {
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if(optionalPlayer.isEmpty()){
            return null;
        }
        return optionalPlayer.get();
    }

    @Override
    public Player findByName(String name){
        return playerRepository.findByName(name);
    }
}
