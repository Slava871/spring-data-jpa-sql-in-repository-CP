package org.client.repo;

import org.client.entity.Individual;
import org.client.entity.RFPassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualRepo extends JpaRepository<Individual, String>{
    Optional<Individual> findByIcp(String icp);

    // без аннотаций @Transactional  и @Modifying не будут вноситься изменения в БД!
    // Без них можно только читать из БД
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // СОЗДАНИЕ НОВОГО ПОЛЬЗОВАТЕЛЯ
            value = "insert into public.individual values " +
                    "(:uuid, :birthDate, :countryOfBirth, :fullName, :gender, :icp, :name, :patronymic, " +
                    ":placeOfBirth, :surname, :contactsUuid, :documentsuuid, :rfPassportUuid)",
            nativeQuery = true)//
    void createUser(@Param("uuid") String uuid, @Param("birthDate") Date birthDate, @Param("countryOfBirth") String countryOfBirth,
                    @Param("fullName") String fullName, @Param("gender") String gender, @Param("icp") String icp, @Param("name") String name,
                    @Param("patronymic") String patronymic, @Param("placeOfBirth") String placeOfBirth, @Param("surname") String surname,
                    @Param("contactsUuid") String contactsUuid, @Param("documentsuuid") String documentsuuid, @Param("rfPassportUuid") UUID rfPassportUuid);

    @Query( //тестовый запрос
            value = "select surname, name, :param1, country_of_birth from public.individual" +
                    " where uuid = :param2",
            nativeQuery = true) //
    List<String> selectFromTable(@Param("param1") String icp, @Param("param2") String uuid);//17659396-f43a-444b-a94d-570f2ef5a166
    // динамическим параметром (по кр. мере в именованном запросе) можно заменить часть запроса, которой соответсвует переменная сущности entity в коде

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( //тестовый запрос
            value = "insert into public.individual values " +
                    "('30659359-f43a-444b-a94d-570f2ef5a166', :date, 'россия'," +
                    " 'qwer', 'meil', '2345', 'toster', 'tostorovich', 'pskov', " +
                    "'pletov', '6a659378-f43a-444b-a94d-570f2ef5a166', '6a659315-f43a-444b-a94d-570f2ef5a166'," +
                    ":rfPassp)",
            nativeQuery = true)
    void testMethod(@Param("date") Date date, @Param("rfPassp") UUID rfPassp);

    @Query("from Individual as indiv join fetch indiv.contacts as cont join fetch cont.phoneNumbers as phnum  where phnum.value = :number")
    Individual findByPhNum(@Param("number") String number);

}
