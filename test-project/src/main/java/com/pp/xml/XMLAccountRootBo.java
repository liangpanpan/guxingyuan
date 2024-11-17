package com.pp.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/11/8       create this file
 * </pre>
 */
@Data
@JacksonXmlRootElement(localName = "Accounts")
public class XMLAccountRootBo {

    /**
     * JacksonXmlElementWrapper注解用于指定列表或数组属性在XML中的包装元素，以提供更好的结构化层次和语义意义。
     * 当useWrapping = true：列表或数组会被包裹在一个额外的元素中。
     * 当useWrapping = false：列表或数组不会被包裹在额外的元素中。
     */


    // 设置为空则没有该节点
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "account")
    private List<XMLAccountBo> xmlAccountBoList;

}
