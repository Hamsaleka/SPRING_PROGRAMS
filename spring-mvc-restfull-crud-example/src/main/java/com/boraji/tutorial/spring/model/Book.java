package com.boraji.tutorial.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Book")
public class Book {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String title;
   private String author;
  public void setId(Long id)
  {
      this.id=id;
  }
  public Long getId()

{
    return id;
} 
public void setTitle(String title)
{
    this.title=title;
}
public String getTitle()

{
  return title;
} 
public void setAuthor(String author)
{
    this.author=author;
}
public String getAuthor()

{
  return author;
} 
 
}