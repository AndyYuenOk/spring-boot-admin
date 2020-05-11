package com.example.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@ApiModel(description = "用户实体")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Entity
@Data
@EqualsAndHashCode(of = {"username"}, callSuper = false)
public class User extends BaseEntity implements UserDetails {
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum SexEnum {
        FEMALE(0, "女"),
        MALE(1, "男");

        @Getter
        @Setter
        private Integer index;

        @Getter
        @Setter
        private String name;
    }

    @ApiModelProperty("用户名")
    @Column(unique = true)
    @NotBlank
    @Size(min = 2, max = 10)
    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")
            })
    private Set<Role> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
