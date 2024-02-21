package com.kdt.landing.domain.user.entity;

import com.kdt.landing.global.cosntant.BaseEntity;
import com.kdt.landing.global.cosntant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String pw;

    @Column(unique = true)
    private String email;

    private String nickname;

    private int active;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Role> roleSet = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.STUDENT; // 디폴트로 USER 권한을 갖도록 초기화

    public void changePassword(String pw) {
        this.pw = pw;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void addRole(Role role) {
        this.roleSet.add(role);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

}