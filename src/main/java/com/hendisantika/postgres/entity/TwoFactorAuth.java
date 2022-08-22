package com.hendisantika.postgres.entity;


import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="two_factor_auth")
public class TwoFactorAuth {

    @Id
    @Column(name = "id")
    @SequenceGenerator (name="tfo_id_seq",
                   sequenceName="tfo_id_seq",
                   allocationSize=1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE,
           generator="tfo_id_seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "otp")
    private int otp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
