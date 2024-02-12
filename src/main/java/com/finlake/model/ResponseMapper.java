package com.finlake.model;

import com.finlake.enums.ResponseCode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
public class ResponseMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "response_code", nullable = false)
    private String responseCode;

    @Column(name = "http_status_code", nullable = false)
    private String httpStatusCode;

    @Column(name = "response_constant", nullable = false)
    private String responseConstant;

    @Column(name = "response_message", nullable = false)
    private String responseMessage;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

    public ResponseMapper getDefaultResponse() {
        ResponseMapper responseMapper = new ResponseMapper();
        responseMapper.setResponseCode(ResponseCode.UNKNOWN.getCode());
        responseMapper.setHttpStatusCode("500");
        responseMapper.setResponseMessage("Please try after some time!");
        return responseMapper;
    }
}
