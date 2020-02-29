package com.hilderjares.spring;

import com.hilderjares.spring.entity.UserDomain;
import com.hilderjares.spring.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SpringAndJwtRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SpringAndJwtRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        UserDomain userDefault = new UserDomain();
        userDefault.setUsername("admin");
        userDefault.setPassword(bCryptPasswordEncoder.encode("admin"));

        UserDomain user = userRepository.findByUsername(userDefault.getUsername());

        if (user == null) {
            userRepository.save(userDefault);
        }

        userDefault = null;

        logger.info("inserting the default user");
    }
}
