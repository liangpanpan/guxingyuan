package com.pp.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/11/8       create this file
 * </pre>
 */
@Data
//@JacksonXmlRootElement(localName = "account")
public class XMLAccountBo {

    @JacksonXmlProperty(localName = "id", isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "accId")
    private String accId;

    @JacksonXmlProperty(localName = "userPasswordMD5")
    private String userPasswordMD5;

    @JacksonXmlProperty(localName = "userPasswordSHA1")
    private String userPasswordSHA1;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "sn")
    private String sn;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "email")
    private EncryptElement email;


}
