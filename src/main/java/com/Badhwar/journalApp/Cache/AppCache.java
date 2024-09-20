package com.Badhwar.journalApp.Cache;

import com.Badhwar.journalApp.entity.ConfigJournalAppEntity;
import com.Badhwar.journalApp.repository.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum Keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    public Map<String, String> appCache;

    //Post Construct will run once the Bean is set up, it will just run this method and P.C, only is given to Methods
    @PostConstruct
    public void init()
    {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity: all)
        {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
