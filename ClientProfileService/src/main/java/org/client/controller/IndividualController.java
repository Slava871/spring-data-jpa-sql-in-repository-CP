package org.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.client.dto.IndividualDto;
import org.client.entity.Individual;
import org.client.service.IndividualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/individual")
@Tag(name = "Individual controller", description = "Методы для работы с пользователем")
public class IndividualController {

    private final IndividualService individualService;

    public IndividualController(IndividualService individualService) {
        this.individualService = individualService;
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<Individual> getAll() {
        List<Individual> list = individualService.getAll();
        System.out.println(list.get(0).getIcp() + " user1 (icp) @@@");
        System.out.println(list.get(1).getIcp() + " user2 (icp) @@@");
        System.out.println(list.get(2).getIcp() + " user3 (icp) @@@");
        return null; //не понимаю, как передать лист пользователей в json, просто вывел их icp в консоль
    }

    @GetMapping("/get/{icp}")
    @Operation(summary = "Информация о клиенте по ICP")
    public ResponseEntity<IndividualDto> getByIcp(@Parameter(description = "ICP уникальный ключ клиента") String ICP,
                                                  @PathVariable(value="icp") String icp) {
        return new ResponseEntity<>(individualService.getClient(icp), HttpStatus.OK);
    }

    @PostMapping("/create")
    public void createIndividual(@RequestBody IndividualDto dto) {
        individualService.addClient(dto.getIcp(),  dto.getContactsUuid(),
                dto.getDocumentsUuid(), dto.getRfPassportUuid(), dto.getBirthDate(), dto.getCountryOfBirth(),
                dto.getFullName(), dto.getGender(), dto.getName(), dto.getPatronymic(),
                dto.getPlaceOfBirth(), dto.getSurname());
    }

    @GetMapping("/get2/{value}")
    @Operation(summary = "Информация о клиенте по номеру телефона")
    public ResponseEntity<IndividualDto> getByPhonenumber(@Parameter(description = "телефон клиента") String Value,
                                                  @PathVariable(value="value") String value) {
        return new ResponseEntity<>(individualService.getClientByPhoneNum(value), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public void editIndividual(@RequestBody IndividualDto dto) {
        individualService.editClient(dto.getIcp(), dto.getBirthDate(), dto.getCountryOfBirth(),
                dto.getFullName(), dto.getGender(), dto.getName(), dto.getPatronymic(),
                dto.getPlaceOfBirth(), dto.getSurname());
    }

    @PostMapping("/delete/{icp}")  // post запрос с пустым телом
    public void editIndividual(@PathVariable(value="icp") String icp) {
        individualService.deleteIndivid(icp);
    }


}
