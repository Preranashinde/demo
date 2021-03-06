package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/verifyaccount")
@CrossOrigin(value = "*")
@PropertySource("classpath:message.properties")
//@Profile("dev")
public class VerifyAccountController {

    @Autowired
    private IBookStoreService iBookStoreService;

    @Autowired
    private Environment environment;

    @GetMapping("/{userId}")
    public ResponseEntity<String> verifyAccount(@PathVariable long userId) {
        return new ResponseEntity(iBookStoreService.verifyUserAccount(userId), HttpStatus.OK);
    }

    @Cacheable(value = "Book")
    @GetMapping("/all")
    public List<Book> getAllBook(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        System.out.println(environment.getProperty("ALL_BOOKS"));
        return iBookStoreService.getAll(pageable);
    }

    @GetMapping("/sort-asc/price")
    public ResponseEntity<Page<Book>> booksInAscendingOrderByPrice(@PageableDefault(page = 0, size = 8)  Pageable pageable) {
        return new ResponseEntity(iBookStoreService.getAllBookByPriceAsc(pageable), HttpStatus.OK);
    }

    @GetMapping("/sort-desc/price")
    public ResponseEntity<Page<Book>> booksInDescendingOrderByPrice(@PageableDefault(page = 0, size = 8) Pageable pageable) {
        return new ResponseEntity(iBookStoreService.getAllBookByPriceDesc(pageable), HttpStatus.OK);
    }

    @GetMapping("/searchbooks/{searchText}")
    public ResponseEntity<List<Book>> searchBooks(@PathVariable String searchText) throws IOException {
        return new ResponseEntity(iBookStoreService.searchBooks(searchText), HttpStatus.OK);
    }
}
