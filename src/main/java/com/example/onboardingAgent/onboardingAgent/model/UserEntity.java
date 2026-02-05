package com.example.onboardingAgent.onboardingAgent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String empId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password; // later use

    private String googleId;

    private Integer roleId; // 2,3,4

    private String location;

    private String designation;

    private boolean passwordRequired = true;

    private boolean documentRequired = true;

    private boolean verificationRequired = true;

    private Date dateOfJoining= new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private UserEntity manager;

    @OneToMany(mappedBy = "manager")
    private List<UserEntity> teamMembers;

}
