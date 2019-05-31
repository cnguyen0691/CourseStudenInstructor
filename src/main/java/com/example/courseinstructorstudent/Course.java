package com.example.courseinstructorstudent;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long id;

    @NotNull
    @Size(min =2)
    private String title;

    @NotNull
    @Size(min=2)
    private String instructor;

    @NotNull
    @Size(min=2)
    private String description;

    @NotNull
    @Min(3)
    private int credit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Student> students;

    public Course() {
    }

    public Course(@NotNull @Size(min = 2) String title, @NotNull @Size(min = 2) String instructor, @NotNull @Size(min = 2) String description, @NotNull @Min(3) int credit) {
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.credit = credit;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
