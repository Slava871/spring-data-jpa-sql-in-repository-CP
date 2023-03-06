package org.client.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.intellij.lang.annotations.Pattern;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Date;

@Schema(description = "тестовое дто")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class testDto {
    @JsonProperty()
    Long id;

    String name;



}
