package com.example.bookservice.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value ="feignDemo" , url ="http://localhost:8765/v1/bookrecords")
public interface FeignUtil {

    @PostMapping("/{id}")
    public void add(@PathVariable Long id);

    @GetMapping("/free")
    public List<Long> getIdFreeBooks();

}
