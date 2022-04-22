package com.epharmacy.medicine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epharmacy.medicine.model.User;
import com.epharmacy.medicine.repository.UserRepository;
import com.epharmacy.medicine.response.Message;
import com.epharmacy.medicine.response.ResponseHandler;

@Service
public class UserService {

	private String responseMessage;
	private HttpStatus status;

	@Autowired
	private UserRepository userRepository;

	/* creates user account by validating request 
	 * if email already present sends the same to client*/
	public ResponseEntity<Object> processCreateRequest(User user) {

		User response = null;

		try {
			String email = user.getEmail();
			if (email == null) {
				responseMessage = Message.PLEASE_ENTER_CREDENTIALS.getMessage();
				status = HttpStatus.BAD_REQUEST;
			} else {
				
					try {
						response = userRepository.saveUser(user);
						responseMessage = Message.ACCOUNT_CREATED.getMessage();
						status = HttpStatus.OK;
					}catch(DuplicateKeyException e) {
						responseMessage = Message.EMAIL_ALREADY_EXISTS.getMessage();
						status = HttpStatus.BAD_REQUEST;
					}
			
			}
		} catch (NullPointerException e) {
			responseMessage = Message.PLEASE_ENTER_CREDENTIALS.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}

		return ResponseHandler.generateResponse(responseMessage, status, response);
	}

	//validates user credentials for login
	public ResponseEntity<Object> validateCredentials(User user) {
		
		User response = null;
		
		try {
			String passwordEntered = user.getPassword();
			User userInfo = userRepository.getUserInfo(user.getEmail());
			try {
				if (passwordEntered.equals(userInfo.getPassword())) {
					responseMessage = Message.USER_VERIFIED.getMessage();
					status = HttpStatus.OK;
					response = userInfo;
					response.setPassword(null);
				}else {
					responseMessage = Message.INCORRECT_PASSWORD.getMessage();
					status = HttpStatus.UNAUTHORIZED;
				}
			} catch (NullPointerException e) {
				responseMessage = Message.INCORRECT_EMAIL.getMessage();
				status = HttpStatus.BAD_REQUEST;
			}
		}catch(NullPointerException e) {
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}catch (Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}
		
		return ResponseHandler.generateResponse(responseMessage, status, response);
	}

}
