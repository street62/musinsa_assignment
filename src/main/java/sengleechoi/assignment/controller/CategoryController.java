package sengleechoi.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sengleechoi.assignment.dto.category.CategoryListResponse;
import sengleechoi.assignment.service.CategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryListResponse> loadCategories(@RequestParam String categoryName) {
        return ResponseEntity.ok(categoryService.getCategoriesByName(categoryName));
    }
}
