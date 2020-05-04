package com.example.demo.Service;


import com.example.demo.Entity.BookEntity;
import com.example.demo.Entity.UserBookEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Reprository.BookRepository;
import com.example.demo.Reprository.UserBookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBookService {

    private final UserBookRepo userBookRepo;
    //private final EntityManager entityManager;
    @Transactional
    public UserBookEntity createBook(String user, Integer book) {

        UserBookEntity UserBookEntity = new UserBookEntity();
        UserBookEntity.setUserid(user);
        UserBookEntity.setBookid(book);
        //return entityManager.merge(book);
        return userBookRepo.saveAndFlush(UserBookEntity);

    }
    @Transactional
    public List<UserBookEntity> findAll() {
        //return entityManager.createQuery("FROM  BookEntity ", BookEntity.class).getResultList();
        return userBookRepo.findAll();
    }

    @Transactional
    public List<UserBookEntity> findAllByUseridLike(String id) {
        return  userBookRepo.findAllByUseridLike('%' + id + '%');
    }
    @Transactional
    public void deleteByIdLike(Integer id){
           userBookRepo.delete( id );
    }
}