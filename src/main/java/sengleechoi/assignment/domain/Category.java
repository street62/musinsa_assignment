package sengleechoi.assignment.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;
    @Transient
    private final List<Category> subCategories = new ArrayList<>();
    private Boolean isDeleted = false;

    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public void mapParentCategory(Category parentCategory) {
        if (parentCategory != null) {
            this.parentCategory = parentCategory;
            parentCategory.addSubCategory(this);
        }
    }

    public void modifyName(String name) {
        this.name = name;
    }

    private void addSubCategory(Category category) {
        subCategories.add(category);
    }

    public boolean hasParentCategory() {
        return parentCategory != null;
    }

    public void markAsDelete() {
        for (Category subCategory : subCategories) {
            subCategory.markAsDelete();
        }
        isDeleted = true;
    }
}
