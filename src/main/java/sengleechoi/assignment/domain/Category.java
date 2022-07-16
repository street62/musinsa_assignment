package sengleechoi.assignment.domain;

import lombok.AllArgsConstructor;
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
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private final List<Category> subCategories = new ArrayList<>();
}
