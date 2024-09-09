package com.vidura.Wired2perform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "user_entity", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class UserEntity  implements UserDetails {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "password is required")
    private String password;

    @Email
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotNull(message = "Age is required")
    @Min(value = 0,message = "Incorrect age")
    @Max(value = 100, message = "Incorrect age")
    private Integer age;

    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TodoList> todoLists;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
