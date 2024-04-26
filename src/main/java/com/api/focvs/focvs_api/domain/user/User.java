package com.api.focvs.focvs_api.domain.user;

import com.api.focvs.focvs_api.dtos.auth.RegisterRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    public User (RegisterRequestDTO registerRequestDTO) {
        this.name = registerRequestDTO.name();
        this.imageUrl = registerRequestDTO.imageUrl();
    }
}
