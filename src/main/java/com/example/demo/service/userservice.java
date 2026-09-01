package com.example.demo.service;
import com.example.demo.models.user;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.userrepo;

@Service
@RequiredArgsConstructor
    public class userservice {

        private final userrepo userrepo;
        private final PasswordEncoder passwordEncoder;

        public user createuser(user user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userrepo.save(user);
        }
    public user getuser(Long id) {
        return userrepo.findById(id).orElse(null);
    }
}

