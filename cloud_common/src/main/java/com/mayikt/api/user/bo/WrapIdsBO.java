package com.mayikt.api.user.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WrapIdsBO implements Serializable {
    private List<Long> ids;
}
