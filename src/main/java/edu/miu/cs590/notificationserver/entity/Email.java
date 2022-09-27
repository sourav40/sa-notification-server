package edu.miu.cs590.notificationserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_address")
    private String from;
    @Column(name = "to_address")
    private String to;
    private String subject;
    private String message;
    private LocalDateTime sendDate;
}

