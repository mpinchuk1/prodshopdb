package org.appMain.repo;


import org.appMain.entities.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<CustomUser, UUID> {
}
