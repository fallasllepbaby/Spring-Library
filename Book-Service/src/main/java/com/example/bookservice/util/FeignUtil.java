package com.example.bookservice.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value ="feignDemo" , url ="http://localhost:8082/v1/bookrecords")
public interface FeignUtil {

    @PostMapping("/{id}")
    void add(@PathVariable Long id);

    @GetMapping("/free")
    List<Long> getIdFreeBooks();

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

}
