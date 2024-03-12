package com.service.todoit.domain.user;


import com.service.todoit.domain.BaseEntity;
import com.service.todoit.domain.user.enums.Role;
import com.service.todoit.domain.user.enums.Social;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String email;

    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @Column(length = 100)
    private String avatar;

    @Setter
    private LocalDateTime loginAt;

    @Setter
    private LocalDateTime withdrawnAt;

    @Enumerated(EnumType.STRING)
    private Social social;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;



    public String getRoleKey() {
        return this.role.getKey();
    }

}