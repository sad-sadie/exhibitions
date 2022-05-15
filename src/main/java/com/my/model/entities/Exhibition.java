package com.my.model.entities;

import com.my.model.services.ExhibitionService;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Exhibition implements Serializable {
    private long id;
    private String theme;
    private String description;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Hall> getHalls() {
        return new ExhibitionService().getHalls(id);
    }

    public static  Exhibition.Builder builder() {
        return new Exhibition().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Exhibition.Builder id(Long id) {
            Exhibition.this.id = id;
            return this;
        }

        public Exhibition.Builder theme(String theme) {
            Exhibition.this.theme = theme;
            return this;
        }

        public Exhibition.Builder description(String description) {
            Exhibition.this.description = description;
            return this;
        }

        public Exhibition.Builder startDate(Date startDate) {
            Exhibition.this.startDate = startDate;
            return this;
        }

        public Exhibition.Builder endDate(Date startDate) {
            Exhibition.this.endDate = startDate;
            return this;
        }

        public Exhibition.Builder startTime(Time startTime) {
            Exhibition.this.startTime = startTime;
            return this;
        }

        public Exhibition.Builder endTime(Time endTime) {
            Exhibition.this.endTime = endTime;
            return this;
        }

        public Exhibition.Builder price(double price) {
            Exhibition.this.price = price;
            return this;
        }

        public Exhibition build() {
            return Exhibition.this;
        }
    }

}
