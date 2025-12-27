package org.example.controller;

import org.example.pojo.Bookstore;
import org.example.pojo.buy;
import org.example.service.BookstoreService;
import org.example.service.BuyService;
import org.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookStoreController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private BookstoreService bookstoreService;
    @Autowired
    private BuyService buyService;
    @RequestMapping("/")
    public String toLogin() {
        return "/login.jsp";
    }
    @RequestMapping("/bookStore")
    public String toBookStore(@RequestParam("customerId") Integer customerId, Model model) {
        List<Bookstore> books = bookstoreService.findAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("customerId", customerId);
        return "/bookStore.jsp";
    }

    // 添加到购物车并结算
    @RequestMapping("/addToCart")
    public String addToCart(@RequestParam("customerId") Integer customerId,
                            @RequestParam("bookId") Integer bookId,
                            @RequestParam("quantity") Integer quantity,
                            Model model) {

        ordersService.byBook(customerId, bookId, quantity);
        model.addAttribute("购买成功");
        return "redirect:/bookStore?customerId=" + customerId;
    }

    // 查询购书记录
    @RequestMapping("/queryPurchase")
    public String queryPurchase(@RequestParam("customerId") Integer customerId, Model model) {
        List<buy> orders = buyService.findById(customerId);
        model.addAttribute("orders", orders);
        model.addAttribute("customerId", customerId);
        return "/purchaseRecord.jsp";
    }
}