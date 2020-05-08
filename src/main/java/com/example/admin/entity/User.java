package com.example.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@ApiModel(description = "用户实体")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
@Entity
@Data
@EqualsAndHashCode(exclude = {"authorities"}, callSuper = true)
public class User extends BaseEntity implements UserDetails, Serializable {
    public enum Sex {
        MALE(0, "男"),
        FEMALE(1, "女");

        Sex(int index, String name) {
        }
    }

    @ApiModelProperty("用户名")
    @Column(unique = true)
    @NotBlank
    @Size(min = 2, max = 10)
    private String username;

    @NotNull
    private String password;

    private String nickname;

    @Enumerated
    private Sex sex;

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
