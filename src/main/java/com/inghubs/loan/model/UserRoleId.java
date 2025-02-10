package com.inghubs.loan.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "roles_id")
    private Long rolesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleId that = (UserRoleId) o;

        if (!usersId.equals(that.usersId)) return false;
        return rolesId.equals(that.rolesId);
    }

    @Override
    public int hashCode() {
        int result = usersId.hashCode();
        result = 31 * result + rolesId.hashCode();
        return result;
    }

}
