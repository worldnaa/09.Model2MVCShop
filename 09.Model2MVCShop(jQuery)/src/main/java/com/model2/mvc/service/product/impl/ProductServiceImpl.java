package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.ProductDao; // ProductDao 인스턴스 import

//==> 상품관리 서비스 구현
@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao; //==> ProductDao productDao = new ProductDaoImpl(); 와 같은 의미
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	///Constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}

	//Method
	public void addProduct(Product product) throws Exception {
		productDao.addProduct(product);
	}

	
	public Product getProduct(int prodNo) throws Exception {
		return productDao.getProduct(prodNo);
	}

	
	public Map<String, Object> getProductList(Search search) throws Exception {
		
		//<Product>만 받는 List list 변수 선언 후, productDaoImpl.getProductList 결과 대입
		//ProductMapper.xml에서 product에 저장한 값을 가져와 list에 대입
		List<Product> list = productDao.getProductList(search);
		System.out.println("list 저장완료 : " + list);
		
		//productDaoImpl.getTotalCount 실행 후, 결과값 변수 totalCount에 대입
		int totalCount = productDao.getTotalCount(search);
		System.out.println("totalCount 저장완료 : " + totalCount);
		
		//map에 list, totalCount 값 담기
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map; 
	}

	
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

}//end of class
