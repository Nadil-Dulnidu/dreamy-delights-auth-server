package com.dreamy_delights.root.dto;

import com.dreamy_delights.root.common.Constants;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Admin extends User {

    @Override
    public Integer getRole() {
        return Constants.ROLE_ADMIN;
    }
}
