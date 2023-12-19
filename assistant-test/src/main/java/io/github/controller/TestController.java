package io.github.controller;

import io.github.common.web.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/test")
@Validated
public class TestController {

    @GetMapping("/paramTest")
    public Result<?> testParam(@RequestParam @NotBlank(message="hello 字段为null") String hello){
        return Result.success();
    }
}
