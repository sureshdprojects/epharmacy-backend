package com.epharmacy.medicine.repository;

import com.epharmacy.medicine.model.User;

public interface UserRepository {

	User saveUser(User user);

	User getUserInfo(String userEmail);

}
