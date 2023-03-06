package org.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.client.entity.ContactMedium;
import org.intellij.lang.annotations.Pattern;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Schema(description = "Модель, описывающая пользоватяле в ЛК банка")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class IndividualDto {

    public static final String UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
    @Schema(example = "4800c301-50a5-46f9-8c5f-6d6b3fbc55nf", description = "Идентификатор клиента по стандарту RFC4122")
    @Pattern(value = UUID_PATTERN)
    @JsonProperty(Fields.UUID)
    private String uuid;

    @Schema(example = "312", description = "Идентификатор Клиентского профиля")
    @JsonProperty(Fields.ICP)
    private String icp;

    private String name;

    private String surname;

    private String patronymic;

    private String fullName;

    private String gender;

    private String placeOfBirth;

    private String countryOfBirth;

    private Date birthDate;

    private String documentsUuid;

    private UUID rfPassportUuid;

    private String contactsUuid;


    @Hidden
    @JsonProperty(Fields.ADDRESS)
    private Collection<AddressDto> address;

    @Hidden
    @JsonProperty(Fields.WALLET)
    private Collection<WalletDto> wallet;


    public static class Fields {
        public static final String UUID = "uuid";

        public static final String ICP = "icp";

        public static final String ADDRESS = "address";

        public static final String WALLET = "wallet";
    }
}
