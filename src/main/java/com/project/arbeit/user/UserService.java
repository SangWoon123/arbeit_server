package com.project.arbeit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(String email,String uid,String name){
        User user=User.builder()
                .email(email)
                .uid(uid)
                .name(name)
                .build();
        return userRepository.save(user);
    }


    public User getByUid(String uid){
        return userRepository.findByUid(uid);
    }


}
