package com.gmail.v.c.charkin.gurmanfood.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shawarmas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Shawarma {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shawarma_id_seq")
    @SequenceGenerator(name = "shawarma_id_seq", sequenceName = "shawarma_id_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "shawarma_title", nullable = false)
    private String shawarmaTitle;

    @Column(name = "morality", nullable = false)
    private String morality;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "filename")
    private String filename;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "category", nullable = false)
    private String category;
}
