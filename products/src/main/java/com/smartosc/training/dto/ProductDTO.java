package com.smartosc.training.dto;

import com.smartosc.training.validator.ImageConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 8:47 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    @NotEmpty(message = "Please, enter a name")
    @Size(min=2, max=30, message="Name should have at least 2 and less than 30 characters")
    private String name;

    @NotEmpty(message = "Please, enter a description")
    private String description;

    @ImageConstraint
    private String image;

    @NotNull(message = "Please, enter price for product")
    @DecimalMin("1.00")
    private BigDecimal price;

    private List<CategoryDTO> categories;

    @Override
    public String toString() {
        return "ProductDTO{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
