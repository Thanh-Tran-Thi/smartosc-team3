package com.smartosc.training.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 10:56 AM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(name = "ROLE_UK", columnNames = "role_name") })
public class Role extends BaseAudit {
    @Column(name = "role_name", length = 45, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
