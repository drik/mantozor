package com.dirks.cool.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Referent entity.
 */
public class ReferentDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;
    
    private Long userId;
    
    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReferentDTO referentDTO = (ReferentDTO) o;
        if(referentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
