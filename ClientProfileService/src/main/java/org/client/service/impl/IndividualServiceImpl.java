package org.client.service.impl;

import lombok.AllArgsConstructor;
import org.client.dto.IndividualDto;
import org.client.entity.ContactMedium;
import org.client.entity.Individual;
import org.client.entity.RFPassport;
import org.client.repo.IndividualRepo;
import org.client.repo.IndividualRepo;
import org.client.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.*;

@Component
@AllArgsConstructor
public class IndividualServiceImpl implements IndividualService {

    @Autowired
    IndividualRepo individualRepo;

    public IndividualServiceImpl() {}

//    // здесь идет пока просто добаление в мап
//    private Map<String, IndividualDto> persist = new HashMap<>();
    // IndividualDto individualDto = IndividualRepo.save(IndividualDto.builder()
    //.icp(icp).uuid(UUID.randomUUID().toString()).name(name).build());

    @Transactional
    @Override
    public void addClient(String icp, String contactsUuid, String documentsUuid, UUID rfPassportUuid,
                          Date birthDate, String countryOfBirth, String fullName, String gender,
                          String name, String patronymic, String placeOfBirth, String surname) {


        individualRepo.createUser(UUID.randomUUID().toString(), birthDate, countryOfBirth, fullName, gender, icp, name, patronymic,
                placeOfBirth, surname, contactsUuid, documentsUuid, rfPassportUuid);
    }

    @Override
    public IndividualDto getClient(String icp) {
        Individual individual = individualRepo.findByIcp(icp).orElse(new Individual());
        IndividualDto individualDto = IndividualDto.builder().icp(individual.getIcp()).uuid(individual.getUuid()).name(individual.getName())
                .fullName(individual.getFullName()).countryOfBirth(individual.getCountryOfBirth()).build();
        return individualDto;
    }

    @Override
    public List<Individual> getAll(){
       return individualRepo.findAll();
    }

    @Override
    public IndividualDto getClientByPhoneNum(String value) {
        Individual individual = individualRepo.findByPhNum(value);
        System.out.println(individual.getIcp() + "@@@");
        IndividualDto individualDto = IndividualDto.builder().icp(individual.getIcp()).name(individual.getName()).build();
        return individualDto;
    }

}
