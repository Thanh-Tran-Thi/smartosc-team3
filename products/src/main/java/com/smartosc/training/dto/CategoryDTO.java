package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 8:52 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;

    @NotEmpty(message = "Please, enter a name")
    private String name;

    @NotEmpty(message = "Please, enter a description")
    private String description;
}
