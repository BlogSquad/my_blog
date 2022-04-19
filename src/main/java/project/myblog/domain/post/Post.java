package project.myblog.domain.post;

import project.myblog.domain.BaseTimeEntity;
import project.myblog.domain.member.Member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
