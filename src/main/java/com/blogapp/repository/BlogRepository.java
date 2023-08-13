package com.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapp.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

	@Query("select b from Blog b where b.author.id=?1")
	List<Blog> findByAuthor(Long userId);
 
}
