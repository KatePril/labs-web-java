package org.kpi.lab1.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.service.CosmoCatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CosmoCatServiceImplementation implements CosmoCatService {

    @Override
    public List<String> getCosmoCats() {
        return List.of("Star cat", "Galaxy cat", "Comet cat");
    }
}
