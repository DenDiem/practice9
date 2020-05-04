package com.example.demo.Controler;


import com.example.demo.Entity.BookEntity;
import com.example.demo.Entity.UserBookEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Model.BookDto;
import com.example.demo.Model.BookResponceDto;
import com.example.demo.Model.SearchDto;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
public class IndexController {

    @Autowired
    private ApplicationContext appContext;
    @RequestMapping({"/", ""})
    public String index() {
        return "add-books-page";
    }

    @RequestMapping(value = "/searchFunction", method = RequestMethod.POST)
    public String search(@RequestParam(value = "search", required = false) String letter, Model model) {
        List<BookEntity> searchResults = null;
        BookService bookService = appContext.getBean(BookService.class);
        System.out.println(letter);
        try {
            searchResults = bookService.findAllWhereIsbnLikeOrTitleLike(letter,letter);

        } catch (Exception ex) {
            //here you should handle unexpected errors
            //...
            //throw ex;
        }
        model.addAttribute("books", searchResults);
        return "index-search";

    }
    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public String booksList(@ModelAttribute BookDto bookModel,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.toString());
        model.addAttribute("bookModel",bookModel);

        BookService bookService = appContext.getBean(BookService.class);
        bookService.createBook(bookModel.getTitle(),bookModel.getIsbn(),bookModel.getAuthor());
              System.out.println("ID = " + bookService.findByID(1));


        return "redirect:books-list";
    }
    @RequestMapping(value ="book-favorite/{id}", method = RequestMethod.POST)
    public String addBookfavorite(@PathVariable("id") int id,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserBookService userBookService = appContext.getBean(UserBookService.class);
        User user = (User) auth.getPrincipal();
        System.out.println("asd"+user.getUsername());
        userBookService.createBook(user.getUsername(),id);

        return "redirect:books-list";
    }
    @RequestMapping(value ="book-favorite-delete/{id}", method = RequestMethod.POST)
    public String deleteBookfavorite(@PathVariable("id") Integer id,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserBookService userBookService = appContext.getBean(UserBookService.class);
        User user = (User) auth.getPrincipal();
        System.out.println("delete -> "+user.getUsername());
        List<UserBookEntity> userBookEntities = userBookService.findAllByUseridLike(user.getUsername());
        for ( UserBookEntity userBookEntity :userBookEntities
        ) {

            if(userBookEntity.getBookid().equals(id)) {
                userBookService.deleteByIdLike(userBookEntity.getId());
            }

        }
        System.out.println(Arrays.toString(userBookEntities.toArray()));


        return "redirect:/book-favorite/books-list";
    }
    @RequestMapping(value = "/book-favorite/books-list", method = RequestMethod.GET)
    public String favoritebooksList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserBookService userBookService = appContext.getBean(UserBookService.class);
        User user = (User) auth.getPrincipal();
       // System.out.println(user.getUsername() + "myUsername");
        BookService bookService = appContext.getBean(BookService.class);
        List<UserBookEntity> userBookEntities = userBookService.findAll();
        //System.out.println(Arrays.toString(userBookEntities.toArray()));
        List<BookEntity> result = new ArrayList<>();
        for ( UserBookEntity userBookEntity :userBookEntities
             ) {

            if(userBookEntity.getUserid().equals(user.getUsername())) {
             //   System.out.println(user.getUsername());
               // System.out.println(userBookEntity.getUserid());
                result.add(bookService.findByID(userBookEntity.getBookid()));
            }

        }
        List<BookEntity> listWithoutDuplicates = new ArrayList<>(new HashSet<>(result));
       // System.out.println(Arrays.toString(result.toArray()));
        model.addAttribute("books",listWithoutDuplicates );


        return "index-favorite";
    }
    @RequestMapping(value = "/books-list", method = RequestMethod.GET)
    public String booksList(Model model) {
        BookService bookService = appContext.getBean(BookService.class);
        model.addAttribute("books", bookService.findAllBooks());


        return "index";
    }
    @RequestMapping(value ="book/{id}", method = RequestMethod.GET)
    public String bookPage(@PathVariable("id") int id,Model model) {
        BookService bookService = appContext.getBean(BookService.class);
        BookEntity bookEntity =  bookService.findByID(id);
        model.addAttribute("book",bookEntity);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserBookService userBookService = appContext.getBean(UserBookService.class);
        User user = (User) auth.getPrincipal();
        System.out.println(user.getUsername() + "myUsername");

        List<UserBookEntity> userBookEntities = userBookService.findAll();
        System.out.println(Arrays.toString(userBookEntities.toArray()));
        List<BookEntity> result = new ArrayList<>();
        for ( UserBookEntity userBookEntity :userBookEntities
        ) {

            if(userBookEntity.getUserid().equals(user.getUsername())&&userBookEntity.getBookid()==id) {
                System.out.println(user.getUsername());
                System.out.println(userBookEntity.getUserid());
                result.add(bookService.findByID(userBookEntity.getBookid()));
            }

        }
        System.out.println(Arrays.toString(result.toArray()));
        boolean kostylb = result.isEmpty();
        System.out.println(kostylb);
        model.addAttribute("flag",kostylb);
        return "book-one-page";
    }
    @RequestMapping("/lab8")
    public String indexlab8() {
        return "indexlab8";
    }

    @RequestMapping(value = "/add-book-ajax", method = RequestMethod.POST)
    public ResponseEntity<BookResponceDto> addnewBookAjax(
            @RequestBody final BookDto bookDto
    ) {
        System.out.println("Accept login request");
        System.out.println(bookDto);
        BookService bookService = appContext.getBean(BookService.class);
        bookService.createBook(bookDto.getTitle(),bookDto.getIsbn(),bookDto.getAuthor());
        System.out.println("ID = " + bookService.findByID(1));

        return ResponseEntity.ok(BookResponceDto.of(bookService.findAllBooks(),"success"));

    }
    @RequestMapping(value = "/ajax-search", method = RequestMethod.POST)
    public ResponseEntity<BookResponceDto> ajaxSearch(
            @RequestBody final SearchDto searchDto
    ) {
        List<BookEntity> searchResults = null;
        BookService bookService = appContext.getBean(BookService.class);
        String letter = searchDto.getLetter();
        System.out.println(searchDto);
        try {
            searchResults = bookService.findAllWhereIsbnLikeOrTitleLike(letter,letter);

        } catch (Exception ex) {
            //here you should handle unexpected errors
            //...
            //throw ex;
        }
        return ResponseEntity.ok(BookResponceDto.of(searchResults,"success"));
    }
}
