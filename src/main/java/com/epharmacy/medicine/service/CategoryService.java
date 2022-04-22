package com.epharmacy.medicine.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epharmacy.medicine.model.Category;
import com.epharmacy.medicine.repository.CategoryRepository;
import com.epharmacy.medicine.response.Message;
import com.epharmacy.medicine.response.ResponseHandler;

@Service
public class CategoryService {

	private String responseMessage;
	private HttpStatus status;

	@Autowired
	private CategoryRepository categoryRepository;

	// validates request and calls saveCategories
	public ResponseEntity<Object> validateAndSave(List<Category> categories) {

		Collection<Category> savedCategories = null;
		
		try {
			if (categories.size() == 0) {
				responseMessage = Message.EMPTY_REQUEST.getMessage();
				status = HttpStatus.BAD_REQUEST;
			} else {
				savedCategories = categoryRepository.saveCategories(categories);
				responseMessage = Message.SUCCESS.getMessage();
				status = HttpStatus.OK;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseHandler.generateResponse(responseMessage, status, savedCategories);
	}

	//gets all master categories
	public ResponseEntity<Object> getCategories() {
		List<Category> categories = null;
		try {
			categories = categoryRepository.getCategories();
			if (categories.size() == 0) {
				responseMessage = Message.EMPTY.getMessage();
				status = HttpStatus.OK;
			} else {
				responseMessage = Message.SUCCESS.getMessage();
				status = HttpStatus.OK;
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}

		return ResponseHandler.generateResponse(responseMessage, status, categories);

	}

	//gets all subcategories under a master category
	public ResponseEntity<Object> getSubCategories(String category) {
		Category response = null;
		if (category == null) {
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} else {
			try
			{
			response = categoryRepository.getSubCategories(category);
			
				if (response.getSubCategories().size() == 0) {
					responseMessage = Message.EMPTY.getMessage();
					status = HttpStatus.OK;
				} else {
					responseMessage = Message.SUCCESS.getMessage();
					status = HttpStatus.OK;
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
				responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
				status = HttpStatus.CONFLICT;
			}

		}
		return ResponseHandler.generateResponse(responseMessage, status, response);
	}
}