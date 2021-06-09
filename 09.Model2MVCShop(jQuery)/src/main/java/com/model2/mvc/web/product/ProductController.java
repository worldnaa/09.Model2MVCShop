package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.CookieGenerator;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

//==> 상품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	//Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
	
	//Constructor
	public ProductController() {
		System.out.println("\n==> " + this.getClass() + " 의 default 생성자 실행");
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
		
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception{
		
		System.out.println("/product/addProduct : GET");
		
		return "forward:/product/addProductView.jsp";
	}
	
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception{
		
		System.out.println("/product/addProduct : POST");
		//Business Logic
		product.setManuDate(product.getManuDate().replaceAll("-", ""));
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
		
	@RequestMapping(value="getProduct", method=RequestMethod.GET )
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model,
							 @CookieValue(value="history", required=false) String history,
							 HttpServletResponse response) throws Exception {
	
		System.out.println("/product/getProduct : GET");
		
		//Cookie 생성
		CookieGenerator cookie = new CookieGenerator();
		history = "history" + prodNo;
		cookie.setCookieName("history");
		cookie.addCookie(response, history);
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		//Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value="getJsonProduct/{prodNo}", method=RequestMethod.GET )
	public void getJsonProduct(@PathVariable int prodNo, Model model,
							     @CookieValue(value="history", required=false) String history,
							     HttpServletResponse response) throws Exception {
	
		System.out.println("/product/getProduct : GET");
		
		//Cookie 생성
		CookieGenerator cookie = new CookieGenerator();
		history = "history" + prodNo;
		cookie.setCookieName("history");
		cookie.addCookie(response, history);
				
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		//Model 과 View 연결
		model.addAttribute("product", product);
	}
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		
		System.out.println("/product/updateProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}

	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception {
		
		System.out.println("/product/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		
		return "forward:/product/getProduct?prodNo="+product.getProdNo();
	}
	

	@RequestMapping(value="listProduct")	
	public String listProduct(@ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
	
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage : " + resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
}//end of class
