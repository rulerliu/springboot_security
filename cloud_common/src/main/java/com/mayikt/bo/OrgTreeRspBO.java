package com.mayikt.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor()
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgTreeRspBO implements Serializable {
    private Long id;
    private Long parentId;
    private String label;
    private Integer flag;
    private Integer level;
    private String remark;
    private Boolean disabled;
    private List<OrgTreeRspBO> children;

}

