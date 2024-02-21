package com.kdt.landing.domain.profile.entity;

import com.kdt.landing.domain.user.entity.User;
import com.kdt.landing.global.cosntant.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private User user;

    @Builder.Default
    private String userImage = "basic.png";

    private String git;

    private String insta;

    private String blog;

    private String linked;

    public void change(String userImage, String git, String insta, String blog, String linked) {
        this.userImage = userImage;
        this.git = git;
        this.blog = blog;
        this.insta = insta;
        this.blog = linked;
    }
}