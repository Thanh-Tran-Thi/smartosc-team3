package com.smartosc.training.dtos;

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
public class AbstractDTO {
    private Long id;
    private Date createdDate;
    private Date modifiedDate;
    private int status;
}
