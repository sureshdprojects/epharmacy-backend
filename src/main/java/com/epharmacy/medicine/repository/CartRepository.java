package com.epharmacy.medicine.repository;

import org.bson.Document;
import com.epharmacy.medicine.model.CartProduct;

public interface CartRepository {

	boolean addProductToCart(CartProduct cartProduct);

	Document getUserCartProducts(String email);

	boolean isProductAlreadyAdded(CartProduct cartProduct);

	boolean updateQuantityOfProduct(String email, long productId, int incrementBy);

	long deleteCartProductById(String email,CartProduct cartProduct);

	boolean emptyCart(String email);

}
