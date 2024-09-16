package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.UserService;
import com.odeon.odeonDemo.business.dtos.requests.RegisterRequest;
import com.odeon.odeonDemo.core.utilities.ModelMapperService;
import com.odeon.odeonDemo.dataAccess.abstracts.UserRepository;
import com.odeon.odeonDemo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public void register(RegisterRequest registerRequest) {
        User user = modelMapperService.forRequest().map(registerRequest, User.class);
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByEmail(userName).orElseThrow();
    }
}
