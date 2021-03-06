package sengleechoi.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sengleechoi.assignment.domain.Category;
import sengleechoi.assignment.dto.category.CategoryNameModificationRequest;
import sengleechoi.assignment.dto.GeneralResponse;
import sengleechoi.assignment.dto.category.ParentModificationRequest;
import sengleechoi.assignment.dto.category.CategoryCreationRequest;
import sengleechoi.assignment.dto.category.CategoryCreationResponse;
import sengleechoi.assignment.dto.category.CategoryListResponse;
import sengleechoi.assignment.dto.category.CategoryResponse;
import sengleechoi.assignment.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryListResponse> loadAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(CategoryListResponse.from(categories));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> loadCategoriesById(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(CategoryResponse.from(category));
    }

    @PostMapping
    public ResponseEntity<CategoryCreationResponse> createCategory(@RequestBody CategoryCreationRequest request) {
        Category savedCategory = categoryService.createCategory(request);
        return ResponseEntity.ok(CategoryCreationResponse.from(savedCategory));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<GeneralResponse> modifyCategoryName(@PathVariable Long categoryId, @RequestBody CategoryNameModificationRequest request) {
        categoryService.modifyCategoryName(categoryId, request);
        return ResponseEntity.ok(new GeneralResponse(200, "?????????????????? ?????????????????????."));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<GeneralResponse> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new GeneralResponse(200, "??????????????? ?????????????????????."));
    }

    @PatchMapping("/{categoryId}/parent")
    public ResponseEntity<GeneralResponse> modifyParentCategory(@PathVariable Long categoryId, @RequestBody ParentModificationRequest request) {
        categoryService.modifyParentCategory(categoryId, request);
        return ResponseEntity.ok(new GeneralResponse(200, "?????? ??????????????? ?????????????????????."));
    }


}
