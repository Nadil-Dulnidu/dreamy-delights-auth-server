package com.dreamy_delights.root.dto;

import com.dreamy_delights.root.common.Constants;

public class RegularUser extends User {
    @Override
    public Integer getRole() {
        return Constants.ROLE_USER;
    }
}
