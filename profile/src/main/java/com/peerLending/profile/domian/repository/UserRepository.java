package com.peerLending.profile.domian.repository;

import com.peerLending.profile.domian.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {



}
