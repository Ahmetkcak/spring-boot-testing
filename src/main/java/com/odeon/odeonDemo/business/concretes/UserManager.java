package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.UserService;
import com.odeon.odeonDemo.business.dtos.requests.RegisterRequest;
import com.odeon.odeonDemo.business.dtos.responses.GetUserInfoResponse;
import com.odeon.odeonDemo.business.messages.AuthMessages;
import com.odeon.odeonDemo.business.rules.UserBusinessRules;
import com.odeon.odeonDemo.core.utilities.exceptions.types.BusinessException;
import com.odeon.odeonDemo.core.utilities.mapping.ModelMapperService;
import com.odeon.odeonDemo.dataAccess.abstracts.UserRepository;
import com.odeon.odeonDemo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;
    private final UserBusinessRules userBusinessRules;

    @Override
    public void register(RegisterRequest registerRequest) {

        userBusinessRules.userEmailCanNotBeDuplicated(registerRequest.getEmail());

        User user = modelMapperService.forRequest().map(registerRequest, User.class);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BusinessException(AuthMessages.LOGIN_FAILED));
    }

    @Override
    public GetUserInfoResponse getUserInfoWithRoles(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(AuthMessages.LOGIN_FAILED));
        GetUserInfoResponse response = modelMapperService.forResponse().map(user, GetUserInfoResponse.class);
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BusinessException(AuthMessages.LOGIN_FAILED));
    }
}
