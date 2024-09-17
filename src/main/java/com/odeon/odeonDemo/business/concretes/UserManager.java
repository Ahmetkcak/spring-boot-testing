package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.UserService;
import com.odeon.odeonDemo.business.dtos.requests.RegisterRequest;
import com.odeon.odeonDemo.business.messages.AuthMessages;
import com.odeon.odeonDemo.core.utilities.mapping.ModelMapperService;
import com.odeon.odeonDemo.dataAccess.abstracts.UserRepository;
import com.odeon.odeonDemo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest registerRequest) {
        User user = modelMapperService.forRequest().map(registerRequest, User.class);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByEmail(userName).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new Exception(AuthMessages.LOGIN_FAILED));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
