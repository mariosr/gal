package com.companyname.springapp.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.companyname.springapp.domain.Product;
import com.companyname.springapp.repository.InMemoryProductDao;
import com.companyname.springapp.repository.JPADisciplinaDao;
import com.companyname.springapp.repository.JPAProductDao;
import com.companyname.springapp.repository.ProductDao;

public class SimpleProductManagerTests {

    private SimpleProductManager productManager;
    
    private List<Product> products;
    
    private static int PRODUCT_COUNT = 2;
    
    private static Double CHAIR_PRICE = new Double(20.50);
    private static String CHAIR_DESCRIPTION = "Chair";
    
    private static String TABLE_DESCRIPTION = "Table";
    private static Double TABLE_PRICE = new Double(150.10);
    
    private static int POSITIVE_PRICE_INCREASE = 10;
    
    private JPADisciplinaDao curriculoDao;
    private JPAProductDao productDao;
    
    @Before
    public void setUp() throws Exception {
    	
    	curriculoDao =  new JPADisciplinaDao();
    	

        //productManager = new SimpleProductManager();
        productManager = new SimpleProductManager(curriculoDao,productDao);
        products = new ArrayList<Product>();
        
        // stub up a list of products
        Product product = new Product();
        product.setDescription("Chair");
        product.setPrice(CHAIR_PRICE);
        products.add(product);
        
        product = new Product();
        product.setDescription("Table");
        product.setPrice(TABLE_PRICE);
        products.add(product);
        
      //  ProductDao productDao = new InMemoryProductDao(products);
        productManager.setProductDao(productDao);
        //productManager.setProducts(products);
    }

    @Test
    public void testGetProductsWithNoProducts() {

        //productManager = new SimpleProductManager();
        productManager = new SimpleProductManager(curriculoDao,productDao);
     //   productManager.setProductDao(new InMemoryProductDao(null));
        assertNull(productManager.getProducts());
    }

    @Test
    public void testGetProducts() {
        List<Product> products = productManager.getProducts();
        assertNotNull(products);        
        assertEquals(PRODUCT_COUNT, productManager.getProducts().size());
    
        Product product = products.get(0);
        assertEquals(CHAIR_DESCRIPTION, product.getDescription());
        assertEquals(CHAIR_PRICE, product.getPrice());
        
        product = products.get(1);
        assertEquals(TABLE_DESCRIPTION, product.getDescription());
        assertEquals(TABLE_PRICE, product.getPrice());      
    }   
    
    @Test
    public void testIncreasePriceWithNullListOfProducts() {
        try {

           // productManager = new SimpleProductManager();
            productManager = new SimpleProductManager(curriculoDao,productDao);
            //productManager.setProductDao(new InMemoryProductDao(null));
            productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        }
        catch(NullPointerException ex) {
        	fail("Products list is null.");
        }
    }
    
    @Test
    public void testIncreasePriceWithEmptyListOfProducts() {
        try {

           // productManager = new SimpleProductManager();
            productManager = new SimpleProductManager(curriculoDao,productDao);
           // productManager.setProductDao(new InMemoryProductDao(new ArrayList<Product>()));
            //productManager.setProducts(new ArrayList<Product>());
            productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        }
        catch(Exception ex) {
        	fail("Products list is empty.");
        }           
    }
    
  //metodo responsavel por testar se o percentual foi realmente incrementado
    @Test
    public void testIncreasePriceWithPositivePercentage() {
        productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        double expectedChairPriceWithIncrease = 22.55;
        double expectedTablePriceWithIncrease = 165.11;
        
        List<Product> products = productManager.getProducts();      
        Product product = products.get(0);
        assertEquals(expectedChairPriceWithIncrease, product.getPrice(), 0);
        
        product = products.get(1);      
      //o terceiro valor é o delta, serve para dizer o quao EXATO você deseja que os numeros sejam iguais
        assertEquals(expectedTablePriceWithIncrease, product.getPrice(), 0);       
    }
}