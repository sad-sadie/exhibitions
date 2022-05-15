package com.my.model.entities;

import com.my.model.services.ExhibitionService;
import com.my.model.services.HallService;

import java.io.Serializable;

public class Hall implements Serializable {
    private long id;
    private String name;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static Hall.Builder builder() {
        return new Hall().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder id(Long id) {
            Hall.this.id = id;
            return this;
        }

        public Builder name(String name) {
            Hall.this.name = name;
            return this;
        }

        public Builder description(String description) {
            Hall.this.description = description;
            return this;
        }

        public Hall build() {
            return Hall.this;
        }
    }

}
