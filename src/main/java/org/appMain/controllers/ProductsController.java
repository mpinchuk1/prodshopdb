package org.appMain.controllers;

import org.appMain.entities.CustomUser;
import org.appMain.entities.Supplier;
import org.appMain.entities.dto.AddProductDTO;
import org.appMain.entities.dto.CreateOrderDTO;
import org.appMain.security.CustomUserDetails;
import org.appMain.services.ProductService;
import org.appMain.services.SupplierService;
import org.appMain.utils.ProductAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final ProductService productService;
    private final SupplierService supplierService;

    @Autowired
    public ProductsController(ProductService productService, SupplierService supplierService) {
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @GetMapping("admin/products")
    public String getAllProductsForAdmin(Model model) {
        logger.info("handling get request for ALL products from ADMIN");

        model.addAttribute("adproducts", productService.getProductsWithQuantities());
        return "admin-products";
    }

    @GetMapping("products")
    public String getAllProducts(Model model) {
        CustomUser user = getCurrentUser().getUser();
        logger.info("handling get request for products from " + user.getLogin());
        model.addAttribute("products", productService.getAllProduct());
        return "admin-products";
    }

    @GetMapping("/")
    public String getMain(Model model) {
        model.addAttribute("productdtos", productService.getAllProduct());
        model.addAttribute("fororder", new CreateOrderDTO());
        return "index";
    }

    @GetMapping("admin/products/update_page/{id}")
    public String updateProductPage(@PathVariable("id") Long id, Model model) {
        logger.info("Trying to update product with id: " + id);
        model.addAttribute("product", productService.getAddProductDTO(id));
        return "update-product";
    }

    @PostMapping("admin/products/update")
    public String updateProduct(@ModelAttribute("product") AddProductDTO product) {
        try {
            productService.update(product);
        } catch (ProductAlreadyExistsException e) {
            return "redirect:/admin/products?updateerror";
        }
        return "redirect:/admin/products";
    }

    @GetMapping("admin/products/new")
    public String addProductPage(Model model) {
        List<String> supplierCompanies = supplierService.getAllSuppliers()
                .stream()
                .map(Supplier::getSupplierCompanyName)
                .collect(Collectors.toList());

        model.addAttribute("product", new AddProductDTO());
        model.addAttribute("suppliers", supplierCompanies);
        return "add-product";
    }

    @PostMapping("admin/products/new")
    public String addProduct(@ModelAttribute("product") AddProductDTO addProductDTO) {
        logger.info("Trying to add new product: " + addProductDTO);
        try {
            productService.addProduct(addProductDTO);
        } catch (ProductAlreadyExistsException exception) {
            return "redirect:/admin/products/new?error";
        }
        return "redirect:/admin/products";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    private CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();
    }
}
