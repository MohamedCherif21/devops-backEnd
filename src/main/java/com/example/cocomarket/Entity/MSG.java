package com.example.cocomarket.Entity;
import lombok.*;

import javax.persistence.*;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MSG {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String content;
    private String sender;
    private MessageType type;






    public String getContent() {
        return content;
    }
    public MSG(String content) {
        this.content = content;
    }



    public enum MessageType {
        CHAT, LEAVE, JOIN
    }
    @ManyToOne(cascade = CascadeType.ALL)
    private User userMsg;
}
