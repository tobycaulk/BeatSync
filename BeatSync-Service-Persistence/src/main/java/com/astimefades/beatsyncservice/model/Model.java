package com.astimefades.beatsyncservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Model {

    private ObjectId id;
    private Details details = new Details();

    @Id
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public class Details {
        private Date createDate;
        private Date modifiedDate;

        public void setDatesToNow() {
            if(createDate == null) {
                createDate = new Date();
            }
            modifiedDate = new Date();
        }

        @JsonProperty("createDate")
        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        @JsonProperty("modifiedDate")
        public Date getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(Date modifiedDate) {
            this.modifiedDate = modifiedDate;
        }
    }
}