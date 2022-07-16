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

    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public void addSubCategory(Category category) {
        category.mapParentCategory(this);
    }

    public void mapParentCategory(Category parentCategory) {
        if (hasParentCategory()) {
            this.parentCategory = parentCategory;
            parentCategory.getSubCategories().add(this);
        }
    }

    public boolean hasParentCategory() {
        return parentCategory != null;
    }
}
