package com.smartosc.training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 11:29 AM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("createdDate")
    private Date createdDate;
    @JsonProperty("modifiedDate")
    private Date modifiedDate;
    @JsonProperty("status")
    private int status;
}
