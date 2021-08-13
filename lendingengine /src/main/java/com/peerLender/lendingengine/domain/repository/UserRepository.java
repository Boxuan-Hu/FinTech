package com.peerLender.lendingengine.domain.repository;

import com.peerLender.lendingengine.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


}
