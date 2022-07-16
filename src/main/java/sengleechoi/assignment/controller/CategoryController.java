package sengleechoi.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sengleechoi.assignment.dto.category.CategoryListResponse;
import sengleechoi.assignment.dto.category.CategoryResponse;
import sengleechoi.assignment.service.CategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryListResponse> loadAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> loadCategoriesById(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoriesById(categoryId));
    }


}
