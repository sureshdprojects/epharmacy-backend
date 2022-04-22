package com.epharmacy.medicine.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epharmacy.medicine.model.Product;
import com.epharmacy.medicine.repository.ProductRepository;
import com.epharmacy.medicine.response.Message;
import com.epharmacy.medicine.response.ResponseHandler;

@Service
public class ProductService {

	private String responseMessage;
	private HttpStatus status;

	@Autowired
	private ProductRepository productRepository;

	/*
	 * verifies request and generates product Id for each product and saves to the
	 * data base
	 */
	public ResponseEntity<Object> saveProducts(List<Product> products) {

		Collection<Product> savedProducts = null;

		try {
		if (products == null) {
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} else {
			for (Product product : products) {
				if (product.getProductId() == 0) {
					
					long productSequence = productRepository.getProductSequence("productId");
					if(productSequence>0)
						product.setProductId(productSequence);
				}
				product.setRating(1);
			}
			savedProducts = productRepository.saveProducts(products);
			if (savedProducts == null) {
				responseMessage = Message.UNABLE_TO_ADD_PRODUCTS.getMessage();
				status = HttpStatus.CONFLICT;
			} else {
				responseMessage = Message.SUCCESS.getMessage();
				status = HttpStatus.CREATED;
			}

		}
		}catch(Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}
		return ResponseHandler.generateResponse(responseMessage, status, savedProducts);

	}

	public List<Product> getAllProducts() {
		return productRepository.getAllProducts();
	}

	//gets all products of a master category
	public ResponseEntity<Object> getProductsOfCategory(String category) {

		List<Product> productsOfCategory = null;

		if (category == null) {
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} else {

			try {
				productsOfCategory = productRepository.getProductsOfCategory(category);
				if(productsOfCategory == null) {
					responseMessage = Message.PRODUCTS_NOT_FOUND.getMessage();
					status = HttpStatus.NOT_FOUND;
				}else {
					responseMessage = Message.SUCCESS.getMessage();
					status = HttpStatus.OK;
				}

			} catch (Exception e) {
				e.printStackTrace();
				responseMessage = Message.DATABASE_FETCHING_FAILED.getMessage();
				status = HttpStatus.EXPECTATION_FAILED;
			}
		}

		return ResponseHandler.generateResponse(responseMessage, status, productsOfCategory);
	}

	//gets products of subcategory under a master category
	public ResponseEntity<Object> getProductsOfSubCategory(String category, String subCategory) {

		List<Product> productsOfSubCategory = null;

		if (category == null || subCategory == null) {
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} else {
			try {
				productsOfSubCategory = productRepository.getProductsOfSubCategory(category, subCategory);
				if(productsOfSubCategory == null) {
					responseMessage = Message.PRODUCTS_NOT_FOUND.getMessage();
					status = HttpStatus.NOT_FOUND;
				}else {
					responseMessage = Message.SUCCESS.getMessage();
					status = HttpStatus.OK;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				responseMessage = Message.DATABASE_FETCHING_FAILED.getMessage();
				status = HttpStatus.EXPECTATION_FAILED;
			}
		}
		return ResponseHandler.generateResponse(responseMessage, status, productsOfSubCategory);
	}

}
