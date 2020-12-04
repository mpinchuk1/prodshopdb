package org.appMain.entities.dto;


import org.appMain.entities.CustomUser;

import java.util.List;

public class UserDTO {
    private List<CustomUser> customUsers;

    public List<CustomUser> getCustomers() {
        return customUsers;
    }

    public void setCustomers(List<CustomUser> customUsers) {
        this.customUsers = customUsers;
    }
}
